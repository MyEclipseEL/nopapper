package com.ladybird.hkd.mapper;


import com.ladybird.hkd.model.example.GradeExample;
import com.ladybird.hkd.model.pojo.Grade;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
@Component
public interface GradeMapper {

    int addGrade(Grade grade);

    int delGrade(String g_id);

    int updateGrade(Grade grade);

    Grade findGrade(Grade grade);

    Grade findGradeByYandC(Grade grade);

    List<GradeExample> selGradesNotInExam(@Param("t_num") String t_num, @Param("course") String course,
                                          @Param("grades") String[] grades, @Param("already") String already) throws Exception;

    List<Grade> findGradeByDept(@Param("dept") String dept);

    Grade findGradeById(@Param("g_id") String g_id);
}
