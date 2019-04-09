package com.ladybird.hkd.model.pojo;

/**
 * @author Shen
 * @description:
 * @create: 2019-04-14
 */
public class Admin {
    private String id;
    private String name;
    private String pwd;
    private String group_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }
}