package com.ladybird.hkd.service.impl;

import com.ladybird.hkd.mapper.ExamMapper;
import com.ladybird.hkd.model.json.ExamJsonOut;
import com.ladybird.hkd.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        System.out.println(examJsonOut.getPre_time());
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
}
