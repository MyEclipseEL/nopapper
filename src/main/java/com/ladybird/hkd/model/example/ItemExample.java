package com.ladybird.hkd.model.example;

import com.ladybird.hkd.model.pojo.Course;
import com.ladybird.hkd.model.pojo.ItemType;

import java.io.Serializable;

/**
 * @author Shen
 * @description:
 * @create: 2019-04-21
 */
public class ItemExample implements Serializable{
    private String item_id;         //题目编号
    private String item_title;      //题目标题
    private String item_desc;       //题目描述
    private String item_valid;      //题目正确答案
    private String item_choice;      //迷惑答案
    private ItemType item_type;       //题型
    private Course course;          //科目
    private String tip;             //备注

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public String getItem_desc() {
        return item_desc;
    }

    public void setItem_desc(String item_desc) {
        this.item_desc = item_desc;
    }

    public String getItem_valid() {
        return item_valid;
    }

    public void setItem_valid(String item_valid) {
        this.item_valid = item_valid;
    }

    public String getItem_choice() {
        return item_choice;
    }

    public void setItem_choice(String item_choice) {
        this.item_choice = item_choice;
    }

    public ItemType getItem_type() {
        return item_type;
    }

    public void setItem_type(ItemType item_type) {
        this.item_type = item_type;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}