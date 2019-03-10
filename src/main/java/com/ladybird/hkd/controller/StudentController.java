package com.ladybird.hkd.controller;

import com.ladybird.hkd.annotation.CheckToken;
import com.ladybird.hkd.exception.BusinessException;
import com.ladybird.hkd.exception.ParamException;
import com.ladybird.hkd.exception.TokenException;
import com.ladybird.hkd.manager.TokenManager;
import com.ladybird.hkd.model.json.StudentJsonIn;
import com.ladybird.hkd.model.json.TokenJsonOut;
import com.ladybird.hkd.model.pojo.Student;
import com.ladybird.hkd.service.StudentService;
import com.ladybird.hkd.util.ConstConfig;
import com.ladybird.hkd.model.json.ResultJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;

/**
 * @author Shen
 * @description: 学生控制层
 * @create: 2019-03-20
 */
@Api(tags = "学生管理类")
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TokenManager tokenManager;

    public Object commit(String score, NativeWebRequest request) {
        if (score == null){
            return ResultJson.ParameterError();
        }
        try {
            String uid = (String) request.getAttribute(ConstConfig.CURRENT_OBJECT_ID, RequestAttributes.SCOPE_REQUEST);
            if (uid == null)
                return ResultJson.ServerException();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultJson.Success();
    }

    @ApiOperation(value = "学生登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stu_num", value = "学号", required = true),
            @ApiImplicitParam(name = "stu_pwd", value = "密码", required = true),
            @ApiImplicitParam(name = "stu_ID", value = "身份证", required = true)
    })
    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Object login(StudentJsonIn studentJsonIn) throws Exception {
        try {
            StudentJsonIn.ValidRequiredPwd(studentJsonIn);
        }catch (ParamException pe){
//            return ResultJson.ParameterException(pe.getLocalizedMessage(), studentJsonIn);
            return  ResponseEntity.badRequest().header("failed").body(ResultJson.Forbidden(pe.getLocalizedMessage()));
        }
            Student student = studentService.login(studentJsonIn);
            //创建token
            String accessToken = tokenManager.createToken(student);
            student.setStu_pwd(null);
            //创建refreshToken
            String refreshToken = tokenManager.createReToken(accessToken);
            return new TokenJsonOut(accessToken, refreshToken);
    }

    @CheckToken
    public Object getItems() {

    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Object handleException(Exception e) {

        if (e.getClass() == ParamException.class) {
            //TODO 日志记录
            return ResultJson.ParameterException(e.getLocalizedMessage(), null);
        }
        if (e.getClass() == TokenException.class) {
            return ResultJson.TokenRedisException();
        }
        if (e.getClass() == BusinessException.class) {
            return ResultJson.BusinessErrorException(e.getLocalizedMessage(),null);
        }
        return ResultJson.ServerException();
    }


}
