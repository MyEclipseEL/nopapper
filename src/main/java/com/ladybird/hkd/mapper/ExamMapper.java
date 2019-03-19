package com.ladybird.hkd.mapper;

import com.ladybird.hkd.model.json.ExamJsonOut;
import com.ladybird.hkd.model.pojo.PaperEdit;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by 和泉纱雾 on 2019/3/12.
 */
@Component
public interface ExamMapper {
    ExamJsonOut selectExamByStuNum(@Param("stu_num") String stu_num) throws Exception;

    int checkExamState(@Param("exam_id") String exam_id) throws Exception;

    ExamJsonOut checkOutExamById(@Param("exam_id") String exam_id) throws Exception;

    List<ExamJsonOut> checkOutByCourseGrades(@Param("course") Integer course,@Param("grades") int[] grades) throws Exception;

    void changeStateAndBegin(@Param("exam_id") String exam, @Param("begin_time") Date date, @Param("state") Integer state) throws Exception;

    void updatePaper(PaperEdit paperEdit) throws Exception;

    PaperEdit checkOutPaper() throws Exception;
}
