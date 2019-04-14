package com.ladybird.hkd.model.json;

import com.ladybird.hkd.model.pojo.ItemType;
import com.ladybird.hkd.model.vo.ItemVO;
import io.swagger.annotations.ApiModelProperty;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author Shen
 * @description:
 * @create: 2019-04-16
 */
public class ItemListOut implements Serializable{
    @ApiModelProperty("题型")
    private ItemType itemType;
    @ApiModelProperty("题目")
    private List<ItemVO> items;

    public ItemListOut(ItemType itemType, List<ItemVO> items) {
        this.itemType = itemType;
        this.items = items;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public List<ItemVO> getItems() {
        return items;
    }

    public void setItems(List<ItemVO> items) {
        this.items = items;
    }
}
