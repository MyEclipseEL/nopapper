package com.ladybird.hkd.service;

import com.ladybird.hkd.model.json.ResultJson;
import com.ladybird.hkd.model.pojo.Course;
import com.ladybird.hkd.model.example.DepartmentExample;
import com.ladybird.hkd.model.pojo.Faculty;

public interface MessageService {
    ResultJson addFaculty(Faculty faculty) throws Exception;

    ResultJson selectAllFaculty() throws Exception;

    ResultJson updateFaculty(Faculty faculty) throws Exception;

    ResultJson findFaculty(Faculty faculty) throws Exception;

    ResultJson selectAllDept(String faculty) throws Exception;

    ResultJson addDept(DepartmentExample departmentExample) throws Exception;

    ResultJson updateDept(DepartmentExample departmentExample) throws Exception;

    ResultJson findDept(DepartmentExample departmentExample) throws Exception;

    ResultJson selectAllCourse() throws Exception;

    ResultJson addCourse(Course course) throws Exception;

    ResultJson updateCourse(Course course) throws Exception;

    ResultJson findCourse(Course course) throws Exception;
}
