package com.ladybird.hkd.mapper;

import com.ladybird.hkd.model.example.PaperEditExample;
import com.ladybird.hkd.model.json.ExamJsonOut;
import com.ladybird.hkd.model.pojo.Exam;
import com.ladybird.hkd.model.pojo.PaperEdit;
import com.ladybird.hkd.model.pojo.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by 和泉纱雾 on 2019/3/12.
 */
@Component
public interface ExamMapper {
    List<ExamJsonOut> selectExamByStu(Student student) throws Exception;

    int checkExamState(@Param("exam_id") String exam_id) throws Exception;

    ExamJsonOut checkOutExamById(@Param("exam_id") String exam_id) throws Exception;

    List<ExamJsonOut> checkOutExamByIds(@Param("ids") List<String> ids) throws Exception;

    List<ExamJsonOut> checkOutByCourseGradesDept(@Param("course") String course,@Param("grades") String[] grades,@Param("dept") String dept) throws Exception;

    void changeStateAndBegin(String[] ids, @Param("begin_time") Date date, @Param("state") Integer state) throws Exception;

    /**
     * 通过课程查找试卷配置
     *@param course String
     *@return PaperEditExample
     *Date: 2019/4/1
     */
    PaperEditExample checkOutPaperEditExm(@Param("course") String course) throws Exception;

    /**
     * 通过课程查找试卷配置
     *
     * @param course String
     * @return PaperEditExample
     * Date: 2019/4/1
     */
    PaperEdit checkOutPaperEdit(@Param("course") String course) throws Exception;
    /**
     * 添加一个考试的配置
     *@param paperEdit PaperEdit
     *@return int
     *Date: 2019/4/1
     */
    int checkInPaperEdit(PaperEdit paperEdit) throws Exception;

    /**
     * @param paperEdit PaperEdit
     * @return int
     * Date: 2019/4/2
     */
    int changePaperEdit(PaperEdit paperEdit) throws Exception;

    /**
     * @return List<PaperEditExample>
     * Date: 2019/4/2
     */
    List<PaperEditExample> checkOutPaperEditExs() throws Exception;


    void addExams(@Param("exams") List<Exam> exams) throws Exception;

    void addExam(@Param("exam") Exam exam) throws Exception;

    //查找近期有过考试的班级
    List<String> selGradesByTC(@Param("t_num") String t_num,@Param("course") String course, @Param("begin_date") Date begin_date);
}
