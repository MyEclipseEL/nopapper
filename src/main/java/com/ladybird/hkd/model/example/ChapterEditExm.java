package com.ladybird.hkd.model.example;

import com.ladybird.hkd.model.pojo.Course;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.List;

/**
 * @author Shen
 * @description:
 * @create: 2019-04-13
 */
public class ChapterEditExm implements Serializable {
    private Course course;
    private String numbers;
    private String tip;
    private Integer[][] number;

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }


    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public String getTip() {
        return tip;
    }

    public Integer[][] getNumber() {
        return number;
    }

    public void setNumber(Integer[][] number) {
        this.number = number;
    }
}
