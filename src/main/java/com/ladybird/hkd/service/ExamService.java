package com.ladybird.hkd.service;

import com.ladybird.hkd.model.example.PaperEditExample;
import com.ladybird.hkd.model.json.ExamJsonIn;
import com.ladybird.hkd.model.example.ExamExample;
import com.ladybird.hkd.model.pojo.PaperEdit;
import com.ladybird.hkd.model.pojo.Student;
import com.ladybird.hkd.model.pojo.Teach;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 和泉纱雾 on 2019/3/12.
 */
@Transactional
public interface ExamService {
    List<ExamExample> selectExamByStu(Student student) throws Exception;

    int checkExamState(String exam) throws Exception;

    ExamExample checkOutExamById(String exam) throws Exception;

    List<ExamExample> checkOutByTeach(Teach teach) throws Exception;

    void changeStateAndBegin(String[] exams,Integer state) throws Exception;

    PaperEdit updatePaper(PaperEdit paperEdit) throws Exception;

    List<PaperEditExample> checkOutPaper() throws Exception;

    List<ExamExample> addExams(ExamJsonIn exam) throws Exception;

    ExamExample beginExam(String t_num, String[] grades, String course) throws Exception;
}
