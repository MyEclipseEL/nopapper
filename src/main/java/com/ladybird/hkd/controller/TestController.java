package com.ladybird.hkd.controller;

import com.ladybird.hkd.exception.BusinessException;
import com.ladybird.hkd.exception.TokenException;
import com.ladybird.hkd.manager.TokenManager;
import com.ladybird.hkd.model.json.StudentJsonIn;
import com.ladybird.hkd.model.pojo.Student;
import com.ladybird.hkd.service.TestService;
import com.ladybird.hkd.model.json.ResultJson;
import com.ladybird.hkd.model.json.TokenJsonOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/login")
    @ResponseBody
    public Object login(StudentJsonIn studentJsonIn) {
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
            Student result = testService.login(studentJsonIn);
            String uid = result.getStu_num();
            //创建token ， 并设进redis
            String accessToken = tokenManager.createToken(uid);
            //设置refresh token
            String refreshToken = tokenManager.createReToken(accessToken);
            return new TokenJsonOut(accessToken, refreshToken);
        } catch (TokenException te) {
            return ResultJson.TokenRedisException();
        } catch (BusinessException be) {
            return ResultJson.BusinessErrorException(be.getLocalizedMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultJson.ServerException();
        }
    }



}
