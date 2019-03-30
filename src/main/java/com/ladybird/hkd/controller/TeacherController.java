package com.ladybird.hkd.controller;

import com.ladybird.hkd.annotation.CheckToken;
import com.ladybird.hkd.enums.ExamStateEnum;
import com.ladybird.hkd.exception.BusinessException;
import com.ladybird.hkd.exception.ParamException;
import com.ladybird.hkd.manager.TokenManager;
import com.ladybird.hkd.model.json.*;
import com.ladybird.hkd.model.pojo.Course;
import com.ladybird.hkd.model.pojo.Grade;
import com.ladybird.hkd.model.pojo.Teach;
import com.ladybird.hkd.model.pojo.Teacher;
import com.ladybird.hkd.service.BasicService;
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
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(value = "*")
@RequestMapping("/teacher")
@Controller
public class TeacherController extends BaseController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ExamService examService;
    @Autowired
    private TokenManager manager;
    @Autowired
    private BasicService basicService;


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

    @ApiOperation( "获取考试场次")
    @CheckToken
    @ResponseBody
    @RequestMapping(value = "/exams",method = RequestMethod.GET)
    public Object checkOutTeaches(NativeWebRequest request) throws Exception{
        TeacherJsonOut teacherJsonOut = getTeacher(request);
        Teach teach = null;
        try {
            //查找考试
            teach = teacherService.checkOutTeaches(teacherJsonOut.getT_num());
        } catch (NullPointerException ne) {
            return ResultJson.BusinessErrorException("token中无用户信息！",null);
        }

        if (teach == null)
            throw new BusinessException("没有您的授课记录！");
        String[] grades = teach.getGrade().split("\\ ");
        if (grades.length == 0) {
            throw new Exception();
        }
        //TODO 课程，专业班级

        //得到考试列表
        List<ExamJsonOut> list = examService.checkOutByTeach(teach);

        return list;
    }

    @CheckToken
    @RequestMapping(value = "examCourses")
    @ResponseBody
    public Object examCourses(NativeWebRequest request) throws Exception{
        TeacherJsonOut teacherJsonOut = getTeacher(request);
        String t_num = "";
        try {
            t_num = teacherJsonOut.getT_num();
        } catch (NullPointerException ne) {
            return ResultJson.BusinessErrorException("token中没有用户信息", null);
        }
        if (t_num == null || "".equals(t_num))
            return ResultJson.BusinessErrorException("token获取用户信息出错！",null);
        List<Course> courses = teacherService.checkOutCourseByNum(t_num);
        if (courses.size() == 0)
            return ResultJson.Success("您没有授课记录！");
        return ResultJson.Success(courses);
    }

    /**
     * 通过课程号查找为考试的班级，默认一年一个班级一个课程只有一场考试
     *
     * @param
     * @return: Date: 2019/3/28
     */
    @CheckToken
    @ResponseBody
    @RequestMapping(value = "examGrades")
    public Object examGrade(String course,NativeWebRequest request) throws Exception{
        TeacherJsonOut teacherJsonOut = getTeacher(request);
        //查找老师教授的未参加过考试的班级
        String t_num = "";
        try {
            t_num = teacherJsonOut.getT_num();
        } catch (NullPointerException np) {
            ResultJson.BusinessErrorException("token中没有用户信息！",null);
        }
        //查询近期还未考过该门课程的班级
        List<Grade> grades = basicService.gradesNotInExam(t_num,course);
        if (grades.size() == 0)
            return ResultJson.Success("没有需要考试的班级！");
        return ResultJson.Success(grades);
    }

//    public Object beginExam() {
//
//    }

    private TeacherJsonOut getTeacher(NativeWebRequest request) throws Exception{
        //获取登陆教师的信息
        String teacherJson = (String) request.getAttribute(ConstConfig.CURRENT_OBJECT, RequestAttributes.SCOPE_REQUEST);
        if (teacherJson == null) {
            return null;
        }
        //转为对象
        TeacherJsonOut teacherJsonOut = JsonUtil.jsonToPojo(teacherJson, TeacherJsonOut.class);
        return teacherJsonOut;
    }

}
