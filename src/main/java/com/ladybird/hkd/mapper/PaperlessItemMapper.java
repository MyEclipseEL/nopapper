package com.ladybird.hkd.mapper;

import com.ladybird.hkd.model.example.ItemExample;
import com.ladybird.hkd.model.example.PaperEditExample;
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
    List<ItemExample> checkOutItems(@Param("course") String course,@Param("item_type")String item_type) throws Exception;


    /**
     * 查找所有的题型
     *@return List<ItemType>
     *Date: 2019/4/1
     */
    List<ItemType> checkOutItemTypes() throws Exception;

    /**
     * 通过课程查找题目，并乱序存储
     *@param course String
     *@return Set<Item>
     *Date: 2019/4/1
     */
    Set<Item> checkOutItemsByCourseSet(@Param("course") String course)throws Exception;

    /**
     * 添加成绩
     *@param score Score
     *Date: 2019/4/1
     */
    void checkInScore(Score score)throws Exception;

    void updateTypeScore(List<ItemType> itemTypes) throws Exception;

    /**
     * 导入题库，并返回插入条数
     *@param items List<Item>
     *@return Integer
     *Date: 2019/4/1
     */
    Integer checkInItems(@Param("items") List<Item> items)throws Exception;

    /**
     * 判断题单独插入
     *@param items List<Item>
     *@return int
     *Date: 2019/4/1
     */
    int checkInChecking(List<Item> items) throws Exception;

    /**
     * @param id String
     *@param course String 课程号
     *@param url String     cos地址
     * @return: Date: 2019/4/9
     */
    Integer checkInCOS(@Param("id") String id,@Param("course") String course,@Param("url") String url) throws Exception;

    Item selItemById(@Param("item_id") Integer item_id) throws Exception;

    Integer changeItem(Item exist) throws Exception;

    Integer addItem(Item item) throws Exception;

    int delItemById(int item_id) throws Exception;
}
