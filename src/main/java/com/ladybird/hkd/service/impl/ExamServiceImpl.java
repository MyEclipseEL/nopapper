package com.ladybird.hkd.service.impl;

import com.ladybird.hkd.enums.ExamStateEnum;
import com.ladybird.hkd.exception.BusinessException;
import com.ladybird.hkd.exception.ParamException;
import com.ladybird.hkd.mapper.CourseMapper;
import com.ladybird.hkd.mapper.ExamMapper;
import com.ladybird.hkd.mapper.PaperlessItemMapper;
import com.ladybird.hkd.model.example.PaperEditExample;
import com.ladybird.hkd.model.json.ExamJsonIn;
import com.ladybird.hkd.model.json.ExamJsonOut;
import com.ladybird.hkd.model.pojo.*;
import com.ladybird.hkd.service.BasicService;
import com.ladybird.hkd.service.ExamService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Shen
 * @description: 考试业务层实现类
 * @create: 2019-03-21
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private PaperlessItemMapper paperlessItemMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private BasicService basicService;

    @Override
    public List<ExamJsonOut> selectExamByStu(Student student) throws Exception {
        List<ExamJsonOut> examJsonOuts = examMapper.selectExamByStu(student);
        for (ExamJsonOut examJsonOut:examJsonOuts) {
            examJsonOut.setFinish_time(new Date(examJsonOut.getFinish_time().getTime() / 1000));
            examJsonOut.setBegin_time(new Date(examJsonOut.getBegin_time().getTime() / 1000));
        }
        return examJsonOuts;
    }

    @Override
    public int checkExamState(String exam) throws Exception{
        return examMapper.checkExamState(exam);
    }

    @Override
    public ExamJsonOut checkOutExamById(String exam) throws Exception {
        ExamJsonOut examJsonOut = examMapper.checkOutExamById(exam);
        if (examJsonOut == null) {
            throw new Exception();
        }
        return examJsonOut;
    }

    @Override
    public List<ExamJsonOut> checkOutByTeach(Teach teach) throws Exception {
        String[] grades = teach.getGrade().split("\\ ");
        List<ExamJsonOut> examJsonOuts = examMapper.checkOutByCourseGradesDept(teach.getCourse(), grades,teach.getDept());
        for (ExamJsonOut e : examJsonOuts) {
            try {
                e.setBegin_time(new Date(e.getBegin_time().getTime() / 1000));
            } catch (NullPointerException ne) {
            }
        }
        return examJsonOuts;
    }

    @Override
    public void changeStateAndBegin(String[] exams,Integer state) throws Exception {
        Date date = new Date();
        examMapper.changeStateAndBegin(exams,date,state);
    }

    @Override
    public PaperEdit updatePaper(PaperEdit paperEdit) throws Exception {
        //判断id是否存在 不存在则是新增
            //判断课程是否存在记录
        Course course = courseMapper.selCourseById(paperEdit.getCourse());
        if (course == null)
            throw new ParamException("课程不存在！");
        PaperEdit exist = examMapper.checkOutPaperEdit(paperEdit.getCourse());
        paperEdit.setId(paperEdit.getCourse());
        int result = -1;
        if (exist == null) {
             result = examMapper.checkInPaperEdit(paperEdit);
            System.out.println(result);
        }else {
            examMapper.changePaperEdit(paperEdit);
        }
        return paperEdit;
    }

    @Override
    public List<PaperEditExample> checkOutPaper() throws Exception {
        return examMapper.checkOutPaperEditExs();
    }

    @Override
    public List<ExamJsonOut> addExams(ExamJsonIn exam) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
        String exam_id = format.format(new Date()) + exam.getCourse() + exam.getDept();
        List<String> ids = new ArrayList<>();
        List<Exam> exams = new ArrayList<>();
//        for (Integer i : exam.getGrades()) {
//            Exam e = new Exam();
//            BeanUtils.copyProperties(exam,e);
//            e.setExam_id(exam_id + i);
//            e.setGrade(i);
//            exams.add(e);
//            ids.add(e.getExam_id());
//        }
        try {
            examMapper.addExams(exams);
        } catch (SQLIntegrityConstraintViolationException se) {
            se.printStackTrace();
            throw new BusinessException("该考试已存在！");
        }
        return examMapper.checkOutExamByIds(ids);
    }

    @Override
    public ExamJsonOut beginExam(String t_num, String[] grades, String course) throws Exception {
        String grade = "";
        //判断考试班级是否已经存在
        List<Grade> gradeList = basicService.gradesNotInExam(t_num, course);
        for (int i = 0 ;i < grades.length; i++) {
            grade += grades[i];
            if (i<grades.length-1)
                grade += ",";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
        String exam_id = format.format(new Date()) + t_num + course;
        PaperEditExample paperEdit = examMapper.checkOutPaperEditExm(course);
        Date now = new Date();
        Exam exam = new Exam(exam_id,course,t_num,grade,new Date(now.getTime()+(paperEdit.getDuration()*60*1000)),null,
                paperEdit.getDuration(), ExamStateEnum.BEGIN.getCode());
        examMapper.addExam(exam);
        return examMapper.checkOutExamById(exam_id);
    }

}
