package com.ladybird.hkd.mapper;

import com.ladybird.hkd.model.pojo.Grade;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
@Component
public interface GradeMapper {

    void addGrade(Grade grade);

    void delGrade(Integer g_id);

    void updateGrade(Grade grade);

    Grade findGrade(Grade grade);

    Grade findGradeByYandC(Grade grade);

    List<Grade> selGradesNotInExam(@Param("t_num") String t_num,@Param("course") String course,@Param("begin_date") Date begin_date,@Param("grades") String[] grades) throws Exception;
}
