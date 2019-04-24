package com.ladybird.hkd.model.json;

import java.util.List;

/**
 * @author Shen
 * @description:
 * @create: 2019-04-17
 */
public class ChapterIn {
    private List<Integer[]> number;
    private String course;
    private String tip;

    public List<Integer[]> getNumber() {
        return number;
    }

    public void setNumber(List<Integer[]> number) {
        this.number = number;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
