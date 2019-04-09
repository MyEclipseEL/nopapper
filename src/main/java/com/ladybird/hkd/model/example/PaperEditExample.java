package com.ladybird.hkd.model.example;

import com.ladybird.hkd.model.pojo.Course;

/**
 * @author Shen
 * @description:
 * @create: 2019-04-20
 */
public class PaperEditExample {
    private String id;
    private Course course;
    private Integer single_count;
    private Integer single_score;
    private Integer multiple_count;
    private Integer multiple_score;
    private Integer checking_count;
    private Integer checking_score;
    private Integer duration;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Integer getSingle_count() {
        return single_count;
    }

    public void setSingle_count(Integer single_count) {
        this.single_count = single_count;
    }

    public Integer getSingle_score() {
        return single_score;
    }

    public void setSingle_score(Integer single_score) {
        this.single_score = single_score;
    }

    public Integer getMultiple_count() {
        return multiple_count;
    }

    public void setMultiple_count(Integer multiple_count) {
        this.multiple_count = multiple_count;
    }

    public Integer getMultiple_score() {
        return multiple_score;
    }

    public void setMultiple_score(Integer multiple_score) {
        this.multiple_score = multiple_score;
    }

    public Integer getChecking_count() {
        return checking_count;
    }

    public void setChecking_count(Integer checking_count) {
        this.checking_count = checking_count;
    }

    public Integer getChecking_score() {
        return checking_score;
    }

    public void setChecking_score(Integer checking_score) {
        this.checking_score = checking_score;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}