package com.ladybird.hkd.mapper;

import com.ladybird.hkd.model.pojo.Grade;
import com.ladybird.hkd.model.example.GradeExample;
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

    GradeExample selGradeById(@Param("g_id") String grade) throws Exception;

    /**
     * 查询数据库中 包含传参中的几个
     *@param
     *@return:
     *Date: 2019/4/14
     */
    Integer selCountGrade(@Param("grades") String[] grade) throws Exception;

    /**
     * 查找没有在授课表中得班级
     *@param
     *@return:
     *Date: 2019/4/17
     */
    List<GradeExample> selGradesNotTeach(@Param("grade") String grade,@Param("g_year") String g_year,
                                         @Param("dept") String dept) throws Exception;

    Integer addGrades(@Param("grades") List<GradeExample> results) throws Exception;

    Integer addGs(@Param("grades") List<Grade> grades) throws Exception;

    String biggestId() throws Exception;

    List<Grade> findGradeByDept(@Param("dept") String dept);

    Grade findGradeById(@Param("g_id") String g_id);
}
