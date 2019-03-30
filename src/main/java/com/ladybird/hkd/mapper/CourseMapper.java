package com.ladybird.hkd.mapper;

import com.ladybird.hkd.model.pojo.Course;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by 和泉纱雾 on 2019/3/11.
 */
@Component
public interface CourseMapper {

    String findNameById(@Param("c_id") String c_id) throws Exception;

    void addCourse(Course course);

    void delCourse(@Param("c_id") String c_id) throws Exception;

    void updateCourse(Course course);

    Course findCourse(Course course);

    Course findCourseByName(@Param("c_name")String c_name);

    List<Course> selByTeacher(@Param("t_num") String t_num) throws Exception;
}
