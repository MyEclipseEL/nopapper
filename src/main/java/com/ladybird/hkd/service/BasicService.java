package com.ladybird.hkd.service;

import com.ladybird.hkd.model.pojo.Course;
import com.ladybird.hkd.model.pojo.Department;
import com.ladybird.hkd.model.pojo.Faculty;
import com.ladybird.hkd.model.pojo.Grade;

import java.util.List;


public interface BasicService {

    void addCourse(Course course);

    void delCourse(String c_id) throws Exception;

    void updateCourse(Course course);

    Course findCourse(Course course);

    Course findCourseByName(String c_name);




    void addDept(Department department);

    void delDept(String dept_num);

    void updateDept(Department department);

    Department findDept(Department department);

    Department findDeptByName(String dept_name);

    Department findDeptByNum(String dept_num);




    void addFaculty(Faculty faculty);

    void delFaculty(String fac_num);

    void updateFaculty(Faculty faculty);

    Faculty findFaculty(Faculty faculty);


    Faculty findFacultyByName(String fac_name);

    Faculty findFacultyByNum(String fac_num);




    void addGrade(Grade grade);

    void delGrade(Integer g_id);

    void updateGrade(Grade grade);

    Grade findGrade(Grade grade);

    Grade findGradeByYandC(Grade grade);

    List<Grade> gradesNotInExam(String _num,String course) throws Exception;
}
