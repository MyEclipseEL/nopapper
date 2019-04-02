package com.ladybird.hkd.service;

import com.ladybird.hkd.model.json.TeacherJsonOut;
import com.ladybird.hkd.model.pojo.Course;
import com.ladybird.hkd.model.pojo.Teach;
import com.ladybird.hkd.model.pojo.Teacher;

import java.util.List;

/**
 * Created by 和泉纱雾 on 2019/3/12.
 */
public interface TeacherService {
    TeacherJsonOut login(Teacher teacher) throws Exception;

    Teach checkOutTeaches(String t_num) throws Exception;

    List<Course> checkOutCourseByNum(String t_num) throws Exception;
}
