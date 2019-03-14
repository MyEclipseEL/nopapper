package com.ladybird.hkd.mapper;

import com.ladybird.hkd.model.pojo.Course;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * Created by 和泉纱雾 on 2019/3/11.
 */
@Component
public interface CourseMapper {

    String findNameById(@Param("c_id") Integer c_id);

    void addCourse(Course course);

    void delCourse(@Param("c_id") Integer c_id);

    void updateCourse(Course course);

    Course findCourse(Course course);

    Course findCourseByName(@Param("c_name")String c_name);
}
