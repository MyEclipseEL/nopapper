package com.ladybird.hkd.controller;

import com.ladybird.hkd.annotation.CheckGroup;
import com.ladybird.hkd.annotation.CheckToken;
import com.ladybird.hkd.enums.ExamStateEnum;
import com.ladybird.hkd.exception.BusinessException;
import com.ladybird.hkd.exception.ParamException;
import com.ladybird.hkd.model.example.ExamExample;
import com.ladybird.hkd.model.json.ItemsOut;
import com.ladybird.hkd.model.json.ResultJson;
import com.ladybird.hkd.model.pojo.ItemType;
import com.ladybird.hkd.model.vo.ItemVO;
import com.ladybird.hkd.service.ExamService;
import com.ladybird.hkd.service.ItemService;
import com.ladybird.hkd.util.JsonUtil;
import com.ladybird.hkd.util.UrlConf;
import io.swagger.annotations.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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

    @CheckGroup
    @CheckToken
    @ResponseBody
    @RequestMapping(value = "/items",method = RequestMethod.GET)
    @ApiOperation(value = "请求题目",notes = "请求所有题目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "course",value = "课程编号"),
            @ApiImplicitParam(name = "authorization",value = "token",required = true,paramType = "header")
    })
    public Object getItems(String course,String item_type,
                           @RequestParam(required = false,defaultValue = "1") Integer curPage,
                           @RequestParam(required = false,defaultValue = "10") Integer pageCount) throws Exception{

//        if (course == null || "".equals(course.trim())) {
//            throw new ParamException("查找哪一科的题目呢？");
//        }
        return  Success(itemService.checkOutItems(course,item_type,curPage,pageCount));
    }

    @CheckGroup
    @CheckToken
    @ResponseBody
    @RequestMapping(value = "/addItem")
    public Object addItem(@RequestBody ItemVO itemVO) throws Exception {
        ItemVO.validItem(itemVO);
        return ResultJson.Success(itemService.addItem(itemVO));
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
        ExamExample examExample = examService.checkOutExamById(exam);
//        if (examExample.getState() == ExamStateEnum.FINISH.getCode()) {
//            throw new BusinessException(ExamStateEnum.FINISH.getMsg());
//        }
//        if (examExample.getState() == ExamStateEnum.PREPAR.getCode()) {
//            throw new BusinessException(ExamStateEnum.PREPAR.getMsg());
//        }
        Date date = new Date();
        System.out.println(date);
        if (date.getTime() - examExample.getBegin_time().getTime() > (0.5 * 3600 * 1000)){
            throw new BusinessException("考试已经开始半个小时，禁止考生登录！");
        }
//        List<ItemsOut> outList = itemService.getPaper(examExample.getCourse().getC_id());
        List<String> result = itemService.getPaperUrl(examExample.getCourse().getC_id());
        Random random = new Random();
        Integer r = random.nextInt(result.size());
        List<String> url = new ArrayList<>();
        url.add(result.get(r));
        return ResultJson.Success(url);
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

    @CheckGroup
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

    @CheckGroup
    @CheckToken
    @ResponseBody
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public Object importItemExcel(HttpServletRequest request) throws Exception{
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(4*1024*1024);
        String course = "";
        String item_type = "";
        List<FileItem> fileItems = new ArrayList<>();
        try {
            fileItems = upload.parseRequest(request);
        } catch (FileUploadException fe) {
            fe.printStackTrace();
        }
        File file = new File("");
        MultipartFile multipartFile = null;
        for (FileItem fileItem : fileItems) {
            if (fileItem.isFormField()) {
                if (fileItem.getFieldName().trim().equals("course"))
                    course = fileItem.getString();
                else
                    item_type = fileItem.getString();
            }else {
                try {
                    File fullFile = new File(fileItem.getName());
//                    file = new File(UrlConf.LOCAL_UPLOAD_PATH, fullFile.getName());
                    file = new File(UrlConf.SERVER_UPLOAD_PATH,fullFile.getName());
                    fileItem.write(file);
                } catch (NullPointerException npe) {
                    throw new ParamException("请选择上传到文件！");
                }
            }
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        } catch (FileNotFoundException fe) {
            throw new ParamException("请选择上传的文件！");
        }
        //调用service
        try{
            List<ItemVO> result = itemService.addItems(multipartFile,course,item_type);
            FileUtils.deleteQuietly(file);
        return ResultJson.Success(result);
        }catch(Exception e){
            throw new BusinessException("请检查您的文件！");
        }
    }

    @CheckGroup
    @CheckToken
    @ResponseBody
    @RequestMapping(value = "/delItem")
    public Object delItem(String item_id) throws Exception {
        if (item_id == null || "".equals(item_id.trim()))
            throw new ParamException("选择删除的题！");
        itemService.delItem(item_id);
        return ResultJson.Success();
    }

    @CheckGroup
    @CheckToken
    @ResponseBody
    @RequestMapping(value = "/changeItem")
    public Object changeItem(@RequestBody ItemVO itemVO) throws Exception{
        return ResultJson.Success(itemService.changeItem(itemVO));
    }



}