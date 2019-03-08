package com.ladybird.hkd.service.impl;

import com.ladybird.hkd.mapper.ItemMapper;
import com.ladybird.hkd.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by 和泉纱雾 on 2019/3/4.
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public void addItemType(String typeName) {

    }
}
