package com.ladybird.hkd.service;

import com.ladybird.hkd.model.json.ItemsOut;
import com.ladybird.hkd.model.pojo.Item;
import com.ladybird.hkd.model.pojo.ItemType;
import com.ladybird.hkd.model.vo.ItemVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by 和泉纱雾 on 2019/3/4.
 */
public interface ItemService {

    /**
     * 添加题型
     * @param typeName 题型名称
     */
    void addItemType(String typeName);

    List<ItemVO> checkOutItems(String course, String item_type) throws Exception;

    List<ItemsOut> getPaper(String course) throws Exception;

    List<ItemType> checkOutTypes() throws Exception;

    void updateTypeScore(List<ItemType> itemTypes) throws Exception;

    List<ItemVO> addItems(MultipartFile multipartFile, String course, String item_type) throws Exception;

    Item selItemById(@Param("item_id") Integer item_id) throws Exception;

    ItemVO changeItem(ItemVO item) throws Exception;

    ItemVO addItem(ItemVO item) throws Exception;

    Integer delItem(String item_id) throws Exception;

    String uploadItems(String course) throws Exception;

}
