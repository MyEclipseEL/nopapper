package com.ladybird.hkd.service;

import com.ladybird.hkd.exception.BusinessException;
import com.ladybird.hkd.model.json.StudentJsonIn;
import com.ladybird.hkd.model.pojo.Student;

/**
 * Created by 和泉纱雾 on 2019/3/9.
 */

public interface StudentService {
    Student login(StudentJsonIn studentJsonIn) throws BusinessException,Exception;
}
