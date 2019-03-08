package com.ladybird.hkd.service;

import com.ladybird.hkd.exception.BusinessException;
import com.ladybird.hkd.mapper.StudentMapper;
import com.ladybird.hkd.pojo.Student;
import com.ladybird.hkd.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by 和泉纱雾 on 2019/3/4.
 */
@Service
public class TestService {
    @Autowired
    private StudentMapper studentMapper;

    public String login1(Student student) throws Exception{
        Student result = studentMapper.findByNumAndPwd(student);
        if(result == null){
            throw new BusinessException("用户名密码错误！");
        }else {
            //登录成功 设置jwt
            JWTUtils util = new JWTUtils();
            //设置信息
            Map<String, Object> payload = new HashMap<String, Object>();
            payload.put("id", result.getStu_num());
            String jwt =util.createJWT("jwt", "", 600000,payload);
            return jwt;
        }
    }

    public Student login(Student student) throws BusinessException,Exception {
        Student result = studentMapper.findByNumAndPwd(student);

        if (result == null) {
            throw new BusinessException("用户名密码错误");
        }
        return student;
    }

    public Student checkNum(String stuNum) {
        if (stuNum == null) {
            return null;
        }else {
            return studentMapper.findByNum(stuNum);
        }
    }



}
