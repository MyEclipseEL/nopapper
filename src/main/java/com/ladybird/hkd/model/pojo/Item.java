package com.ladybird.hkd.model.pojo;

/**
 * @author Shen
 * @description: 题目实体
 * @create: 2019-03-22
 */
public class Item {
    private String item_id;         //题目编号
    private String item_title;      //题目标题
    private String item_desc;       //题目描述
    private String item_valid;      //题目正确答案
    private String item_wrong;      //迷惑答案
    private String item_type;       //题型
    private String course;          //科目

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

    public String getItem_wrong() {
        return item_wrong;
    }

    public void setItem_wrong(String item_wrong) {
        this.item_wrong = item_wrong;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
