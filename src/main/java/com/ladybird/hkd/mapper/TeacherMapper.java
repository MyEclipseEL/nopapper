package com.ladybird.hkd.mapper;

import com.ladybird.hkd.model.json.TeacherJsonOut;
import com.ladybird.hkd.model.pojo.Course;
import com.ladybird.hkd.model.pojo.Teach;
import com.ladybird.hkd.model.pojo.Teacher;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by 和泉纱雾 on 2019/3/12.
 */
@Component
public interface TeacherMapper {
    TeacherJsonOut validNumPwd(Teacher teacher) throws Exception;

    Teach checkOutTeaches(@Param("teacher") String t_num) throws Exception;

    String selGradesByDeptCourse(@Param("t_num") String t_num,@Param("dept_num") String dept_num,@Param("c_id") String c_id) throws Exception;

    String selGradesByCourse(@Param("t_num") String t_num,@Param("c_id") String c_id) throws Exception;


    List<Course> checkOutCourseByNum(@Param("t_num") String t_num) throws Exception;
}
