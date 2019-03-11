package com.ladybird.hkd.controller;

import com.ladybird.hkd.annotation.CheckToken;
import com.ladybird.hkd.exception.ParamException;
import com.ladybird.hkd.model.json.ItemsOut;
import com.ladybird.hkd.model.json.ResultJson;
import com.ladybird.hkd.model.vo.ItemVO;
import com.ladybird.hkd.service.ItemService;
import com.ladybird.hkd.util.JsonUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static com.ladybird.hkd.model.json.ResultJson.Success;

/**
 * @author Shen
 * @description: 题目控制类
 * @create: 2019-03-13
 */
@Api("题目接口")
@Controller
@RequestMapping("/item")
public class ItemController extends BaseController{

    @Autowired
    private ItemService itemService;

    @CheckToken
    @ResponseBody
    @RequestMapping(value = "/items",method = RequestMethod.GET)
    @ApiOperation(value = "请求题目",notes = "请求所有题目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "course",value = "课程编号"),
            @ApiImplicitParam(name = "authorization",value = "token",required = true,paramType = "header")
    })
    public Object getItems(String course) throws Exception{
        if (course == null || "".equals(course.trim())) {
            throw new ParamException("查找哪一科的题目呢？");
        }
        return  Success(JsonUtil.objectToJson(itemService.checkOutItems(Integer.parseInt(course))));
    }

    @CheckToken
    @ResponseBody
    @RequestMapping(value = "/paper",method = RequestMethod.GET)
    @ApiOperation(value = "请求试卷",notes = "请求单张试卷的所有题目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "course",value = "课程编号"),
            @ApiImplicitParam(name = "authorization",value = "token",required = true,paramType = "header")
    })
    public Object getPaper(String course) throws Exception {
        if (course == null || "".equals(course.trim()))
            throw new ParamException("科目未知！");
        List<ItemsOut> outList = itemService.getPaper(Integer.parseInt(course));

        return ResultJson.Success(outList);
    }
}
