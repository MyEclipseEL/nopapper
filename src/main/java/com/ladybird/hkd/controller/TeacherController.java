package com.ladybird.hkd.controller;

import com.ladybird.hkd.annotation.CheckToken;
import com.ladybird.hkd.enums.ExamStateEnum;
import com.ladybird.hkd.exception.BusinessException;
import com.ladybird.hkd.exception.ParamException;
import com.ladybird.hkd.manager.TokenManager;
import com.ladybird.hkd.model.json.*;
import com.ladybird.hkd.model.pojo.Teach;
import com.ladybird.hkd.model.pojo.Teacher;
import com.ladybird.hkd.service.ExamService;
import com.ladybird.hkd.service.TeacherService;
import com.ladybird.hkd.util.ConstConfig;
import com.ladybird.hkd.util.JsonUtil;
import com.ladybird.hkd.util.ParamUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;

import java.util.Date;
import java.util.List;

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
    private ExamService examService;
    @Autowired
    private TokenManager manager;


    @ApiOperation("教师登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "t_num",value = "教师工号",required = true),
            @ApiImplicitParam(name = "t_pwd",value = "登陆密码",required = true)
    })
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

    @ApiOperation("获取考试场次")
    @CheckToken
    @ResponseBody
    @RequestMapping("/exams")
    public Object findExams(NativeWebRequest request) throws Exception{
        //获取登陆教师的信息
        String teacherJson = (String) request.getAttribute(ConstConfig.CURRENT_OBJECT, RequestAttributes.SCOPE_REQUEST);
        if (teacherJson == null) {
            return ResultJson.ServerException();
        }
        //转为对象
        TeacherJsonOut teacherJsonOut = JsonUtil.jsonToPojo(teacherJson, TeacherJsonOut.class);
        //查找考试
        Teach teach = teacherService.checkOutCourse(teacherJsonOut.getT_num());
        if (teach == null)
            throw new BusinessException("没有您的授课记录！");
        String[] grades = teach.getGrade().split("\\ ");
        if (grades.length == 0) {
            throw new Exception();
        }
        //得到考试列表
        List<ExamJsonOut> list = examService.checkOutByCourseGrades(teach.getCourse(),grades);

        return list;
    }

    @ApiOperation("开始考试，设置倒计时开始")
    @ApiImplicitParam(name = "exam",value = "考试号",required = true)
    @CheckToken
    @RequestMapping("/beginExam")
    @ResponseBody
    public Object beginExam(String exam) throws Exception{
        if (ParamUtils.stringIsNull(exam))
            throw new ParamException("考试号没有传！");
        examService.changeStateAndBegin(exam, ExamStateEnum.BEGIN.getCode());
        return ResultJson.Success();
    }

}
