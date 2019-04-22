package com.ladybird.hkd.service.impl;
import com.github.pagehelper.PageInfo;
import com.ladybird.hkd.exception.ExcelImportException;
import com.ladybird.hkd.mapper.FacultyMapper;
import com.ladybird.hkd.mapper.GradeMapper;
import com.ladybird.hkd.mapper.TeacherMapper;
import com.ladybird.hkd.model.example.TeacherExample;
import com.ladybird.hkd.model.json.PageBean;
import com.ladybird.hkd.model.json.ResultJson;
import com.ladybird.hkd.model.pojo.Faculty;
import com.ladybird.hkd.model.pojo.Grade;
import com.ladybird.hkd.model.pojo.Teacher;
import com.ladybird.hkd.model.vo.Teaching;
import com.ladybird.hkd.service.TeacherManageService;
import com.ladybird.hkd.util.excel.ReadTeacherExcel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;
@Service
public class TeacherManageServiceImpl implements TeacherManageService {
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private FacultyMapper facultyMapper;
    @Autowired
    private GradeMapper gradeMapper;
    @Override
    public ResultJson selectTeacher(Teacher teacher, String faculty, int pageNum, int pageSize) throws Exception{
        int startNum = (pageNum-1)*pageSize;
        List<TeacherExample> teacherExamples = teacherMapper.selectTeacher(teacher,faculty,startNum,pageSize);
        if(teacherExamples == null || teacherExamples.isEmpty()){
            return ResultJson.Forbidden("查询错误");
        }
        PageBean pageResult = new PageBean();
        pageResult.setData(teacherExamples);
        pageResult.setCurPage(pageNum);
        pageResult.setPageCount(pageSize);
        int totalCount = teacherMapper.selectTeacherCount();
        pageResult.setTotalCount(totalCount);
        int totalPages;
        if(totalCount%10==0){
            totalPages = totalCount / pageSize;
        }else {
            totalPages = totalCount / pageSize+1;
        }
       pageResult.setTotalPages(totalPages);
        return ResultJson.Success(pageResult);
    }

    @Override
    public ResultJson addTeacher(Teacher teacher) throws Exception {
        if(!StringUtils.isNotBlank(teacher.getT_num())||!StringUtils.isNotBlank(teacher.getT_name())||!StringUtils.isNotBlank(teacher.getT_faculty())||!StringUtils.isNotBlank(teacher.getT_dept())){
            return ResultJson.ParameterError();
        }
        Teacher teacher1 = teacherMapper.selTeacherByNum(teacher.getT_num());
        if (teacher1 != null){
            return this.updateTeacher(teacher);

        }
        int resultCount = teacherMapper.addTeacher(teacher);
        if(resultCount > 0){
            return ResultJson.Success("添加成功");
        }
        return ResultJson.Forbidden("添加失败");
    }

    @Override
    public ResultJson updateTeacher(Teacher teacher) throws Exception {
        if(!StringUtils.isNotBlank(teacher.getT_num())||!StringUtils.isNotBlank(teacher.getT_name())||!StringUtils.isNotBlank(teacher.getT_faculty())||!StringUtils.isNotBlank(teacher.getT_dept())){
            return ResultJson.ParameterError();
        }
        int resultCount = teacherMapper.updateTeacher(teacher);
        if(resultCount > 0){
            return ResultJson.Success("更新成功");
        }
        return ResultJson.Forbidden("更新失败");
    }

    @Override
    public ResultJson deleteTeacher(String t_num) throws Exception {
        if(!StringUtils.isNotBlank(t_num)){
            return ResultJson.ParameterError();
        }
        int resultCount = teacherMapper.deleteTeacher(t_num);
        if (resultCount > 0){
            return ResultJson.Success("删除成功");
        }
        return ResultJson.Forbidden("删除失败");
    }
    @Override
    public List<TeacherExample> addTeachers(MultipartFile multipartFile) throws Exception {
        ReadTeacherExcel readTeacherExcel = new ReadTeacherExcel();
        List<TeacherExample> result = readTeacherExcel.getExcelInfo(multipartFile);
        List<Teacher> teachers = new ArrayList<>();
        for (TeacherExample t : result) {
            Teacher teacher = new Teacher();
            teacher.setT_num(t.getT_num());
            teacher.setT_name(t.getT_name());
            Faculty faculty = facultyMapper.findFaculty(
                    new Faculty(null, t.getT_faculty().getFac_name(), null));
            teacher.setT_faculty(faculty.getFac_num());
            teachers.add(teacher);
            t.setT_faculty(faculty);
        }
        Integer count = teacherMapper.checkInTeachers(teachers);
        if (count < result.size())
            throw new ExcelImportException("<导入教师信息>：应导入" + result.size() + "条，实际导入" + count + "条");
        return result;
    }

    @Override
    public ResultJson teaching(String t_num) throws Exception {
        if(!StringUtils.isNotBlank(t_num)){
            return ResultJson.Forbidden("请选择老师");
        }
        List<Teaching> teachings = teacherMapper.selectTeaching(t_num);

        for (Teaching teaching : teachings) {
            List<Grade> grades = new ArrayList<Grade>();
            String[] g = teaching.getGrade().split(",");
            for (int i = 0;i < g.length;i++){
                Grade grade = gradeMapper.findGradeById(g[i]);
                grades.add(grade);
            }
            teaching.setGrades(grades);
        }
        return ResultJson.Success(teachings);
    }




}
