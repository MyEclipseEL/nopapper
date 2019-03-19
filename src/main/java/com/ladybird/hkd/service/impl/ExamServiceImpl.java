package com.ladybird.hkd.service.impl;

import com.ladybird.hkd.mapper.ExamMapper;
import com.ladybird.hkd.model.json.ExamJsonOut;
import com.ladybird.hkd.model.pojo.PaperEdit;
import com.ladybird.hkd.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ExamJsonOut selectExamByStuNum(String stu_num) throws Exception {
        ExamJsonOut examJsonOut = examMapper.selectExamByStuNum(stu_num);
        examJsonOut.setPre_time(new Date(examJsonOut.getPre_time().getTime()/1000));
        examJsonOut.setBegin_time(new Date(examJsonOut.getBegin_time().getTime()/1000));
        return examJsonOut;
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
    public List<ExamJsonOut> checkOutByCourseGrades(Integer course, String[] gradesString) throws Exception {
        int[] grades = new int[gradesString.length];
        for (int i=0;i<gradesString.length;i++) {
            grades[i] = Integer.parseInt(gradesString[i]);
        }
        List<ExamJsonOut> examJsonOuts = examMapper.checkOutByCourseGrades(course, grades);
        for (ExamJsonOut e : examJsonOuts) {
            e.setPre_time(new Date(e.getPre_time().getTime()/1000));
            e.setBegin_time(new Date(e.getBegin_time().getTime()/1000));
        }
        return examJsonOuts;
    }

    @Override
    public void changeStateAndBegin(String exam,Integer state) throws Exception {
        Date date = new Date();
        examMapper.changeStateAndBegin(exam,date,state);
    }

    @Override
    public void updatePaper(PaperEdit paperEdit) throws Exception {
        examMapper.updatePaper(paperEdit);
    }

    @Override
    public PaperEdit checkOutPaper() throws Exception {
        return examMapper.checkOutPaper();
    }

}
