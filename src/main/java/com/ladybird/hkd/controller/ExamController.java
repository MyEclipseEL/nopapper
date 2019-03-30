package com.ladybird.hkd.controller;

import com.ladybird.hkd.annotation.CheckToken;
import com.ladybird.hkd.enums.ExamStateEnum;
import com.ladybird.hkd.exception.BusinessException;
import com.ladybird.hkd.exception.ParamException;
import com.ladybird.hkd.model.json.ExamJsonIn;
import com.ladybird.hkd.model.json.ExamJsonOut;
import com.ladybird.hkd.model.json.ResultJson;
import com.ladybird.hkd.model.json.TeacherJsonOut;
import com.ladybird.hkd.model.pojo.*;
import com.ladybird.hkd.service.ExamService;
import com.ladybird.hkd.service.StudentService;
import com.ladybird.hkd.service.TeacherService;
import com.ladybird.hkd.util.ConstConfig;
import com.ladybird.hkd.util.JsonUtil;
import com.ladybird.hkd.util.ParamUtils;
import com.ladybird.hkd.vo.ItemTypeListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Shen
 * @description: 考试管理
 * @create: 2019-03-10
 */
@Api("考试管理类")
@CrossOrigin
@Controller
@RequestMapping("/exam")
public class ExamController extends BaseController{
    @Autowired
    private ExamService examService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;



    @CheckToken
    @ApiOperation(value = "修改试卷",notes= "修改试卷题型分配")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "单选题个数", name = "single_choice", required = true),
            @ApiImplicitParam(value = "多选题个数", name = "multiple_choice", required = true),
            @ApiImplicitParam(value = "判断题个数",name = "checking",required = true)
    })
    @ResponseBody
    @RequestMapping(value = "/editPaper",method = {RequestMethod.POST}, consumes="application/json", produces="application/json")
    public Object editPaper(@RequestBody PaperEdit paperEdit) throws Exception{
        PaperEdit.checkParams(paperEdit);
        examService.updatePaper(paperEdit);
        return ResultJson.Success(paperEdit);
    }

    @ApiOperation("请求试卷当前配置信息")
    @CheckToken
    @RequestMapping(value = "/checkOutPaper",method = RequestMethod.GET)
    @ResponseBody
    public Object checkOutPaper() throws Exception{
        PaperEdit paperEdit = examService.checkOutPaper();
        if (paperEdit == null) {
            return ResultJson.ServerException();
        }
        try {
            PaperEdit.checkParams(paperEdit);
        } catch (ParamException pe) {
            pe.printStackTrace();
            return ResultJson.ServerException();
        }
        return ResultJson.Success(paperEdit);
    }



    /*@ApiOperation(value = "开始考试，设置倒计时开始")
    @ApiImplicitParam(name = "exam",value = "考试号",required = true)
    @CheckToken
    @RequestMapping(value = "/beginExam",method = RequestMethod.GET)
    @ResponseBody
    public Object beginExam(String[] exams) throws Exception{
        if (exams.length ==0)
            throw new ParamException("考试号没有传！");
        examService.changeStateAndBegin(exams, ExamStateEnum.BEGIN.getCode());
        return ResultJson.Success();
    }*/

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

    @ApiOperation("学生请求考试安排")
    @ApiImplicitParam(name = ConstConfig.AUTHORIZATION,value = "token:xxx accessToken")
    @CheckToken
    @RequestMapping(value = "/exam",method = RequestMethod.GET)
    @ResponseBody
    public Object exam(NativeWebRequest request) throws Exception{
        String studentJson = (String) request.getAttribute(ConstConfig.CURRENT_OBJECT, RequestAttributes.SCOPE_REQUEST);
        if (ParamUtils.stringIsNull(studentJson)) {
            return ResultJson.ServerException();
        }
        Student student = JsonUtil.jsonToPojo(studentJson, Student.class);
        if (student == null) {
            throw new BusinessException("Token中没有用户信息！");
        }
        if (ParamUtils.stringIsNull(student.getStu_num()))
            return ResultJson.ServerException();
        List<ExamJsonOut> examJsonOuts = examService.selectExamByStu(student);
        if (examJsonOuts.size() == 0)
            return ResultJson.BusinessErrorException("暂时没有考试！",null);
        return examJsonOuts;
    }

    /*@ApiOperation("后端添加考试")
    @CheckToken
    @RequestMapping(value = "/addExam",method = RequestMethod.POST)
    @ResponseBody
    public Object addExam(@RequestBody ExamJsonIn exam) throws Exception{
        if (exam == null)
            return ResultJson.ParameterError();
        ExamJsonIn.ValidExamIn(exam);
        List<ExamJsonOut> examJsonOuts = examService.addExams(exam);
        return ResultJson.Success(examJsonOuts);
    }*/
}