package com.ladybird.hkd.controller;

import com.ladybird.hkd.annotation.CheckToken;
import com.ladybird.hkd.exception.NullException;
import com.ladybird.hkd.exception.ParamException;
import com.ladybird.hkd.model.json.ResultJson;
import com.ladybird.hkd.model.pojo.Course;
import com.ladybird.hkd.model.pojo.Department;
import com.ladybird.hkd.model.pojo.Faculty;
import com.ladybird.hkd.model.pojo.Grade;
import com.ladybird.hkd.service.BasicService;
import com.ladybird.hkd.util.ParamUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Shen
 * @description: 基本信息管理
 * @create: 2019-03-13
 */
@Api(value = "基本信息controller",tags = "基本信息管理类")
@Controller
@RequestMapping("/basic")
public class BasicController extends BaseController {
    @Autowired
    private BasicService basicService;

    @ApiOperation("添加课程")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "c_name",value = "课程名称",required = true)
    })

    @RequestMapping(value = "/addCourse",method = RequestMethod.GET)
    @ResponseBody
    public Object addCourse(Course course) throws ParamException {


        if(ParamUtils.stringIsNull(course.getC_name())){

            throw new ParamException("请输入添加的课程名称");
        }


        if (basicService.findCourseByName(course.getC_name())!=null){
            throw new ParamException("已经有此课程");
        }
        basicService.addCourse(course);
        return ResultJson.Success("添加成功");
    }
    @ApiOperation("删除课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "c_id",value = "课程ID",required = true),

    })

    @RequestMapping(value = "/delCourse",method = RequestMethod.GET)
    @ResponseBody
    public Object delCourse(Integer c_id){
        basicService.delCourse(c_id);
        return ResultJson.Success("删除成功");
    }
    @ApiOperation("修改课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "c_id",value = "课程ID",required = true),
            @ApiImplicitParam(name = "c_naem",value = "课程名称",required = true),

    })

    @RequestMapping(value = "/updateCourse",method = RequestMethod.GET)
    @ResponseBody
    public Object updateCourse(Course course) throws ParamException {
        if(ParamUtils.stringIsNull(course.getC_name())){
            throw new ParamException("请输入要修改的课程名称");
        }
        if (basicService.findCourseByName(course.getC_name())!=null){
            throw new ParamException("已经有此课程");
        }
        basicService.updateCourse(course);
        return ResultJson.Success("修改成功");
    }
    @ApiOperation("查询课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "c_id",value = "课程ID",required = true),
            @ApiImplicitParam(name = "c_naem",value = "课程名称",required = true),

    })

    @RequestMapping(value = "/findCourse",method = RequestMethod.GET)
    @ResponseBody
    public Object findCourse(Course course) throws ParamException {
        if (ParamUtils.stringIsNull(course.getC_name())){
            throw new ParamException("请输入要查询的课程名称");
        }
        Course course1 = basicService.findCourse(course);
        if(course1==null){
            throw new ParamException("没有此课程");
        }
        return ResultJson.Success(course1);
    }




    @ApiOperation("添加专业")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dept_num",value = "专业代码",required = true),
            @ApiImplicitParam(name = "dept_name",value = "专业名称",required = true)
    })

    @RequestMapping(value = "/addDept",method = RequestMethod.GET)
    @ResponseBody
    public Object addDept(Department department) throws ParamException {
        if (ParamUtils.stringIsNull(department.getDept_num())||ParamUtils.stringIsNull(department.getDept_name())){
            throw new ParamException("请输入要添加的专业代码或专业名称");
        }
        if(basicService.findDeptByName(department.getDept_name())!=null){
            throw new ParamException("专业名称重复");
        }
        if (basicService.findDeptByNum(department.getDept_num())!=null){
            throw new ParamException("专业代码重复");
        }

        basicService.addDept(department);
        return ResultJson.Success("添加成功");
    }
    @ApiOperation("删除专业")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dept_num",value = "专业代码",required = true)

    })

    @RequestMapping(value = "/delDept",method = RequestMethod.GET)
    @ResponseBody
    public Object delDept(String dept_num){
        basicService.delDept(dept_num);
        return ResultJson.Success("删除成功");
    }
    @ApiOperation("修改专业")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dept_num",value = "专业代码",required = true),
            @ApiImplicitParam(name = "dept_name",value = "专业名称",required = true)
    })

    @RequestMapping(value = "/updateDept",method = RequestMethod.GET)
    @ResponseBody
    public Object updateDept(Department department) throws NullException, ParamException {
        if (ParamUtils.stringIsNull(department.getDept_num())||ParamUtils.stringIsNull(department.getDept_name())){
            throw new NullException("请输入要修改的专业名称");
        }
        if(basicService.findDeptByName(department.getDept_name())!=null){
            throw new ParamException("专业名称重复");
        }
        if (basicService.findDeptByNum(department.getDept_num())!=null){
            throw new ParamException("专业代码重复");
        }
        basicService.updateDept(department);
        return ResultJson.Success("修改成功");
    }
    @ApiOperation("查询专业")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dept_num",value = "专业代码",required = true),
            @ApiImplicitParam(name = "dept_name",value = "专业名称",required = true)
    })

    @RequestMapping(value = "/findDept",method = RequestMethod.GET)
    @ResponseBody
    public Object findDept(Department department) throws ParamException {
        if (ParamUtils.stringIsNull(department.getDept_name())&&ParamUtils.stringIsNull(department.getDept_num())){
            throw new ParamException("请输入要查询的专业名称或代码");
        }
        Department dept = basicService.findDept(department);
        if(dept==null){
            throw new ParamException("没有此专业");
        }
        return ResultJson.Success(dept);
    }



    @ApiOperation("添加学院")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fac_num",value = "学院代码",required = true),
            @ApiImplicitParam(name = "fac_name",value = "学院名称",required = true)
    })

    @RequestMapping(value = "/addFaculty",method = RequestMethod.GET)
    @ResponseBody
    public Object addFaculty(Faculty faculty) throws ParamException {
        if (ParamUtils.stringIsNull(faculty.getFac_name())||ParamUtils.stringIsNull(faculty.getFac_num())){
            throw new ParamException("请输入要添加的学院代码或学院名称");
        }
        if(basicService.findFacultyByName(faculty.getFac_name())!=null){
            throw new ParamException("已经有此学院");
        }
        if(basicService.findFacultyByNum(faculty.getFac_num())!=null){
            throw new ParamException("学院代码重复");
        }


        basicService.addFaculty(faculty);
        return ResultJson.Success("添加成功");
    }

    @ApiOperation("删除学院")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fac_num",value = "学院代码",required = true)
    })

    @RequestMapping(value = "/delFaculty",method = RequestMethod.GET)
    @ResponseBody
    public Object delFaculty(String fac_num){
        basicService.delFaculty(fac_num);
        return ResultJson.Success("删除成功");
    }

    @ApiOperation("修改学院")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fac_num",value = "学院代码",required = true),
            @ApiImplicitParam(name = "fac_name",value = "学院名字",required = true)
    })
    @RequestMapping(value = "/updateFaculty",method = RequestMethod.GET)
    @ResponseBody
    public Object updateFaculty(Faculty faculty) throws NullException, ParamException {
        if (ParamUtils.stringIsNull(faculty.getFac_name())||ParamUtils.stringIsNull(faculty.getFac_num())){
            throw new NullException("请输入修改信息");
        }
        if(basicService.findFacultyByName(faculty.getFac_name())!=null){
            throw new ParamException("已经有此学院");
        }
        if(basicService.findFacultyByNum(faculty.getFac_num())!=null){
            throw new ParamException("学院代码重复");
        }
        basicService.updateFaculty(faculty);
        return ResultJson.Success("修改成功");
    }
    @ApiOperation("查询学院")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fac_num",value = "学院代码",required = true),
            @ApiImplicitParam(name = "fac_name",value = "学院名字",required = true)
    })
    @RequestMapping(value = "/findFaculty",method = RequestMethod.GET)
    @ResponseBody
    public Object findFaculty(Faculty faculty) throws ParamException {
        if (ParamUtils.stringIsNull(faculty.getFac_name())||ParamUtils.stringIsNull(faculty.getFac_num())){
            throw new ParamException("请输入查询的学院名或代码");
        }
        Faculty faculty1 = basicService.findFaculty(faculty);
        if(faculty1==null){
            throw new ParamException("没有此学院");
        }
        return ResultJson.Success(faculty1);
    }



    @ApiOperation("添加班级")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "g_year",value = "年",required = true),
            @ApiImplicitParam(name = "g_class",value = "班",required = true)
    })

    @RequestMapping(value = "/addGrade",method = RequestMethod.GET)
    @ResponseBody
    public Object addGrade(Grade grade) throws ParamException {
        if(grade.getG_year()==null||grade.getG_class()==null){
            throw new ParamException("请输入年级或班级");
        }
        if (basicService.findGradeByYandC(grade)!=null){
            throw new ParamException("该年级已有此班");
        }
        basicService.addGrade(grade);
        return ResultJson.Success("添加成功");

    }

    @ApiOperation("删除班级")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "g_id",value = "年级id",required = true)

    })
    @RequestMapping(value = "/delGrade",method = RequestMethod.GET)
    @ResponseBody
    public Object delGrade(Integer g_id){
        basicService.delGrade(g_id);
        return ResultJson.Success("删除成功");
    }

    @ApiOperation("修改班级")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "g_id",value = "年级id",required = true),
            @ApiImplicitParam(name = "g_year",value = "年",required = true),
            @ApiImplicitParam(name = "g_class",value = "班",required = true)
    })

    @RequestMapping(value = "/updateGrade",method = RequestMethod.GET)
    @ResponseBody
    public Object updateGrade(Grade grade) throws ParamException {
        if(grade.getG_class()==null||grade.getG_year()==null){
            throw new ParamException("年级或班级为空");
        }
        if(basicService.findGradeByYandC(grade)!=null){
            throw new ParamException("该年级此班已存在");
        }
        basicService.updateGrade(grade);
        return ResultJson.Success("修改成功");
    }

    @ApiOperation("查询班级")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "g_id",value = "年级id",required = true),
            @ApiImplicitParam(name = "g_year",value = "年",required = true),
            @ApiImplicitParam(name = "g_class",value = "班",required = true)
    })

    @RequestMapping(value = "/findGrade",method = RequestMethod.GET)
    @ResponseBody
    public Object findGrade(Grade grade) throws ParamException {
        if(grade.getG_id()==null||grade.getG_year()==null||grade.getG_class()==null){
            throw new ParamException("请输入查询条件");
        }
        Grade grade1 = basicService.findGrade(grade);
        if (grade1==null){
            throw new ParamException("没有查询结果");
        }
        return ResultJson.Success(grade1);
    }

}
