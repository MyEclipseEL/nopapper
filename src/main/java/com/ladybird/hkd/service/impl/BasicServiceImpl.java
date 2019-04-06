package com.ladybird.hkd.service.impl;

import com.ladybird.hkd.exception.BusinessException;
import com.ladybird.hkd.exception.ParamException;
import com.ladybird.hkd.mapper.*;
import com.ladybird.hkd.model.pojo.Course;
import com.ladybird.hkd.model.pojo.Department;
import com.ladybird.hkd.model.pojo.Faculty;
import com.ladybird.hkd.model.pojo.Grade;
import com.ladybird.hkd.service.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BasicServiceImpl implements BasicService {
    @Autowired
    private GradeMapper gradeMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Grade> gradesNotInExam(String t_num,String course) throws Exception {
        Date now = new Date();
        //查找课程是否存在
        Course exist = courseMapper.selCourseById(course);
        if (exist == null)
            throw new ParamException("课程不存在！course：" + course);
        //教师教授的班级
        String sgrade = teacherMapper.selGradesByCourse(t_num, course);
        String[] grades = sgrade.split(",");
        long time= 8 * 7 * 24 * 3600 * 100;
        //八周内参加过该门课程考试的班级
        List<String> gradeList = examMapper.selGradesByTC(t_num, course,new Date(now.getTime()-time));
        if (gradeList.size() != 0) {
            String already = "";
            if (gradeList.size()==1){
                already += gradeList.get(0);
            }else {
                for (int i = 0; i < gradeList.size(); i++) {
                    already += gradeList.get(i);
                    if (i < gradeList.size() - 1)
                        already += ",";
                }
            }
            return gradeMapper.selGradesNotInExam(t_num, course, grades, already);
        }else {
            return gradeMapper.selGradesNotInExam(t_num, course, grades,null);
        }
    }


}
