package com.ladybird.hkd.model.json;

import com.ladybird.hkd.model.pojo.ItemType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Shen
 * @description: 题目json输出给前端
 * @create: 2019-03-13
 */
@ApiModel("组装数据")
public class ItemsOut<ItemVO> implements Serializable{
    @ApiModelProperty("题型")
    private ItemType itemType;
    @ApiModelProperty("题目")
    private Set<ItemVO> items;

    public ItemsOut(ItemType itemType, Set<ItemVO> items) {
        this.itemType = itemType;
        this.items = items;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public Set<ItemVO> getItems() {
        return items;
    }

    public void setItems(Set<ItemVO> items) {
        this.items = items;
    }
}
