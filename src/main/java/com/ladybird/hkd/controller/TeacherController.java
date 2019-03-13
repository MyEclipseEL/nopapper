package com.ladybird.hkd.controller;

import com.ladybird.hkd.annotation.CheckToken;
import com.ladybird.hkd.exception.ParamException;
import com.ladybird.hkd.manager.TokenManager;
import com.ladybird.hkd.model.json.ResultJson;
import com.ladybird.hkd.model.json.TeacherJsonIn;
import com.ladybird.hkd.model.json.TeacherJsonOut;
import com.ladybird.hkd.model.json.TokenJsonOut;
import com.ladybird.hkd.model.pojo.Teacher;
import com.ladybird.hkd.service.TeacherService;
import com.ladybird.hkd.util.ConstConfig;
import com.ladybird.hkd.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;

/**
 * @author Shen
 * @description: 老师管理类
 * @create: 2019-03-13
 */
@RequestMapping("/teacher")
@Controller
public class TeacherController extends BaseController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TokenManager manager;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Object login(Teacher teacher) throws Exception {
        try {
            TeacherJsonIn.ValidNumPwd(teacher);
        } catch (ParamException pe) {
            return ResponseEntity.badRequest().body(ResultJson.Forbidden(pe.getLocalizedMessage()));
        }
        TeacherJsonOut teacherJsonOut = teacherService.login(teacher);
        //创建token
        String accessToken = manager.createToken(teacherJsonOut);
        //创建refreshToken
        String reToken = manager.createReToken(accessToken);
        return new TokenJsonOut(accessToken, reToken);
    }

    @CheckToken
    public Object beginExam(String exam,NativeWebRequest request) throws Exception{
        //获取登陆教师的信息
        String teacherJson = (String) request.getAttribute(ConstConfig.CURRENT_OBJECT, RequestAttributes.SCOPE_REQUEST);
        if (teacherJson == null) {
            return ResultJson.ServerException();
        }
        //转为对象
        TeacherJsonOut teacherJsonOut = JsonUtil.jsonToPojo(teacherJson, TeacherJsonOut.class);
        //查找考试

        //判断考试状态

        //未开始的考试可以开始考试
        return null;
    }

}
