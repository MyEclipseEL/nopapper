package com.ladybird.hkd.service.impl;


import com.ladybird.hkd.exception.BusinessException;
import com.ladybird.hkd.exception.ParamException;
import com.ladybird.hkd.mapper.*;

import com.ladybird.hkd.model.example.GradeExample;
import com.ladybird.hkd.model.pojo.Department;
import com.ladybird.hkd.model.json.ResultJson;

import com.ladybird.hkd.model.pojo.Course;
import com.ladybird.hkd.model.example.DepartmentExample;
import com.ladybird.hkd.model.pojo.Faculty;
import com.ladybird.hkd.model.pojo.Grade;
import com.ladybird.hkd.service.MessageService;

import com.ladybird.hkd.util.excel.ReadGradeExcel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service("messageService")
public class MessageServiceImpl implements MessageService {
    @Autowired
    private FacultyMapper facultyMapper;
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private GradeMapper gradeMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private ExamMapper examMapper;

    public ResultJson selectAllFaculty() throws Exception{
        List<Faculty> faculties = facultyMapper.selectAllFaculty();
        if(faculties==null||faculties.isEmpty()){
            return ResultJson.Forbidden("查询失败");
        }
        return ResultJson.Success(faculties);
    }


    public ResultJson addFaculty(Faculty faculty) throws Exception {

        if(!StringUtils.isNotBlank(faculty.getFac_name())||!StringUtils.isNotBlank(faculty.getFac_num())){
            return ResultJson.ParameterError();
        }
        int resultCount = facultyMapper.selectFacultyByPrimary(faculty.getFac_num());
        if(resultCount > 0){
            this.updateFaculty(faculty);
        }
        resultCount = facultyMapper.addFaculty(faculty);
        if(resultCount > 0){
            return ResultJson.Success("添加成功");
        }

        return ResultJson.Forbidden("添加失败");
    }

    public ResultJson updateFaculty(Faculty faculty) throws Exception {
        if(!StringUtils.isNotBlank(faculty.getFac_name())||!StringUtils.isNotBlank(faculty.getFac_num())){
            return ResultJson.ParameterError();
        }
        int resultCount = facultyMapper.updateFaculty(faculty);
        if(resultCount > 0){
            return ResultJson.Success("更新成功");
        }
        return ResultJson.Forbidden("更新失败");
    }


    public ResultJson findFaculty(Faculty faculty) throws Exception {
        if(!StringUtils.isNotBlank(faculty.getFac_num())&&!StringUtils.isNotBlank(faculty.getFac_name())){
            return ResultJson.ParameterError();
        }
        Faculty faculty1 = facultyMapper.findFaculty(faculty);
        if(faculty1 == null){
            return ResultJson.Forbidden("查找失败");
        }
        return ResultJson.Success(faculty1);
    }




    /*public ResultJson selectAllDept(Faculty faculty) throws Exception {
        if(faculty == null){
            return ResultJson.ParameterError();
        }
        List<Department> departmentExamples = deptMapper.selectAllDept(faculty);
        if(departmentExamples == null || departmentExamples.isEmpty()){

            return ResultJson.Forbidden("查询错误");
        }
        return ResultJson.Success(departmentExamples);
    }*/
    @Override
    public ResultJson findDeptByFac(String fac_num) throws Exception {
        if (!StringUtils.isNotBlank(fac_num)){
            return ResultJson.Forbidden("请选择学院");
        }
        List<Department> departments = deptMapper.findDeptByFac(fac_num);
        if(departments == null||departments.isEmpty()){
            return ResultJson.Forbidden("查询失败");
        }
        return ResultJson.Success(departments);
    }

    public ResultJson addDept(Department department) throws Exception {
        if(!StringUtils.isNotBlank(department.getDept_num())||!StringUtils.isNotBlank(department.getDept_name())||!StringUtils.isNotBlank(department.getFaculty())){
            return ResultJson.ParameterError();
        }
        int resultCount = deptMapper.selectDeptByPrimary(department.getDept_num());
        if(resultCount > 0){
            this.updateDept(department);
        }
        resultCount = deptMapper.addDept(department);
        if(resultCount > 0){
            return ResultJson.Success("添加专业成功");
        }
        return ResultJson.Forbidden("添加专业失败");
    }


    public ResultJson updateDept(Department department) throws Exception {
        if(!StringUtils.isNotBlank(department.getDept_num())||!StringUtils.isNotBlank(department.getDept_name())||!StringUtils.isNotBlank(department.getFaculty())){
            return ResultJson.ParameterError();
        }
        int resultCount = deptMapper.updateDept(department);
        if(resultCount > 0) {
            return ResultJson.Success("修改成功");
        }
        return ResultJson.Forbidden("修改失败");
    }


    public ResultJson findDept(Department department) throws Exception {
        if (!StringUtils.isNotBlank(department.getDept_num())&&!StringUtils.isNotBlank(department.getDept_name())){
            return ResultJson.ParameterError();
        }
        List<Department> departments = deptMapper.selectDept(department);
       if (departments == null || departments.isEmpty())
           throw new BusinessException("查询失败");
        return ResultJson.Success(departments);
    }


    public ResultJson selectAllCourse() throws Exception {
        List<Course> courses = courseMapper.selectAllCourse();
        if(courses==null||courses.isEmpty()){
            return ResultJson.Forbidden("查询错误");
        }
        return ResultJson.Success(courses);
    }


    public ResultJson addCourse(Course course) throws Exception {
        if(!StringUtils.isNotBlank(course.getC_name())){
            return ResultJson.ParameterError();
        }
        if (course.getChapter() == null || course.getChapter()==0)
            throw new ParamException("<添加课程>：请填写章节！");
//        int resultCount = courseMapper.selectCourseByPrimary(course.getC_id());
//        if(resultCount > 0){
//            return updateCourse(course);
//        }
        if (course.getTip() == null || "".equals(course.getTip().trim()))
            course.setTip(null);
        Integer resultCount = courseMapper.addCourse(course);
        if (resultCount > 0){
            return ResultJson.Success("添加成功");
        }
        return ResultJson.Forbidden("添加失败");
    }


    public ResultJson updateCourse(Course course) throws Exception {
        if(!StringUtils.isNotBlank(course.getC_id())){
            return ResultJson.ParameterError();
        }
        Course exist = courseMapper.selCourseById(course.getC_id());
        if (course.getC_name() != null && "".equals(course.getC_name().trim()))
            exist.setC_name(course.getC_name());
        if (course.getChapter() != null && course.getChapter() != 0)
            exist.setChapter(course.getChapter());
        if(course.getTip() != null && "".equals(course.getTip().trim()))
            exist.setTip(course.getTip());
        int resultCount = courseMapper.updateCourse(exist);
        if(resultCount > 0){
            return ResultJson.Success("修改成功");
        }
        return ResultJson.Forbidden("修改失败");
    }


    public ResultJson findCourse(Course course) throws Exception {
        if(!StringUtils.isNotBlank(course.getC_id())&&!StringUtils.isNotBlank(course.getC_name())){
            return ResultJson.Forbidden("请输入有效查询条件");
        }
        Course course1 = courseMapper.findCourse(course);
        if(course1==null){
            return ResultJson.Forbidden("没有查询结果");
        }
        return ResultJson.Success(course1);
    }

    @Override
    public List<GradeExample> gradesNotInExam(String t_num, String course) throws Exception {
        Date now = new Date();
        //查找课程是否存在
        Course exist = courseMapper.selCourseById(course);
        if (exist == null)
            throw new ParamException("课程不存在！course：" + course);
        //教师教授的班级
        String sgrade = teacherMapper.selGradesByCourse(t_num, course);
        String[] grades = sgrade.split(",");
        long time= 8 * 7 * 24 * 3600 * 100;
        //八周内参加过该门课程考试的班级
        List<String> gradeList = examMapper.selGradesByTC(t_num, course,new Date(now.getTime()-time));
        if (gradeList.size() != 0) {
            String already = "";
            if (gradeList.size()==1){
                already += gradeList.get(0);
            }else {
                for (int i = 0; i < gradeList.size(); i++) {
                    already += gradeList.get(i);
                    if (i < gradeList.size() - 1)
                        already += ",";
                }
            }
            return gradeMapper.selGradesNotInExam(t_num, course, grades, already);
        }else {
            return gradeMapper.selGradesNotInExam(t_num, course, grades,null);
        }
    }


    @Override
    public List<GradeExample> selGradesNotTeach(String course, String teacher, String dept,String year) throws Exception {
        if (course == null || "".equals(course.trim()))
            throw new ParamException("<查找授课班级>：课程为空！");
        if (teacher == null || "".equals(teacher.trim()))
            throw new ParamException("<查找授课班级>：教师为空！");
        if (dept == null || "".equals(dept.trim()))
            throw new ParamException("<查找授课班级>：专业为空！");
        if (year == null || "".equals(year.trim()))
            throw new ParamException("<查找授课班级>：请选择年级！");
        String grade = null;
        try {
            grade = teacherMapper.selGradesByDeptCourse(teacher, dept, course, year);
        } catch (NumberFormatException nfe) {
            throw new ParamException("<查找授课班级>：年级出错");
        }
        List<GradeExample> result = gradeMapper.selGradesNotTeach(grade,dept,year);
        return result;
    }

    @Override
    public List<GradeExample> addGrades(MultipartFile multipartFile) throws Exception {
        ReadGradeExcel readGradeExcel = new ReadGradeExcel();
        List<GradeExample> results = readGradeExcel.getExcelInfo(multipartFile);
        Integer max = Integer.parseInt(gradeMapper.biggestId());
        for (int i = 0;i < results.size();i ++) {
            results.get(i).setG_id(max + i+1 +"");
        }
        Integer result = gradeMapper.addGrades(results);
        if (result < results.size())
            throw new BusinessException("<添加班级>：添加失败！应添加 "+results.size()+"条，实际添加"+result+"条");
        return null;
    }

    @Override
    public ResultJson addGrade(Grade grade) throws Exception{
        if(grade.getG_year()==null||grade.getG_class()==null){
            throw new BusinessException("请输入年级或班级");
        }
        grade.setG_id(gradeMapper.biggestId()+1);
        int resultCount = gradeMapper.addGrade(grade);
        if (resultCount != 1){
            throw new BusinessException("添加失败");
        }
        return ResultJson.Success("添加成功");
    }

    @Override
    public ResultJson deleteGrade(String g_id) throws Exception {
        int resultCount = gradeMapper.delGrade(g_id);
        if(resultCount != 1){
            throw new BusinessException("删除失败");
        }
        return ResultJson.Success("删除成功");
    }


}