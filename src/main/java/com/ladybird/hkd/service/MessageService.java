package com.ladybird.hkd.service;



import com.ladybird.hkd.model.pojo.Department;
import com.ladybird.hkd.model.example.GradeExample;
import com.ladybird.hkd.model.json.ResultJson;
import com.ladybird.hkd.model.pojo.Course;
import com.ladybird.hkd.model.example.DepartmentExample;
import com.ladybird.hkd.model.pojo.Faculty;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MessageService {


    ResultJson addFaculty(Faculty faculty) throws Exception;

    ResultJson selectAllFaculty() throws Exception;

    ResultJson updateFaculty(Faculty faculty) throws Exception;

    ResultJson findFaculty(Faculty faculty) throws Exception;

    ResultJson selectAllDept(Faculty faculty) throws Exception;

    ResultJson addDept(Department department) throws Exception;

    ResultJson updateDept(Department department) throws Exception;

    ResultJson findDept(Department department) throws Exception;

    ResultJson selectAllCourse() throws Exception;

    ResultJson addCourse(Course course) throws Exception;

    ResultJson updateCourse(Course course) throws Exception;

    ResultJson findCourse(Course course) throws Exception;


    List<GradeExample> gradesNotInExam(String t_num, String course) throws Exception;

    List<GradeExample> selGradesNotTeach(String course, String teacher, String dept,String year) throws Exception;

    List<GradeExample> addGrades(MultipartFile multipartFile) throws Exception;
}
