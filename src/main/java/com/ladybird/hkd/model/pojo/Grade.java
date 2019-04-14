package com.ladybird.hkd.model.pojo;


import com.ladybird.hkd.model.pojo.Department;

import java.io.Serializable;

/**
 * @author Shen
 * @description:
 * @create: 2019-04-16
 */
public class Grade implements Serializable{
    private String g_id;
    private Integer g_year;
    private Integer g_class;
    private String dept;

    public String getG_id() {
        return g_id;
    }

    public void setG_id(String g_id) {
        this.g_id = g_id;
    }

    public Integer getG_year() {
        return g_year;
    }

    public void setG_year(Integer g_year) {
        this.g_year = g_year;
    }

    public Integer getG_class() {
        return g_class;
    }

    public void setG_class(Integer g_class) {
        this.g_class = g_class;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
