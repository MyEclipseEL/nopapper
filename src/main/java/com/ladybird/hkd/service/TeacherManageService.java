package com.ladybird.hkd.service;

import com.ladybird.hkd.model.json.ResultJson;
import com.ladybird.hkd.model.pojo.Teacher;

public interface TeacherManageService {
    ResultJson selectTeacher(Teacher teacher, String faculty, int pageNum, int pageSize) throws Exception;

    ResultJson addTeacher(Teacher teacher) throws Exception;

    ResultJson updateTeacher(Teacher teacher) throws Exception;

    ResultJson deleteTeacher(String t_num) throws Exception;

    ResultJson teaching(String t_num) throws Exception;
}
