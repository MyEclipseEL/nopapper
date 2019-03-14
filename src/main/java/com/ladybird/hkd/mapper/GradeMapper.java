package com.ladybird.hkd.mapper;

import com.ladybird.hkd.model.pojo.Grade;

public interface GradeMapper {

    void addGrade(Grade grade);

    void delGrade(Integer g_id);

    void updateGrade(Grade grade);

    Grade findGrade(Grade grade);

    Grade findGradeByYandC(Grade grade);
}
