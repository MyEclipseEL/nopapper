package com.ladybird.hkd.model.pojo;

/**
 * @author Shen
 * @description: 组卷
 * @create: 2019-03-14
 */
public class PaperEdit {
    private int id;                 //id
    private int single_choice;      //单选题个数
    private int multiple_choice;    //多选题个数
    private int checking;           //判断题个数

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSingle_choice() {
        return single_choice;
    }

    public void setSingle_choice(int single_choice) {
        this.single_choice = single_choice;
    }

    public int getMultiple_choice() {
        return multiple_choice;
    }

    public void setMultiple_choice(int multiple_choice) {
        this.multiple_choice = multiple_choice;
    }

    public int getChecking() {
        return checking;
    }

    public void setChecking(int checking) {
        this.checking = checking;
    }
}
