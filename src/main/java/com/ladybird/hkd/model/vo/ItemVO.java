package com.ladybird.hkd.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Set;

/**
 * @author Shen
 * @description: itemView 方便前端的数据返回类
 * @create: 2019-03-18
 */
@ApiModel
public class ItemVO {
    @ApiModelProperty("题目编号")
    private String item_id;         //题目编号
    @ApiModelProperty("题目标题")
    private String item_title;      //题目标题
    @ApiModelProperty("题目描述")
    private String item_desc;       //题目描述
    @ApiModelProperty("题目正确答案")
    private String[] item_valid;      //题目正确答案
    @ApiModelProperty("迷惑答案")
    private List<String> item_choice;      //选项
    @ApiModelProperty("题型")
    private String item_type;       //题型
    @ApiModelProperty("科目")
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

    public String[] getItem_valid() {
        return item_valid;
    }

    public void setItem_valid(String[] item_valid) {
        this.item_valid = item_valid;
    }

    public List<String> getItem_choice() {
        return item_choice;
    }

    public void setItem_choice(List<String> item_choice) {
        this.item_choice = item_choice;
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
