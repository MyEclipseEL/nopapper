package com.ladybird.hkd.service.impl;

import com.ladybird.hkd.exception.BusinessException;
import com.ladybird.hkd.exception.ParamException;
import com.ladybird.hkd.mapper.CourseMapper;
import com.ladybird.hkd.mapper.DeptMapper;
import com.ladybird.hkd.mapper.GradeMapper;
import com.ladybird.hkd.mapper.TeacherMapper;
import com.ladybird.hkd.model.example.GradeExample;
import com.ladybird.hkd.model.example.TeachExample;
import com.ladybird.hkd.model.json.TeacherJsonOut;
import com.ladybird.hkd.model.pojo.*;
import com.ladybird.hkd.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shen
 * @description: 教师业务层实现类
 * @create: 2019-03-14
 */
@Service
@Transactional
public class TeacherServiceImpl implements TeacherService{

    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private GradeMapper gradeMapper;


    @Override
    public TeacherJsonOut login(Teacher teacher) throws Exception {
//        if (teacherJsonOut == null)
//            throw new ParamException("用户名密码错误！");
        return teacherMapper.validNumPwd(teacher);
    }

    @Override
    public List<Teach> checkOutTeaches(String t_num) throws Exception {
        return teacherMapper.checkOutTeaches(t_num);
    }

    @Override
    public List<Course> checkOutCourseByNum(String t_num) throws Exception {
        return teacherMapper.checkOutCourseByNum(t_num);
    }

    @Override
    public List<TeachExample> checkOutTeachExms(String teacher, String course) throws Exception {
        if (teacher == null || "".equals(teacher.trim()))
            teacher = null;
        if (course == null || "".equals(course.trim()))
            course = null;
        List<TeachExample> results = teacherMapper.checkOutTeachesExm(teacher, course, null);
        for (TeachExample t : results) {
            String grade = t.getGrade();
            List<GradeExample> grades = new ArrayList<>();
            for (String s : grade.split(",")) {
                grades.add(gradeMapper.selGradeById(s));
            }
            t.setGrades(grades);
        }
        return results;
    }

    @Override
    public TeachExample checkInTeach(TeachExample teach) throws Exception {
        Teach t = new Teach();
        if (teach.getCourse().getC_id() == null || "".equals(teach.getCourse().getC_id().trim()))
            throw new ParamException("请选择课程！");
        Course course = courseMapper.selCourseById(teach.getCourse().getC_id());
        if (course == null)
            throw new ParamException("课程不存在！");
        //设置课程
        t.setCourse(course.getC_id());
        if (teach.getTeacher().getT_num() == null || "".equals(teach.getTeacher().getT_num().trim()))
            throw new ParamException("请选择授课老师！");
        //设置教师
        Teacher teacher = teacherMapper.selTeacherByNum(teach.getTeacher().getT_num());
        if (teacher == null)
            throw new ParamException("不存在这个工号的教师！");
        t.setTeacher(teacher.getT_num());
        try {
            String[] grade = teach.getGradesIn();
            if (grade.length < 1)
                throw new ParamException("添加授课：请选择班级！");
            //判断班级存在
            String grades = "";
            for (int i = 0;i < grade.length;i ++) {
                grades += grade[i];
                if (i < grade.length - 1)
                    grades += ",";
            }
            Integer count = gradeMapper.selCountGrade(grade);
            if (count < grade.length)
                throw new ParamException("添加授课：参数有误！");
            //设置班级
            t.setGrade(grades);

        } catch (NullPointerException npe) {
            throw new ParamException("添加授课：请选择班级!");
        }
        if (teach.getDept().getDept_num() == null || "".equals(teach.getDept().getDept_num()))
            throw new ParamException("添加授课：请选择专业！");
        t.setDept(teach.getDept().getDept_num());
        String teach_id = t.getTeacher() + t.getCourse() + t.getDept();
        t.setTeach_id(teach_id);
        Integer reset = teacherMapper.checkInTeaches(t);
        if (reset < 1)
            throw new BusinessException("添加授课：添加失败！");
        TeachExample result = teacherMapper.checkOutById(teach_id);
        List<GradeExample> gradeExamples = new ArrayList<>();
        for (String s : result.getGrade().split(",")) {
            gradeExamples.add(gradeMapper.selGradeById(s));
        }
        result.setGrades(gradeExamples);
        return result;
    }

    @Override
    public TeachExample changeGrade(String teach_id, String[] grade) throws Exception {
        TeachExample result = teacherMapper.checkOutById(teach_id);
        if (result == null)
            throw new ParamException("修改授课班级：不存在该条授课记录！");
        Integer count = gradeMapper.selCountGrade(grade);
        if (count < grade.length)
            throw new ParamException("修改授课班级：参数出错！");
        String grades = "";
        for (int i = 0;i < grade.length;i ++) {
            grades += grade[i];
            if (i < grade.length - 1)
                grades += ",";
        }
        Integer reset = teacherMapper.changeTeachGrades(teach_id, grades);
        if (reset != 1)
            throw new BusinessException("修改授课班级：修改失败！");
        result.setGrade(grades);
        List<GradeExample> gradeExamples = new ArrayList<>();
        for (String s : result.getGrade().split(",")) {
            gradeExamples.add(gradeMapper.selGradeById(s));
        }
        result.setGrades(gradeExamples);
        return result;
    }

    @Override
    public void delTeach(String teach_id) throws Exception {
        Integer exist = teacherMapper.teachIsExist(teach_id);
        if (exist != 1)
            throw new ParamException("删除授课：不存在该记录！");
        Integer result = teacherMapper.delTeachById(teach_id);
        if (result != 1)
            throw new BusinessException("删除授课：删除失败！");
    }

    @Override
    //上传授课管理
    public List<TeachExample> uploadTeaches(MultipartFile multipartFile) {
        return null;
    }


}