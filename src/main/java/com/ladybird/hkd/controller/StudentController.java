package com.ladybird.hkd.controller;

import com.ladybird.hkd.annotation.CheckGroup;
import com.ladybird.hkd.annotation.CheckToken;
import com.ladybird.hkd.exception.AuthorizationException;
import com.ladybird.hkd.exception.BusinessException;
import com.ladybird.hkd.exception.ParamException;
import com.ladybird.hkd.manager.TokenManager;
import com.ladybird.hkd.model.example.AdminExample;
import com.ladybird.hkd.model.example.ScoreExample;
import com.ladybird.hkd.model.json.StudentJsonIn;
import com.ladybird.hkd.model.json.TeacherJsonOut;
import com.ladybird.hkd.model.json.TokenJsonOut;
import com.ladybird.hkd.model.pojo.Score;
import com.ladybird.hkd.model.pojo.Student;
import com.ladybird.hkd.service.ExamService;
import com.ladybird.hkd.service.StudentService;
import com.ladybird.hkd.model.json.ResultJson;
import com.ladybird.hkd.util.ConstConfig;
import com.ladybird.hkd.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;

import java.util.List;

/**
 * @author Shen
 * @description: 学生控制层
 * @create: 2019-03-20
 */
@CrossOrigin
@Api(value = "学生controller",tags = "学生管理类")
@Controller
@RequestMapping("/student")
public class StudentController extends BaseController{

    @Autowired
    private StudentService studentService;
    @Autowired
    private ExamService examService;

    @Autowired
    private TokenManager tokenManager;

    @ApiOperation(value = "学生登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stu_num", value = "学号", required = true),
            @ApiImplicitParam(name = "stu_pwd", value = "密码", required = true),
            @ApiImplicitParam(name = "stu_ID", value = "身份证", required = true)
    })
    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Object login(StudentJsonIn studentJsonIn) throws Exception {

        //TODO 判断是否已经登陆
        try {
            StudentJsonIn.ValidRequiredPwd(studentJsonIn);
        }catch (ParamException pe){
//            return ResultJson.ParameterException(pe.getLocalizedMessage(), studentJsonIn);
            return  ResponseEntity.badRequest().header("failed").body(ResultJson.Forbidden(pe.getLocalizedMessage()));
        }
        Student student = studentService.login(studentJsonIn);
        student.setStu_pwd(null);
        //创建token
        String accessToken = tokenManager.createToken(student);

        //创建refreshToken
        String refreshToken = tokenManager.createReToken(accessToken);
        return new TokenJsonOut(accessToken, refreshToken);
    }

    //TODO 导出成绩
//    @CheckGroup
    @CheckToken
    @ResponseBody
    @RequestMapping(value = "/checkOutScores")
//    教师导出自己教授的学生的成绩
    public Object checkOutScores(NativeWebRequest request,String t_num,String course, String faculty, String dept, String year, String clazz) throws Exception {
        //获取登陆教师的信息
        String teacherJson = (String) request.getAttribute(ConstConfig.CURRENT_OBJECT, RequestAttributes.SCOPE_REQUEST);
        TeacherJsonOut teacherJsonOut = null;
        if (teacherJson == null) {
            throw new AuthorizationException("非法登陆！");
        }else {
            //转为对象
            teacherJsonOut = JsonUtil.jsonToPojo(teacherJson, TeacherJsonOut.class);
            if (teacherJsonOut != null && teacherJsonOut.getGroup_id().getGroup_id().equals("2")) {
                try {
                    t_num = teacherJsonOut.getT_num();
                } catch (NullPointerException npe) {
                    throw new BusinessException("token用户信息错误！");
                }
            } else if (teacherJsonOut == null) {
                AdminExample example = JsonUtil.jsonToPojo(teacherJson, AdminExample.class);
                if (example == null)
                    throw new AuthorizationException("非法登陆！");
            }
        }
        List<ScoreExample> scoreList = studentService.checkOutScores(t_num,course, faculty, dept, year,clazz);
        return ResultJson.Success(scoreList);
    }
}
