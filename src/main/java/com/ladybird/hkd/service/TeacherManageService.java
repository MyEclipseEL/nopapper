package com.ladybird.hkd.service;

import com.ladybird.hkd.model.json.ResultJson;
import com.ladybird.hkd.model.pojo.Teacher;

public interface TeacherManageService {
    ResultJson selectTeacher(Teacher teacher);
}
