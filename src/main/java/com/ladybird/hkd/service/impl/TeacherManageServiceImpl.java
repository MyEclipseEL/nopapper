package com.ladybird.hkd.service.impl;

import com.ladybird.hkd.mapper.FacultyMapper;
import com.ladybird.hkd.mapper.GradeMapper;
import com.ladybird.hkd.mapper.TeacherMapper;
import com.ladybird.hkd.model.example.TeacherExample;
import com.ladybird.hkd.model.json.ResultJson;
import com.ladybird.hkd.model.pojo.Faculty;
import com.ladybird.hkd.model.pojo.Grade;
import com.ladybird.hkd.model.pojo.Teacher;
import com.ladybird.hkd.model.vo.Teaching;
import com.ladybird.hkd.service.TeacherManageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
@Service("teacherManageService")
public class TeacherManageServiceImpl implements TeacherManageService {
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private FacultyMapper facultyMapper;
    @Autowired
    private GradeMapper gradeMapper;
    @Override
    public ResultJson selectTeacher(Teacher teacher,String faculty) throws Exception{
        List<TeacherExample> teacherExamples = teacherMapper.selectTeacher(teacher,faculty);
        if(teacherExamples == null || teacherExamples.isEmpty()){
            return ResultJson.Forbidden("查询错误");
        }
        return ResultJson.Success(teacherExamples);
    }

    @Override
    public ResultJson addTeacher(Teacher teacher) throws Exception {
        if(!StringUtils.isNotBlank(teacher.getT_num())||!StringUtils.isNotBlank(teacher.getT_name())||!StringUtils.isNotBlank(teacher.getT_faculty())||!StringUtils.isNotBlank(teacher.getT_dept())){
            return ResultJson.ParameterError();
        }
        Teacher t = new Teacher();
        t.setT_num(teacher.getT_num());
        List<TeacherExample> teacherExamples = teacherMapper.selectTeacher(t, null);
        if (teacherExamples != null ||!teacherExamples.isEmpty()){
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



    public List<Teacher> uploadTeacher(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();    //获取文件名
        return null;
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
