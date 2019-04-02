package com.ladybird.hkd.service.impl;

import com.ladybird.hkd.exception.BusinessException;
import com.ladybird.hkd.mapper.ExamMapper;
import com.ladybird.hkd.model.json.ExamJsonIn;
import com.ladybird.hkd.model.json.ExamJsonOut;
import com.ladybird.hkd.model.pojo.Exam;
import com.ladybird.hkd.model.pojo.PaperEdit;
import com.ladybird.hkd.model.pojo.Student;
import com.ladybird.hkd.model.pojo.Teach;
import com.ladybird.hkd.service.ExamService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Shen
 * @description: 考试业务层实现类
 * @create: 2019-03-21
 */
@Service
@Transactional
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamMapper examMapper;


    @Override
    public List<ExamJsonOut> selectExamByStu(Student student) throws Exception {
        List<ExamJsonOut> examJsonOuts = examMapper.selectExamByStu(student);
        for (ExamJsonOut examJsonOut:examJsonOuts) {
//            examJsonOut.setPre_time(new Date(examJsonOut.getPre_time().getTime() / 1000));
            examJsonOut.setBegin_time(new Date(examJsonOut.getBegin_time().getTime() / 1000));
        }
        return examJsonOuts;
    }

    @Override
    public int checkExamState(String exam) throws Exception{
        return examMapper.checkExamState(exam);
    }

    @Override
    public ExamJsonOut checkOutExamById(String exam) throws Exception {
        ExamJsonOut examJsonOut = examMapper.checkOutExamById(exam);
        if (examJsonOut == null) {
            throw new Exception();
        }
        return examJsonOut;
    }

    @Override
    public List<ExamJsonOut> checkOutByTeach(Teach teach) throws Exception {
        String[] grades = teach.getGrade().split("\\ ");
        List<ExamJsonOut> examJsonOuts = examMapper.checkOutByCourseGradesDept(teach.getCourse(), grades,teach.getDept());
        for (ExamJsonOut e : examJsonOuts) {
            try {
                e.setBegin_time(new Date(e.getBegin_time().getTime() / 1000));
            } catch (NullPointerException ne) {
            }
        }
        return examJsonOuts;
    }

    @Override
    public void changeStateAndBegin(String[] exams,Integer state) throws Exception {
        Date date = new Date();
        examMapper.changeStateAndBegin(exams,date,state);
    }

    @Override
    public void updatePaper(PaperEdit paperEdit) throws Exception {
        examMapper.updatePaper(paperEdit);
    }

    @Override
    public PaperEdit checkOutPaper() throws Exception {
        return examMapper.checkOutPaper();
    }

    @Override
    public List<ExamJsonOut> addExams(ExamJsonIn exam) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
        String exam_id = format.format(new Date()) + exam.getCourse() + exam.getDept();
        List<String> ids = new ArrayList<>();
        List<Exam> exams = new ArrayList<>();
//        for (Integer i : exam.getGrades()) {
//            Exam e = new Exam();
//            BeanUtils.copyProperties(exam,e);
//            e.setExam_id(exam_id + i);
//            e.setGrade(i);
//            exams.add(e);
//            ids.add(e.getExam_id());
//        }
        try {
            examMapper.addExams(exams);
        } catch (SQLIntegrityConstraintViolationException se) {
            se.printStackTrace();
            throw new BusinessException("该考试已存在！");
        }
        return examMapper.checkOutExamByIds(ids);
    }

}
