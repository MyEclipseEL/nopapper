package com.ladybird.hkd.service;

import com.ladybird.hkd.model.json.ItemsOut;
import com.ladybird.hkd.model.pojo.ItemType;
import com.ladybird.hkd.model.vo.ItemVO;
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

    Object checkOutItems(String course) throws Exception;

    List<ItemsOut> getPaper(String course) throws Exception;

    List<ItemType> checkOutTypes() throws Exception;

    void updateTypeScore(List<ItemType> itemTypes) throws Exception;

    String addItems(MultipartFile multipartFile) throws Exception;
}
