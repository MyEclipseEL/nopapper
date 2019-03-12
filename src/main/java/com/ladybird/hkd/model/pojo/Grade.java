package com.ladybird.hkd.model.pojo;


import java.util.Date;

/**
 * @author Shen
 * @description: 年级实体
 * @create: 2019-03-13
 */
public class Grade {
    private Integer g_id;
    private Date g_year;
    private Integer g_class;

    public Grade() {
    }

    public Grade(Integer g_id, Date g_year, Integer g_class) {
        this.g_id = g_id;
        this.g_year = g_year;
        this.g_class = g_class;
    }

    public Integer getG_id() {
        return g_id;
    }

    public void setG_id(Integer g_id) {
        this.g_id = g_id;
    }

    public Date getG_year() {
        return g_year;
    }

    public void setG_year(Date g_year) {
        this.g_year = g_year;
    }

    public Integer getG_class() {
        return g_class;
    }

    public void setG_class(Integer g_class) {
        this.g_class = g_class;
    }
}
