package com.ladybird.hkd.model.example;

import com.ladybird.hkd.model.pojo.Group;

/**
 * @author Shen
 * @description:
 * @create: 2019-04-14
 */
public class AdminExample {
    private String id;
    private String name;
    private String pwd;
    private Group group_id;

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

    public Group getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Group group_id) {
        this.group_id = group_id;
    }
}
