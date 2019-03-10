package com.ladybird.hkd.service.impl;

import com.ladybird.hkd.exception.BusinessException;
import com.ladybird.hkd.mapper.StudentMapper;
import com.ladybird.hkd.model.json.StudentJsonIn;
import com.ladybird.hkd.model.pojo.Student;
import com.ladybird.hkd.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Shen
 * @description: 学生service层实现类
 * @create: 2019-03-10
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Student login(StudentJsonIn studentJsonIn) throws BusinessException,Exception {

        Student student = studentMapper.findByNumAndPwd(studentJsonIn);
        if (student == null) {
            throw new BusinessException("请检查您的信息，确定用户名、密码、身份证正确");
        }

        return student;
    }
}
