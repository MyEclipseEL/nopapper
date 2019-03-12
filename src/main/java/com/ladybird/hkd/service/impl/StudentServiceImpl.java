package com.ladybird.hkd.service.impl;

import com.ladybird.hkd.exception.BusinessException;
import com.ladybird.hkd.mapper.PaperlessItemMapper;
import com.ladybird.hkd.mapper.StudentMapper;
import com.ladybird.hkd.model.json.StudentJsonIn;
import com.ladybird.hkd.model.pojo.Score;
import com.ladybird.hkd.model.pojo.Student;
import com.ladybird.hkd.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Shen
 * @description: 学生service层实现类
 * @create: 2019-03-10
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    @Autowired
    private PaperlessItemMapper paperlessItemMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Student login(StudentJsonIn studentJsonIn) throws BusinessException,Exception {

        Student student = studentMapper.findByNumAndPwd(studentJsonIn);
        if (student == null) {
            throw new BusinessException("请检查您的信息，确定学号、密码、身份证正确");
        }

        return student;
    }

    @Override
    public void checkInScore(Score param) throws Exception{
        if (param == null) {
            throw new Exception();
        }
        paperlessItemMapper.checkInScore(param);
    }
}
