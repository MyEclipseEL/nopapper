package com.ladybird.hkd.service;

import com.ladybird.hkd.model.json.ExamJsonOut;
import com.ladybird.hkd.model.pojo.Exam;
import com.ladybird.hkd.model.pojo.PaperEdit;

import java.sql.Date;
import java.util.List;

/**
 * Created by 和泉纱雾 on 2019/3/12.
 */
public interface ExamService {
    ExamJsonOut selectExamByStuNum(String stu_num) throws Exception;

    int checkExamState(String exam) throws Exception;

    ExamJsonOut checkOutExamById(String exam) throws Exception;

    List<ExamJsonOut> checkOutByCourseGrades(Integer course, String[] grades) throws Exception;

    void changeStateAndBegin(String exam,Integer state) throws Exception;

    void updatePaper(PaperEdit paperEdit) throws Exception;

    PaperEdit checkOutPaper() throws Exception;

    ExamJsonOut addExam(Exam exam) throws Exception;
}
