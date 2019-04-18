package com.ladybird.hkd.service;

import com.ladybird.hkd.model.example.StudentExample;
import com.ladybird.hkd.model.json.ResultJson;
import com.ladybird.hkd.model.pojo.Student;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentManageService {

    ResultJson selectAllGrade(int gradelow) throws Exception;

    ResultJson selectStudent(Student student, int pageNum, int pageSize) throws Exception;

    ResultJson addStudent(Student student) throws Exception;

    ResultJson deleteStudent(String stu_num) throws Exception;

    ResultJson updateStudent(Student student) throws Exception;

    ResultJson findStudent(Student student) throws Exception;

    ResultJson backGrade(String dept) throws Exception;



   List<StudentExample> uploadStudent(MultipartFile file) throws Exception;
}
