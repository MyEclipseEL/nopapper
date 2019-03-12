package com.ladybird.hkd.mapper;

import com.ladybird.hkd.model.pojo.Item;
import com.ladybird.hkd.model.pojo.ItemType;
import com.ladybird.hkd.model.pojo.PaperEdit;
import com.ladybird.hkd.model.pojo.Score;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * Created by 和泉纱雾 on 2019/2/22.
 */
@Component
public interface PaperlessItemMapper {

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
     * 检出数据库的题目
     */
    List<Item> checkOutItemsByCourse(@Param("course") Integer course) throws Exception;

    PaperEdit checkOutPaperEdit() throws Exception;

    List<ItemType> checkOutItemTypes() throws Exception;

    Set<Item> checkOutItemsByCourseSet(@Param("course") Integer course)throws Exception;

    void checkInScore(Score score)throws Exception;
}
