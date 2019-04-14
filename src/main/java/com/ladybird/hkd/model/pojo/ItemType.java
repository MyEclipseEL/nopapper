package com.ladybird.hkd.model.pojo;

import com.ladybird.hkd.exception.ParamException;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 和泉纱雾 on 2019/3/3.
 */
public class ItemType implements Serializable{
    private String type_id;
    private String type_name;
    private Integer type_score;
    private String tip;

    public ItemType() {
    }


    public static void checkScoreList(List<ItemType> itemTypes) throws ParamException {
        for (ItemType itemType : itemTypes) {
            if (itemType.getType_id() == null || "".equals(itemType.getType_id().trim()))
                throw new ParamException("有题型id没有传！");
            if (itemType.getType_score() == null || itemType.getType_score() == 0)
                throw new ParamException("题型分数传递有误！");
        }

    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public Integer getType_score() {
        return type_score;
    }

    public void setType_score(Integer type_score) {
        this.type_score = type_score;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
