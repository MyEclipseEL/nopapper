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


    void delCourse(@Param("c_id") String c_id) throws Exception;


    Course findCourseByName(@Param("c_name")String c_name);

    int checkCourse(Integer c_id);

    int addCourse(Course course) throws Exception;
    List<Course> selectAllCourse() throws Exception;
    int updateCourse(Course course) throws Exception;

    int selectCourseByPrimary(String c_id) throws Exception;

    Course selCourseById(@Param("c_id") String c_id) throws Exception;

    Course findCourse(Course course) throws Exception;

    List<Course> selByTeacher(@Param("t_num") String t_num) throws Exception;
}
