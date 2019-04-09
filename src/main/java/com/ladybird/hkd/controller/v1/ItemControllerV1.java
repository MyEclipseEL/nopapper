package com.ladybird.hkd.controller.v1;

import com.ladybird.hkd.annotation.CheckToken;
import com.ladybird.hkd.controller.BaseController;
import com.ladybird.hkd.enums.ExamStateEnum;
import com.ladybird.hkd.exception.BusinessException;
import com.ladybird.hkd.exception.ParamException;
import com.ladybird.hkd.model.example.ExamExample;
import com.ladybird.hkd.model.json.ItemsOut;
import com.ladybird.hkd.model.json.ResultJson;
import com.ladybird.hkd.service.ExamService;
import com.ladybird.hkd.service.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

import static com.ladybird.hkd.model.json.ResultJson.Success;

/**
 * @author Shen
 * @description: 题目控制类
 * @create: 2019-03-13
 */
@Api("题目接口")
@Controller
@RequestMapping("/v1/item")
public class ItemControllerV1 extends BaseController{

    @Autowired
    private ItemService itemService;
    @Autowired
    private ExamService examService;


    @CheckToken
    @ResponseBody
    @RequestMapping(value = "/paper",method = RequestMethod.GET)
    @ApiOperation(value = "请求试卷",notes = "请求单张试卷的所有题目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exam",value = "考试号"),
            @ApiImplicitParam(name = "authorization",value = "token",required = true,paramType = "header")
    })
    public Object getPaper(@RequestParam String exam) throws Exception {
        //TODO 校验考试是否开始
        //校验考试
        if (exam == null || "".equals(exam.trim())) {
            throw new ParamException("哪场考试呢？");
        }
        ExamExample examExample = examService.checkOutExamById(exam);
        if (examExample.getState() == ExamStateEnum.FINISH.getCode()) {
            throw new BusinessException(ExamStateEnum.FINISH.getMsg());
        }
        if (examExample.getState() == ExamStateEnum.PREPAR.getCode()) {
            throw new BusinessException(ExamStateEnum.PREPAR.getMsg());
        }
        Date date = new Date();
        if (date.getTime() - examExample.getBegin_time().getTime() > 0.5 * 3600 * 1000){
            throw new BusinessException("考试已经开始半小时，禁止考试参加");
        }
        List<ItemsOut> outList = itemService.getPaper(examExample.getCourse().getC_id());

        return ResultJson.Success(outList);
    }
}