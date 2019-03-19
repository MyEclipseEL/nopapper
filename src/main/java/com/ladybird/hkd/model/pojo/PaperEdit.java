package com.ladybird.hkd.model.pojo;

import com.ladybird.hkd.exception.ParamException;

/**
 * @author Shen
 * @description: 组卷
 * @create: 2019-03-14
 */
public class PaperEdit {
    private Integer id;                 //id
    private Integer single_choice;      //单选题个数
    private Integer multiple_choice;    //多选题个数
    private Integer checking;           //判断题个数

    public PaperEdit() {
    }

    public PaperEdit(Integer id, Integer single_choice, Integer multiple_choice, Integer checking) {
        this.id = id;
        this.single_choice = single_choice;
        this.multiple_choice = multiple_choice;
        this.checking = checking;
    }

    public static void checkParams(PaperEdit temp) throws ParamException {
        try {
            if (temp.getSingle_choice() == null || temp.getSingle_choice() == 0)
                throw new ParamException("请选择单选题个数！");
            if (temp.getMultiple_choice() == null || temp.getMultiple_choice() == 0)
                throw new ParamException("请选择多选题个数！");
            if (temp.getChecking() == null || temp.getChecking() == 0)
                throw new ParamException("请选择判断题个数！");
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new ParamException("参数传递错误");
        }

    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSingle_choice() {
        return single_choice;
    }

    public void setSingle_choice(Integer single_choice) {
        this.single_choice = single_choice;
    }

    public Integer getMultiple_choice() {
        return multiple_choice;
    }

    public void setMultiple_choice(Integer multiple_choice) {
        this.multiple_choice = multiple_choice;
    }

    public Integer getChecking() {
        return checking;
    }

    public void setChecking(Integer checking) {
        this.checking = checking;
    }
}
