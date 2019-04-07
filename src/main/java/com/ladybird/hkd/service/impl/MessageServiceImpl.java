package com.ladybird.hkd.service.impl;


import com.ladybird.hkd.mapper.*;

import com.ladybird.hkd.model.example.Grade;
import com.ladybird.hkd.model.json.ResultJson;

import com.ladybird.hkd.model.pojo.Course;
import com.ladybird.hkd.model.pojo.Department;
import com.ladybird.hkd.model.pojo.Faculty;
import com.ladybird.hkd.service.MessageService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public ResultJson selectAllDept(Faculty faculty) throws Exception {
        if(faculty == null){
            return ResultJson.ParameterError();
        }
        List<Department> departments = deptMapper.selectAllDept(faculty);
        if(departments == null || departments.isEmpty()){

             return ResultJson.Forbidden("查询错误");
        }
        return ResultJson.Success(departments);
    }


    public ResultJson addDept(Department department) throws Exception {
        if(!StringUtils.isNotBlank(department.getDept_num())||!StringUtils.isNotBlank(department.getDept_name())||!StringUtils.isNotBlank(department.getFaculty().getFac_num())){
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
        if(!StringUtils.isNotBlank(department.getDept_num())||!StringUtils.isNotBlank(department.getDept_name())||!StringUtils.isNotBlank(department.getFaculty().getFac_num())){
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
        Department department1 = deptMapper.findDept(department);
        if(department1==null){
            return ResultJson.Forbidden("查询失败");
        }
        return ResultJson.Success(department1);
    }


    public ResultJson selectAllCourse() throws Exception {
        List<Course> courses = courseMapper.selectAllCourse();
        if(courses==null||courses.isEmpty()){
            return ResultJson.Forbidden("查询错误");
        }
        return ResultJson.Success(courses);
    }


    public ResultJson addCourse(Course course) throws Exception {
        if(!StringUtils.isNotBlank(course.getC_id())||!StringUtils.isNotBlank(course.getC_name())){
            return ResultJson.ParameterError();
        }
        int resultCount = courseMapper.selectCourseByPrimary(course.getC_id());
        if(resultCount > 0){
            this.updateCourse(course);
        }
        resultCount = courseMapper.addCourse(course);
        if (resultCount > 0){
            return ResultJson.Success("添加成功");
        }
        return ResultJson.Forbidden("添加失败");
    }


    public ResultJson updateCourse(Course course) throws Exception {
        if(!StringUtils.isNotBlank(course.getC_id())||!StringUtils.isNotBlank(course.getC_name())){
            return ResultJson.ParameterError();
        }
        int resultCount = courseMapper.updateCourse(course);
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
    public List<Grade> gradesNotInExam(String t_num, String course) throws Exception {
        Date now = new Date();
        String sgrade = teacherMapper.selGradesByCourse(t_num, course);
        String[] grades = sgrade.split(",");
        //判断八周内是否参加过考试
        long time= 8 * 7 * 24 * 3600 * 100;
        return gradeMapper.selGradesNotInExam(t_num,course,new Date(now.getTime()-time),grades);
    }





}
