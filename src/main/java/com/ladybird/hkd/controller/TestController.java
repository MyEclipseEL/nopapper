package com.ladybird.hkd.controller;

import com.alibaba.fastjson.JSONObject;
import com.ladybird.hkd.annotation.CheckToken;
import com.ladybird.hkd.annotation.LoginToken;
import com.ladybird.hkd.exception.BusinessException;
import com.ladybird.hkd.exception.TokenException;
import com.ladybird.hkd.manager.TokenManager;
import com.ladybird.hkd.pojo.Student;
import com.ladybird.hkd.service.TestService;
import com.ladybird.hkd.util.ResultJson;
import com.ladybird.hkd.util.TokenJsonOut;
import com.ladybird.hkd.vo.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by 和泉纱雾 on 2019/3/4.
 */
@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TestService testService;
    @Autowired
    private TokenManager tokenManager;

    @RequestMapping(value = "/go",method = RequestMethod.GET)
    @ResponseBody
    public String test() {
        System.out.println("成功进入controller");
        return "success";

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus
    public Object login(String stu_num, String stu_pwd) {
        Student student = new Student();
        student.setStu_num(stu_num);
        student.setStu_pwd(stu_pwd);
//        ResultObject resultObject = new ResultObject();
//        try {
//            resultObject.setMessage("success!");
//            String token = testService.login(student);
//            return ResponseEntity.ok()
//                    .header("Authorization",token)
//                    .body(resultObject);
//        } catch (Exception e) {
//            resultObject.setMessage(e.getMessage());
//            return ResponseEntity.badRequest()
//                    .header("stu_num",stu_num)
//                    .body(resultObject);
//        }

        try {
            Student result = testService.login(student);
            String uid = stu_num;
            //创建token ， 并设进redis
            String accessToken = tokenManager.createToken(uid);
            //设置refresh token
            String refreshToken = tokenManager.createReToken(accessToken);
            return new TokenJsonOut(accessToken, refreshToken);
        } catch (TokenException te) {
            return ResultJson.TokenRedisException();
        } catch (BusinessException be) {
            return ResultJson.BusinessErroException(be.getLocalizedMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultJson.ServerException();
        }
    }


}
