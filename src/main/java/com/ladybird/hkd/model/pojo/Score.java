package com.ladybird.hkd.model.pojo;

import java.math.BigDecimal;

/**
 * @author Shen
 * @description:
 * @create: 2019-03-21
 */
public class Score {
    private String student;
    private String exam;
    private BigDecimal s_score;

    public Score() {
    }

    public Score(String student, String exam, BigDecimal s_score) {
        this.exam = exam;
        this.s_score = s_score;
        this.student = student;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }

    public BigDecimal getS_score() {
        return s_score;
    }

    public void setS_score(BigDecimal s_score) {
        this.s_score = s_score;
    }
}
