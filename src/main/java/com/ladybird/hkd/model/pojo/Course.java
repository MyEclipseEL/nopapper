package com.ladybird.hkd.model.pojo;

import java.io.Serializable;

/**
 * @author Shen
 * @description:
 * @create: 2019-03-20
 */
public class Course implements Serializable{
    private String c_id;
    private String c_name;
    private Integer chapter;    //改课的章节数
    private String tip;

    public Course() {
    }

    public Course(String c_id, String c_name, Integer chapter, String tip) {
        this.c_id = c_id;
        this.c_name = c_name;
        this.chapter = chapter;
        this.tip = tip;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Integer getChapter() {
        return chapter;
    }

    public void setChapter(Integer chapter) {
        this.chapter = chapter;
    }
}
