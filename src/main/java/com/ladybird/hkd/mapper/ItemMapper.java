package com.ladybird.hkd.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * Created by 和泉纱雾 on 2019/2/22.
 */
@Component
public interface ItemMapper {

    /**
     *  添加题目类型
     * @param itemId 题目id
     * @param itemName 题目名字
     * */
    void addItemType(@Param("itemId") String itemId,@Param("itemName") String itemName);

    /**
     * 建立题库表
     */
    void createItemBank(@Param("tableName") String tableName);

    /**
     *
     * */
}
