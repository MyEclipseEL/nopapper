package com.ladybird.hkd.controller;

import com.ladybird.hkd.annotation.CheckToken;
import com.ladybird.hkd.exception.BusinessException;
import com.ladybird.hkd.exception.ParamException;
import com.ladybird.hkd.manager.TokenManager;
import com.ladybird.hkd.model.example.AdminExample;
import com.ladybird.hkd.model.example.ExamExample;
import com.ladybird.hkd.model.example.GradeExample;
import com.ladybird.hkd.model.json.*;
import com.ladybird.hkd.model.pojo.Course;
import com.ladybird.hkd.model.pojo.Teach;
import com.ladybird.hkd.model.pojo.Teacher;
import com.ladybird.hkd.service.AdminService;
import com.ladybird.hkd.service.ExamService;
import com.ladybird.hkd.service.MessageService;
import com.ladybird.hkd.service.TeacherService;
import com.ladybird.hkd.util.ConstConfig;
import com.ladybird.hkd.util.JsonUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;

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
    private MessageService messageService;
    @Autowired
    private AdminService adminService;

    //登陆web端
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Object login(String t_num,String t_pwd) throws Exception {
        if (t_num == null || "".equals(t_num.trim()))
            throw new ParamException("请填写工号！");
        if (t_pwd == null || "".equals(t_pwd.trim()))
            throw new ParamException("请填写密码！");
        Teacher teacher = new Teacher();
        teacher.setT_num(t_num);
        teacher.setT_pwd(t_pwd);
        TeacherJsonOut teacherJsonOut = teacherService.login(teacher);
        if (teacherJsonOut != null) {
            //创建token
            String accessToken = manager.createToken(teacherJsonOut);
            //创建refreshToken
            String reToken = manager.createReToken(accessToken);
            return new TokenJsonOut(accessToken, reToken);
        }else {
            AdminExample admin = adminService.login(t_num, t_pwd);
            if (admin == null)
                throw new ParamException("用户名密码错误！");
            String accessToken = manager.createToken(admin);
            String reToken = manager.createReToken(accessToken);
            return new TokenJsonOut(accessToken, reToken);
        }

    }

    @CheckToken
    @ResponseBody
    @RequestMapping(value = "/logout")
    public Object logout(NativeWebRequest request) throws Exception {
        String tokenKey = request.getHeader(ConstConfig.AUTHORIZATION);
        String refreshTokenKey = request.getHeader(ConstConfig.AUTHORIZATION_RE);
        manager.deleteToken(tokenKey.split("\\ ")[1]);
        manager.deleteToken(refreshTokenKey);
        return ResultJson.Success();
    }

    @CheckToken
    @ResponseBody
    @RequestMapping(value = "/afterLogin")
    public Object afterLogin(NativeWebRequest request) throws Exception{
        //教师信息
        TeacherJsonOut teacherJsonOut = getTeacher(request);
        if (teacherJsonOut == null) {
            String adminJson = (String) request.getAttribute(ConstConfig.CURRENT_OBJECT, RequestAttributes.SCOPE_REQUEST);
            AdminExample admin = JsonUtil.jsonToPojo(adminJson,AdminExample.class);
            return ResultJson.Success(admin);
        }
        return ResultJson.Success(teacherJsonOut);

        //超级管理员
    }

    @ApiOperation( "获取考试场次")
    @CheckToken
    @ResponseBody
    @RequestMapping(value = "/exams",method = RequestMethod.GET)
    public Object checkOutTeaches(NativeWebRequest request) throws Exception{
        TeacherJsonOut teacherJsonOut = getTeacher(request);
        List<Teach> teach = null;
        try {
            //查找考试
            teach = teacherService.checkOutTeaches(teacherJsonOut.getT_num());
        } catch (NullPointerException ne) {
            return ResultJson.BusinessErrorException("token中无用户信息！",null);
        }

        if (teach == null || teach.size() == 0)
            throw new BusinessException("没有您的授课记录！");
        String grade = "";
        Teach teach1 = teach.get(0);
        if (teach.size()>1) {
            for (int i = 0; i < teach.size(); i++) {
                grade += teach.get(i).getGrade();
                if (i < teach.size() - 1)
                    grade += " ";
            }
            teach1.setCourse(grade);
        }
        //TODO 课程，专业班级

        //得到考试列表
        List<ExamExample> list = examService.checkOutByTeach(teach1);

        return list;
    }

    @CheckToken
    @RequestMapping(value = {"/examCourses","/courses"},method = {RequestMethod.POST,RequestMethod.GET})
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
    @RequestMapping(value = "/examGrades",method = {RequestMethod.GET,RequestMethod.POST})
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
        List<GradeExample> gradeExamples = messageService.gradesNotInExam(t_num,course);
        if (gradeExamples.size() == 0)
            return ResultJson.Success("没有需要考试的班级！");
        return ResultJson.Success(gradeExamples);
    }

    @CheckToken
    @ResponseBody
    @RequestMapping(value = "/begin",method = {RequestMethod.POST,RequestMethod.GET})
    public Object beginExam( String course,String[] grades, Integer duration,NativeWebRequest request) throws Exception {
        //得到redis中的教师信息
        TeacherJsonOut teacherJsonOut = getTeacher(request);
        //得到教师工号
        String t_num = "";
        try {
            t_num = teacherJsonOut.getT_num();
        } catch (NullPointerException np) {
            throw new BusinessException("token中没有用户信息");
        }
        //对选中班级开始考试
        if (grades.length == 0)
            throw new ParamException("没有选择考试的班级！");
        try {
            for (String s : grades){
                if ("".equals(s.trim()))
                    throw new ParamException("请选择班级！");
                Integer.parseInt(s);
            }
        } catch (NumberFormatException nfe) {
            throw new ParamException("班级出错！grades:");
        }
        if (course == null || "".equals(course))
            throw new ParamException("没有选择课程！");
        ExamExample examExample = examService.beginExam(t_num, grades, course);
        if (examExample == null)
            return ResultJson.ServerException("开始失败，稍后重试！");
        try {
            examExample.getExam_id();
        } catch (NullPointerException np) {
            return ResultJson.ServerException();
        }
        return ResultJson.Success(examExample);
    }



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