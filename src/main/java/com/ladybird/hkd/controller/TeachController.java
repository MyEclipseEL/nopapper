package com.ladybird.hkd.controller;

import com.ladybird.hkd.annotation.CheckGroup;
import com.ladybird.hkd.annotation.CheckToken;
import com.ladybird.hkd.exception.AuthorizationException;
import com.ladybird.hkd.exception.BusinessException;
import com.ladybird.hkd.exception.ParamException;
import com.ladybird.hkd.model.example.AdminExample;
import com.ladybird.hkd.model.example.TeachExample;
import com.ladybird.hkd.model.json.ResultJson;
import com.ladybird.hkd.model.json.TeacherJsonOut;
import com.ladybird.hkd.model.pojo.Course;
import com.ladybird.hkd.model.pojo.Teach;
import com.ladybird.hkd.service.TeacherService;
import com.ladybird.hkd.util.ConstConfig;
import com.ladybird.hkd.util.JsonUtil;
import com.ladybird.hkd.util.UrlConf;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Shen
 * @description: 授课管理
 * @create: 2019-04-13
 */
@Controller
@RequestMapping("/teach")
public class TeachController extends BaseController {

    @Autowired
    private TeacherService teacherService;

    //请求课程信息
    //查询教师教授的课程
    @CheckToken
    @ResponseBody
    @RequestMapping(value = "/teaches")
    public Object teaches(NativeWebRequest request, String t_num,String course) throws Exception{
        //获取登陆教师的信息
        String teacherJson = (String) request.getAttribute(ConstConfig.CURRENT_OBJECT, RequestAttributes.SCOPE_REQUEST);
        String adminJson = (String) request.getAttribute(ConstConfig.CURRENT_OBJECT, RequestAttributes.SCOPE_REQUEST);
        //转为对象
        TeacherJsonOut teacherJsonOut = JsonUtil.jsonToPojo(teacherJson, TeacherJsonOut.class);
        if (teacherJsonOut != null && teacherJsonOut.getGroup_id().getGroup_id().equals("2")) {
            try {
                t_num = teacherJsonOut.getT_num();
            } catch (NullPointerException npe) {
                throw new BusinessException("token中没有用户信息！");
            }
        }else if (teacherJsonOut == null){
            //判断是否为管理员
            AdminExample adminExample = JsonUtil.jsonToPojo(adminJson,AdminExample.class);
            if (adminExample == null )
                throw new AuthorizationException("非法的登陆！");
        }
        List<TeachExample> teachExamples = teacherService.checkOutTeachExms(t_num, course);
        return ResultJson.Success(teachExamples);
    }

    @CheckToken
    @CheckGroup
    @ResponseBody
    @RequestMapping(value = "/teachIn")
    //添加授课信息
    public Object checkInTeach(@RequestBody TeachExample teach) throws Exception{
        TeachExample example = teacherService.checkInTeach(teach);
        return ResultJson.Success(example);
    }

    @CheckToken
    @CheckGroup
    @ResponseBody
    @RequestMapping(value = "/teachChange")
    //修改被授课的班级
    public Object changeGrade(String teach_id,String[] grade,String teacher,String dept,String course) throws Exception{
        if (grade == null || grade.length < 1)
            throw new ParamException("修改授课班级：参数为空！");
        TeachExample result = teacherService.changeGrade(teach_id, grade,teacher,dept,course);
        return ResultJson.Success(result);
    }

    @CheckToken
    @CheckGroup
    @ResponseBody
    @RequestMapping(value = "/teachDel")
    //删除授课记录
    public Object delTeach(String teach_id) throws Exception {
        if (teach_id == null || "".equals(teach_id.trim()))
            throw new ParamException("删除授课记录：参数为空！");
        teacherService.delTeach(teach_id);
        return ResultJson.Success();
    }
    @CheckToken
    @CheckGroup
    @ResponseBody
    @RequestMapping(value = "/upload")
    //导入授课表
    public Object importTeaches(HttpServletRequest request) throws Exception{
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
        MultipartFile multipartFile = null;
        for (FileItem item : fileItems) {
            try {
                File fullFile = new File(item.getName());
                file = new File(UrlConf.LOCAL_UPLOAD_PATH, fullFile.getName());
//            file = new File(UrlConf.SERVER_UPLOAD_PATH);
                item.write(file);
            } catch (NullPointerException npe) {
                throw new ParamException("请选择上传到文件！");
            }
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        } catch (FileNotFoundException fe) {
            throw new ParamException("请选择上传的文件！");
        }
        FileUtils.deleteQuietly(file);
        return ResultJson.Success(teacherService.uploadTeaches(multipartFile));
    }

}
