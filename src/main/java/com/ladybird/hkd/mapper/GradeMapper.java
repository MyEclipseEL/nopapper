package com.ladybird.hkd.mapper;

import com.ladybird.hkd.model.example.GradeExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface GradeMapper {

    int addGrade(GradeExample gradeExample);

    int delGrade(Integer g_id);

    int updateGrade(GradeExample gradeExample);

    GradeExample findGrade(GradeExample gradeExample);

    GradeExample findGradeByYandC(GradeExample gradeExample);

    List<GradeExample> selGradesNotInExam(@Param("t_num") String t_num, @Param("course") String course,
                                          @Param("grades") String[] grades, @Param("already") String already) throws Exception;
}
