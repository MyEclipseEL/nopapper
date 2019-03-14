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

@Service
public class BasicServiceImpl implements BasicService {
    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private FacultyMapper facultyMapper;

    @Autowired
    private GradeMapper gradeMapper;
    //课程
    @Override
    public void addCourse(Course course) {

        courseMapper.addCourse(course);
    }

    @Override
    public void delCourse(Integer c_id) {

        courseMapper.delCourse(c_id);
    }

    @Override
    public void updateCourse(Course course) {

        courseMapper.updateCourse(course);
    }

    @Override
    public Course findCourse(Course course) {
        return courseMapper.findCourse(course);
    }

    @Override
    public Course findCourseByName(String c_name) {
        return courseMapper.findCourseByName(c_name);
    }




    @Override
    public void addDept(Department department) {
        deptMapper.addDept(department);
    }

    @Override
    public void delDept(String dept_num) {

        deptMapper.delDept(dept_num);
    }

    @Override
    public void updateDept(Department department) {

        deptMapper.updateDept(department);
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
    public void addFaculty(Faculty faculty) {
        facultyMapper.addFaculty(faculty);
    }

    @Override
    public void delFaculty(String fac_num) {

        facultyMapper.delFaculty(fac_num);
    }

    @Override
    public void updateFaculty(Faculty faculty) {

        facultyMapper.updateFaculty(faculty);
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
    public void addGrade(Grade grade) {
        gradeMapper.addGrade(grade);
    }

    @Override
    public void delGrade(Integer g_id) {

        gradeMapper.delGrade(g_id);
    }

    @Override
    public void updateGrade(Grade grade) {
        gradeMapper.updateGrade(grade);
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
