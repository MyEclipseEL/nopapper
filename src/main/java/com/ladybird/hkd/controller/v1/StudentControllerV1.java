package com.ladybird.hkd.controller.v1;

import com.ladybird.hkd.annotation.CheckToken;
import com.ladybird.hkd.controller.BaseController;
import com.ladybird.hkd.exception.ParamException;
import com.ladybird.hkd.manager.TokenManager;
import com.ladybird.hkd.model.example.ExamExample;
import com.ladybird.hkd.model.json.ResultJson;
import com.ladybird.hkd.model.json.StudentJsonIn;
import com.ladybird.hkd.model.json.TokenJsonOut;
import com.ladybird.hkd.model.pojo.Score;
import com.ladybird.hkd.model.pojo.Student;
import com.ladybird.hkd.service.ExamService;
import com.ladybird.hkd.service.StudentService;
import com.ladybird.hkd.util.ConstConfig;
import com.ladybird.hkd.util.JsonUtil;
import com.ladybird.hkd.util.ParamUtils;
import io.swagger.annotations.Api;
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

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Shen
 * @description: v1版本
 * @create: 2019-03-14
 */
@Api(value = "学生controller",tags = "学生管理类")
@Controller
@RequestMapping("/v1/student")
public class StudentControllerV1 extends BaseController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private ExamService examService;

    @Autowired
    private TokenManager tokenManager;

    @ApiOperation("提交试卷成绩")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "score",value = "分数",required = true),
            @ApiImplicitParam(name = "course",value = "课程号",required = true)
    })
    @CheckToken
    @RequestMapping(value = "/commitPaper",method = RequestMethod.GET)
    @ResponseBody
    public Object commitPaper(String score,String course,NativeWebRequest request) throws Exception{
        if (ParamUtils.stringIsNull(score) || ParamUtils.stringIsNull(course)){
            return ResultJson.ParameterError();
        }
        String studentJson = (String) request.getAttribute(ConstConfig.CURRENT_OBJECT, RequestAttributes.SCOPE_REQUEST);
        if (ParamUtils.stringIsNull(studentJson)) {
            return ResultJson.ServerException();
        }
        Student student = JsonUtil.jsonToPojo(studentJson, Student.class);
        if (student == null) {
            return ResultJson.ServerException();
        }
        Score param = new Score(student.getStu_num(), course, new BigDecimal(score));
        studentService.checkInScore(param);

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
        student.setStu_pwd(null);
        //创建token
        String accessToken = tokenManager.createToken(student);

        //创建refreshToken
        String refreshToken = tokenManager.createReToken(accessToken);
        return new TokenJsonOut(accessToken, refreshToken);
    }

    @CheckToken
    @RequestMapping("/exam")
    @ResponseBody
    public Object exam(NativeWebRequest request) throws Exception{
        String studentJson = (String) request.getAttribute(ConstConfig.CURRENT_OBJECT, RequestAttributes.SCOPE_REQUEST);
        if (ParamUtils.stringIsNull(studentJson)) {
            return ResultJson.ServerException();
        }
        Student student = JsonUtil.jsonToPojo(studentJson, Student.class);
        if (student == null) {
            throw new Exception("Token中没有用户信息！");
        }
        if (ParamUtils.stringIsNull(student.getStu_num()))
            return ResultJson.ServerException();
        List<ExamExample> examExamples = examService.selectExamByStu(student);
        if (examExamples.size() == 0)
            return ResultJson.Success("暂时没有考试！");
        return examExamples;
    }

}