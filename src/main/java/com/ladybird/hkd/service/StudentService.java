package com.ladybird.hkd.service;

import com.ladybird.hkd.exception.BusinessException;
import com.ladybird.hkd.model.example.ScoreExample;
import com.ladybird.hkd.model.json.StudentJsonIn;
import com.ladybird.hkd.model.pojo.Score;
import com.ladybird.hkd.model.pojo.Student;

import java.util.List;

/**
 * Created by 和泉纱雾 on 2019/3/9.
 */

public interface StudentService {
    Student login(StudentJsonIn studentJsonIn) throws BusinessException,Exception;

    void checkInScore(Score param) throws Exception;

    List<ScoreExample> checkOutScores(String t_num,String course, String faculty, String dept, String year,String clazz) throws Exception;
}
