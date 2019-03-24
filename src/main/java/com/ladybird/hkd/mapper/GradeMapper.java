package com.ladybird.hkd.mapper;

import com.ladybird.hkd.model.pojo.Grade;

public interface GradeMapper {

    int addGrade(Grade grade);

    int delGrade(Integer g_id);

    int updateGrade(Grade grade);

    Grade findGrade(Grade grade);

    Grade findGradeByYandC(Grade grade);
}
