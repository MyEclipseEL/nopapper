package com.ladybird.hkd.model.example;

import com.ladybird.hkd.model.pojo.Student;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Shen
 * @description:
 * @create: 2019-04-16
 */
public class ScoreExample implements Serializable {
    private StudentExample student;
    private ExamExample exam;
    private BigDecimal s_score;

    public StudentExample getStudent() {
        return student;
    }

    public void setStudent(StudentExample student) {
        this.student = student;
    }

    public ExamExample getExam() {
        return exam;
    }

    public void setExam(ExamExample exam) {
        this.exam = exam;
    }

    public BigDecimal getS_score() {
        return s_score;
    }

    public void setS_score(BigDecimal s_score) {
        this.s_score = s_score;
    }
}
