package com.ladybird.hkd.service;

import com.ladybird.hkd.model.json.ExamJsonIn;
import com.ladybird.hkd.model.json.ExamJsonOut;
import com.ladybird.hkd.model.pojo.Exam;
import com.ladybird.hkd.model.pojo.PaperEdit;
import com.ladybird.hkd.model.pojo.Student;
import com.ladybird.hkd.model.pojo.Teach;

import java.sql.Date;
import java.util.List;

/**
 * Created by 和泉纱雾 on 2019/3/12.
 */
public interface ExamService {
    List<ExamJsonOut> selectExamByStu(Student student) throws Exception;

    int checkExamState(String exam) throws Exception;

    ExamJsonOut checkOutExamById(String exam) throws Exception;

    List<ExamJsonOut> checkOutByTeach(Teach teach) throws Exception;

    void changeStateAndBegin(String[] exams,Integer state) throws Exception;

    void updatePaper(PaperEdit paperEdit) throws Exception;

    PaperEdit checkOutPaper() throws Exception;

    List<ExamJsonOut> addExams(ExamJsonIn exam) throws Exception;
}
