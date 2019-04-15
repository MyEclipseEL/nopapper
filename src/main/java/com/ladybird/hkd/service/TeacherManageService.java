package com.ladybird.hkd.service;

import com.ladybird.hkd.model.json.ResultJson;
import com.ladybird.hkd.model.pojo.Teacher;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TeacherManageService {
    ResultJson selectTeacher(Teacher teacher,String faculty) throws Exception;

    ResultJson addTeacher(Teacher teacher) throws Exception;

    ResultJson updateTeacher(Teacher teacher) throws Exception;

    ResultJson deleteTeacher(String t_num) throws Exception;

    List<Teacher> uploadTeacher(MultipartFile multipartFile) throws Exception;





    ResultJson teaching(String t_num) throws Exception;
}
