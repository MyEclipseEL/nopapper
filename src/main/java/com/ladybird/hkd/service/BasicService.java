package com.ladybird.hkd.service;

import com.ladybird.hkd.model.pojo.Course;
import com.ladybird.hkd.model.pojo.Department;
import com.ladybird.hkd.model.pojo.Faculty;



public interface BasicService {

    void addCourse(Course course);

    void delCourse(Integer c_id);

    void updateCourse(Course course);

    Course findCourse(Course course);



    void addDept(Department department);

    void delDept(String dept_num);

    void updateDept(Department department);

    Department findDept(Department department);


    void addFaculty(Faculty faculty);

    void delFaculty(String fac_num);

    void updateFaculty(Faculty faculty);

    Faculty findFaculty(Faculty faculty);
}
