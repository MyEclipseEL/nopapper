package com.ladybird.hkd.service;

import com.ladybird.hkd.model.pojo.Course;
import com.ladybird.hkd.model.pojo.Department;
import com.ladybird.hkd.model.pojo.Faculty;
import com.ladybird.hkd.model.pojo.Grade;


public interface BasicService {

    int addCourse(Course course);

    int delCourse(Integer c_id);

    int updateCourse(Course course);

    Course findCourse(Course course);

    Course findCourseByName(String c_name);
    int checkCourse(Integer c_id);



    int addDept(Department department);

    int delDept(String dept_num);

    int updateDept(Department department);

    Department findDept(Department department);

    Department findDeptByName(String dept_name);

    Department findDeptByNum(String dept_num);




    int addFaculty(Faculty faculty);

    int delFaculty(String fac_num);

    int updateFaculty(Faculty faculty);

    Faculty findFaculty(Faculty faculty);


    Faculty findFacultyByName(String fac_name);

    Faculty findFacultyByNum(String fac_num);




    int addGrade(Grade grade);

    int delGrade(Integer g_id);

    int updateGrade(Grade grade);

    Grade findGrade(Grade grade);

    Grade findGradeByYandC(Grade grade);
}
