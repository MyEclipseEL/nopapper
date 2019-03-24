package com.ladybird.hkd.service.impl;

import com.ladybird.hkd.mapper.CourseMapper;
import com.ladybird.hkd.mapper.DeptMapper;
import com.ladybird.hkd.mapper.FacultyMapper;
import com.ladybird.hkd.mapper.GradeMapper;
import com.ladybird.hkd.model.pojo.Course;
import com.ladybird.hkd.model.pojo.Department;
import com.ladybird.hkd.model.pojo.Faculty;
import com.ladybird.hkd.model.pojo.Grade;
import com.ladybird.hkd.service.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("basicService")
public class BasicServiceImpl implements BasicService {
    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private FacultyMapper facultyMapper;

    @Autowired
    private GradeMapper gradeMapper;
    //课程c

    @Override
    public int addCourse(Course course) {
        return courseMapper.addCourse(course);
    }

    @Override
    public int delCourse(Integer c_id) {

        return courseMapper.delCourse(c_id);
    }

    @Override
    public int updateCourse(Course course) {

        return courseMapper.updateCourse(course);
    }

    @Override
    public Course findCourse(Course course) {
        return courseMapper.findCourse(course);
    }

    @Override
    public Course findCourseByName(String c_name) {
        return courseMapper.findCourseByName(c_name);
    }

    public int checkCourse(Integer c_id){
        return courseMapper.checkCourse(c_id);
    }



    @Override
    public int addDept(Department department) {
        return deptMapper.addDept(department);
    }

    @Override
    public int delDept(String dept_num) {

       return  deptMapper.delDept(dept_num);
    }

    @Override
    public int updateDept(Department department) {

        return deptMapper.updateDept(department);
    }

    @Override
    public Department findDept(Department department) {

        return deptMapper.findDept(department);
    }

    @Override
    public Department findDeptByName(String dept_name) {
        return deptMapper.findDeptByName(dept_name);
    }

    @Override
    public Department findDeptByNum(String dept_num) {
        return deptMapper.findDeptByNum(dept_num);
    }



    @Override
    public int addFaculty(Faculty faculty) {
        return facultyMapper.addFaculty(faculty);
    }

    @Override
    public int delFaculty(String fac_num) {

        return facultyMapper.delFaculty(fac_num);
    }

    @Override
    public int updateFaculty(Faculty faculty) {

        return facultyMapper.updateFaculty(faculty);
    }

    @Override
    public Faculty findFaculty(Faculty faculty) {
        return facultyMapper.findFaculty(faculty);
    }

    @Override
    public Faculty findFacultyByName(String fac_name) {
        return facultyMapper.findFacultyByName(fac_name);
    }

    @Override
    public Faculty findFacultyByNum(String fac_num) {
        return facultyMapper.findFacultyByNum(fac_num);
    }


    @Override
    public int addGrade(Grade grade) {
        return gradeMapper.addGrade(grade);
    }

    @Override
    public int delGrade(Integer g_id) {

        return gradeMapper.delGrade(g_id);
    }

    @Override
    public int updateGrade(Grade grade) {
        return gradeMapper.updateGrade(grade);
    }

    @Override
    public Grade findGrade(Grade grade) {
        return gradeMapper.findGrade(grade);
    }

    @Override
    public Grade findGradeByYandC(Grade grade) {
        return gradeMapper.findGradeByYandC(grade);
    }
}
