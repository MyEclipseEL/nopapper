package com.ladybird.hkd.model.pojo;

import java.math.BigDecimal;

/**
 * @author Shen
 * @description:
 * @create: 2019-03-21
 */
public class Score {
    private String student;
    private Integer course;
    private BigDecimal s_score;

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public BigDecimal getS_score() {
        return s_score;
    }

    public void setS_score(BigDecimal s_score) {
        this.s_score = s_score;
    }
}
