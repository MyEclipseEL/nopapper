package com.ladybird.hkd.service.impl;

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

    @Override
    public List<Grade> gradesNotInExam(String t_num,String course) throws Exception {
        Date now = new Date();
        String sgrade = teacherMapper.selGradesByCourse(t_num, course);
        String[] grades = sgrade.split(",");
        //判断八周内是否参加过考试
        long time= 8 * 7 * 24 * 3600 * 100;
        return gradeMapper.selGradesNotInExam(t_num,course,new Date(now.getTime()-time),grades);
    }


}
