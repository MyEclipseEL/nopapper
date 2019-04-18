package com.ladybird.hkd.service;

import com.ladybird.hkd.model.example.TeachExample;
import com.ladybird.hkd.model.json.TeacherJsonOut;
import com.ladybird.hkd.model.pojo.Course;
import com.ladybird.hkd.model.pojo.Teach;
import com.ladybird.hkd.model.pojo.Teacher;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by 和泉纱雾 on 2019/3/12.
 */
public interface TeacherService {
    TeacherJsonOut login(Teacher teacher) throws Exception;

    List<Teach> checkOutTeaches(String t_num) throws Exception;

    List<Course> checkOutCourseByNum(String t_num) throws Exception;

    List<TeachExample> checkOutTeachExms(String teacher, String course) throws Exception;

    TeachExample checkInTeach(TeachExample teach) throws Exception;

    TeachExample changeGrade(String teach_id, String[] grade,String teacher,String dept,String course) throws Exception;

    void delTeach(String teach_id) throws Exception;

    List<TeachExample> uploadTeaches(MultipartFile multipartFile) throws Exception;
}
