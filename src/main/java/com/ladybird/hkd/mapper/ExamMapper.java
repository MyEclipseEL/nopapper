package com.ladybird.hkd.mapper;

import com.ladybird.hkd.model.json.ExamJsonOut;
import com.ladybird.hkd.model.pojo.Exam;
import com.ladybird.hkd.model.pojo.PaperEdit;
import com.ladybird.hkd.model.pojo.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by 和泉纱雾 on 2019/3/12.
 */
@Component
public interface ExamMapper {
    List<ExamJsonOut> selectExamByStu(Student student) throws Exception;

    int checkExamState(@Param("exam_id") String exam_id) throws Exception;

    ExamJsonOut checkOutExamById(@Param("exam_id") String exam_id) throws Exception;

    List<ExamJsonOut> checkOutExamByIds(@Param("ids") List<String> ids) throws Exception;

    List<ExamJsonOut> checkOutByCourseGradesDept(@Param("course") String course,@Param("grades") String[] grades,@Param("dept") String dept) throws Exception;

    void changeStateAndBegin(String[] ids, @Param("begin_time") Date date, @Param("state") Integer state) throws Exception;

    void updatePaper(PaperEdit paperEdit) throws Exception;

    PaperEdit checkOutPaper() throws Exception;

    void addExams(@Param("exams") List<Exam> exams) throws Exception;

}
