package com.ladybird.hkd.controller;

import com.ladybird.hkd.annotation.CheckToken;
import com.ladybird.hkd.enums.ExamStateEnum;
import com.ladybird.hkd.exception.BusinessException;
import com.ladybird.hkd.exception.ParamException;
import com.ladybird.hkd.model.json.ExamJsonOut;
import com.ladybird.hkd.model.json.ItemsOut;
import com.ladybird.hkd.model.json.ResultJson;
import com.ladybird.hkd.model.pojo.ItemType;
import com.ladybird.hkd.model.pojo.Student;
import com.ladybird.hkd.model.vo.ItemVO;
import com.ladybird.hkd.service.ExamService;
import com.ladybird.hkd.service.ItemService;
import com.ladybird.hkd.util.ConstConfig;
import com.ladybird.hkd.util.JsonUtil;
import com.ladybird.hkd.util.UrlConf;
import io.swagger.annotations.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ladybird.hkd.model.json.ResultJson.Success;

/**
 * @author Shen
 * @description: 题目控制类
 * @create: 2019-03-13
 */
@CrossOrigin
@Api("题目接口")
@Controller
@RequestMapping("/item")
public class ItemController extends BaseController{

    @Autowired
    private ItemService itemService;
    @Autowired
    private ExamService examService;

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
        return  Success(JsonUtil.objectToJson(itemService.checkOutItems(course)));
    }

    @CheckToken
    @ResponseBody
    @RequestMapping(value = "/paper",method = RequestMethod.GET)
    @ApiOperation(value = "请求试卷",notes = "请求单张试卷的所有题目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exam",value = "考试号",required = true),
            @ApiImplicitParam(name = "authorization",value = "token",required = true,paramType = "header")
    })
    public Object getPaper(@RequestParam String exam) throws Exception {
        //TODO 校验考试是否开始
        //校验考试
        if (exam == null || "".equals(exam.trim())) {
            throw new ParamException("哪场考试呢？");
        }
        ExamJsonOut examJsonOut = examService.checkOutExamById(exam);
        if (examJsonOut.getState() == ExamStateEnum.FINISH.getCode()) {
            throw new BusinessException(ExamStateEnum.FINISH.getMsg());
        }
        if (examJsonOut.getState() == ExamStateEnum.PREPAR.getCode()) {
            throw new BusinessException(ExamStateEnum.PREPAR.getMsg());
        }
        Date date = new Date();
        //TODO 开始测试时打开
//        if (date.getTime() - examJsonOut.getBegin_time().getTime() > 0.5 * 3600 * 1000){
//            throw new BusinessException("考试已经开始半个小时，禁止考生登录！");
//        }
        List<ItemsOut> outList = itemService.getPaper(examJsonOut.getCourse().getC_id());
        return ResultJson.Success(outList);
    }

    /*@ApiOperation("配置考试题型分数")
    @CheckToken
    @ResponseBody
    @RequestMapping(value = "/editTypeScore",method = RequestMethod.POST)
    public Object editTypeScore(@RequestBody List<ItemType> itemTypes) throws Exception{
        if (itemTypes.size() <3)
            return ResultJson.ServerException();
        itemService.updateTypeScore(itemTypes);
        return ResultJson.Success(itemService.checkOutTypes());
    }*/

    @ApiOperation("查找所有题型")
    @CheckToken
    @ResponseBody
    @RequestMapping(value = "/checkOutTypes",method = RequestMethod.GET)
    public Object checkOutTypes() throws Exception{
        List<ItemType> types = itemService.checkOutTypes();
        if (types.size() <3)
            return ResultJson.ServerException();
        return ResultJson.Success(types);
    }

    @ResponseBody
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public Object importItemExcel(HttpServletRequest request) throws Exception{
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(4*1024*1024);
        List<FileItem> fileItems = new ArrayList<>();
        try {
            fileItems = upload.parseRequest(request);
        } catch (FileUploadException fe) {
            fe.printStackTrace();
        }
        File file = new File("");
        for (FileItem fileItem : fileItems) {
            File fullFile = new File(new String(fileItem.getName().getBytes(), "utf-8"));
            file = new File(UrlConf.LOCAL_UPLOAD_PATH,fullFile.getName());
//            file = new File(UrlConf.SERVER_UPLOAD_PATH);
            fileItem.write(file);
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        String readResult = null;
        try {
            //调用service
            readResult = itemService.addItems(multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultJson.Success(readResult);
    }



}
