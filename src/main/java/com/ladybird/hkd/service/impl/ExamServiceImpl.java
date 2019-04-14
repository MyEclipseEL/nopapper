package com.ladybird.hkd.service.impl;

import com.ladybird.hkd.enums.ExamStateEnum;
import com.ladybird.hkd.exception.BusinessException;
import com.ladybird.hkd.exception.ParamException;
import com.ladybird.hkd.mapper.CourseMapper;
import com.ladybird.hkd.mapper.ExamMapper;
import com.ladybird.hkd.mapper.PaperlessItemMapper;
import com.ladybird.hkd.model.example.ChapterEditExm;
import com.ladybird.hkd.model.example.GradeExample;
import com.ladybird.hkd.model.example.PaperEditExample;
import com.ladybird.hkd.model.json.ExamJsonIn;
import com.ladybird.hkd.model.example.ExamExample;
import com.ladybird.hkd.model.pojo.*;
import com.ladybird.hkd.service.ExamService;
import com.ladybird.hkd.service.MessageService;
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
    private MessageService messageService;
    @Override
    public List<ExamExample> selectExamByStu(Student student) throws Exception {
        List<ExamExample> examExamples = examMapper.selectExamByStu(student);
        for (ExamExample examExample : examExamples) {
            examExample.setFinish_time(new Date(examExample.getFinish_time().getTime() / 1000));
            examExample.setBegin_time(new Date(examExample.getBegin_time().getTime() / 1000));
        }
        return examExamples;
    }

    @Override
    public int checkExamState(String exam) throws Exception{
        return examMapper.checkExamState(exam);
    }

    @Override
    public ExamExample checkOutExamById(String exam) throws Exception {
        ExamExample examExample = examMapper.checkOutExamById(exam);
        if (examExample == null) {
            throw new Exception();
        }
        return examExample;
    }

    @Override
    public List<ExamExample> checkOutByTeach(Teach teach) throws Exception {
        String[] grades = teach.getGrade().split("\\ ");
        List<ExamExample> examExamples = examMapper.checkOutByCourseGrades(teach.getCourse(), grades);
        for (ExamExample e : examExamples) {
            try {
                e.setBegin_time(new Date(e.getBegin_time().getTime() / 1000));
            } catch (NullPointerException ne) {

            }
        }
        return examExamples;
    }


    @Override
    public void changeStateAndBegin(String[] exams,Integer state) throws Exception {
        Date date = new Date();
        examMapper.changeStateAndBegin(exams,date,state);
    }

    @Override
    public PaperEditExample updatePaper(PaperEdit paperEdit) throws Exception {
        PaperEditExample editExample = new PaperEditExample();
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
        BeanUtils.copyProperties(paperEdit,editExample);
        editExample.setCourse(course);
        return editExample;
    }

    @Override
    public List<PaperEditExample> checkOutPaper() throws Exception {
        return examMapper.checkOutPaperEditExs();
    }

    @Override
    public List<ExamExample> addExams(ExamJsonIn exam) throws Exception {
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
    public ExamExample beginExam(String t_num, String[] grades, String course) throws Exception {
        String grade = "";
        //判断考试班级是否已经存在
        List<GradeExample> gradeExampleList = messageService.gradesNotInExam(t_num, course);
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

    @Override
    public PaperEditExample checkOutPaperByCourse(String exam) throws Exception{
        String course = examMapper.selCourseById(exam);
        return examMapper.checkOutPaperEditExm(course);
    }

    @Override
    public List<ChapterEditExm> checkOutChapter(String course) throws Exception {
        return examMapper.checkOutChapter(course);
    }

    //配置课程的章节题目数量
    @Override
    public ChapterEditExm checkInChapter(String course, Integer[][] number, String tip) throws Exception {
        String numbers = "";
        for (int n = 0;n < number.length; n ++) {
            Integer[] cur = number[n];
            for (int i =0 ;i < cur.length;i ++) {
                numbers += cur[i];
                if (i < cur.length -1)
                    numbers += ",";
            }
            if (n < number.length - 1)
                numbers += "\\|";
        }
        if (tip == null || "".equals(tip.trim()))
            tip = null;
        Course exist = courseMapper.selCourseById(course);
        if (exist == null)
            throw new ParamException("课程不存在!");
        examMapper.delChapterByCourse(course);
        examMapper.checkInChapter(course, numbers, tip);
        ChapterEditExm result = examMapper.checkOutChapter(course).get(0);
        return result;
    }


}
