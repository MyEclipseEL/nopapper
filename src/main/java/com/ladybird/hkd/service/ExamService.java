package com.ladybird.hkd.service;

import com.ladybird.hkd.model.json.ExamJsonOut;

/**
 * Created by 和泉纱雾 on 2019/3/12.
 */
public interface ExamService {
    ExamJsonOut selectExamByStuNum(String stu_num) throws Exception;

    int checkExamState(String exam) throws Exception;

    ExamJsonOut checkOutExamById(String exam) throws Exception;
}
