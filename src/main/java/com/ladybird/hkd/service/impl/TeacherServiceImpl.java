package com.ladybird.hkd.service.impl;

import com.ladybird.hkd.exception.ParamException;
import com.ladybird.hkd.mapper.TeacherMapper;
import com.ladybird.hkd.model.json.TeacherJsonOut;
import com.ladybird.hkd.model.pojo.Teach;
import com.ladybird.hkd.model.pojo.Teacher;
import com.ladybird.hkd.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Shen
 * @description: 教师业务层实现类
 * @create: 2019-03-14
 */
@Service
@Transactional
public class TeacherServiceImpl implements TeacherService{

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public TeacherJsonOut login(Teacher teacher) throws Exception {
        TeacherJsonOut teacherJsonOut = teacherMapper.validNumPwd(teacher);
        if (teacherJsonOut == null)
            throw new ParamException("用户名密码错误！");
        return teacherJsonOut;
    }

    @Override
    public Teach checkOutTeaches(String t_num) throws Exception {
        return teacherMapper.checkOutTeaches(t_num);
    }
}
