package com.ladybird.hkd.service;

import com.ladybird.hkd.model.json.ItemsOut;
import com.ladybird.hkd.model.vo.ItemVO;

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

    Object checkOutItems(Integer course) throws Exception;

    List<ItemsOut> getPaper(Integer course) throws Exception;
}
