package com.ladybird.hkd.service.impl;

import com.ladybird.hkd.exception.ParamException;
import com.ladybird.hkd.mapper.CourseMapper;
import com.ladybird.hkd.mapper.PaperlessItemMapper;
import com.ladybird.hkd.model.json.ItemsOut;
import com.ladybird.hkd.model.pojo.Item;
import com.ladybird.hkd.model.pojo.ItemType;
import com.ladybird.hkd.model.pojo.PaperEdit;
import com.ladybird.hkd.model.vo.ItemVO;
import com.ladybird.hkd.service.ItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by 和泉纱雾 on 2019/3/4.
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private PaperlessItemMapper paperlessItemMapper;

    @Override
    public void addItemType(String typeName) {

    }

    @Override
    public List<Item> checkOutItems(Integer course) throws Exception {
        List<Item> items = paperlessItemMapper.checkOutItemsByCourse(course);

        if (items.size() <= 0)
            throw new ParamException("课程号没有对应的课程！");
        return items;
    }

    @Override
    public List<ItemsOut> getPaper(Integer course) throws Exception{
        Set<Item> items = paperlessItemMapper.checkOutItemsByCourseSet(course);
        PaperEdit paperEdit = paperlessItemMapper.checkOutPaperEdit();
        List<ItemType> itemTypes = paperlessItemMapper.checkOutItemTypes();
        String courseName = courseMapper.findNameById(course);
        ItemType single = new ItemType() ;
        ItemType multiple = new ItemType();
        ItemType checking = new ItemType();
        for (ItemType itemType : itemTypes) {
            if ("单选题".equals(itemType.getType_name()))
                single = itemType;
            if ("多选题".equals(itemType.getType_name()))
                multiple = itemType;
            if ("判断题".equals(itemType.getType_name()))
                checking = itemType;
        }
        Set<ItemVO> singles = new HashSet<>();
        Set<ItemVO> multiples = new HashSet<>();
        Set<ItemVO> checkings = new HashSet<>();
        while (singles.size() < paperEdit.getSingle_choice()
                || multiples.size() < paperEdit.getMultiple_choice()
                || checkings.size() < paperEdit.getChecking()) {
            for (Item item : items) {
                ItemVO itemVO = new ItemVO();
                BeanUtils.copyProperties(item,itemVO);
                itemVO.setItem_valid(item.getItem_valid().split("\\ "));
                itemVO.setItem_wrong(item.getItem_wrong().split("\\ "));
                if (item.getItem_type().equals(single.getType_id())
                        &&singles.size() < paperEdit.getSingle_choice()) {

                    itemVO.setItem_type(single.getType_name());

                    singles.add(itemVO);
                }
                if (item.getItem_type().equals(multiple.getType_id())
                        &&multiples.size() < paperEdit.getMultiple_choice()){
                    itemVO.setItem_type(multiple.getType_name());
                    multiples.add(itemVO);
                }
                if (item.getItem_type().equals(checking.getType_id())
                        &&checkings.size() < paperEdit.getChecking()){
                    itemVO.setItem_type(checking.getType_name());
                    checkings.add(itemVO);
                }
            }
        }
        List<ItemsOut> result = new ArrayList<>();
        result.add(new ItemsOut(single, singles));
        result.add(new ItemsOut(multiple, multiples));
        result.add(new ItemsOut(checking, checkings));
        return result;
    }

    @Override
    public List<ItemType> checkOutTypes() throws Exception {
        return paperlessItemMapper.checkOutItemTypes();
    }

    @Override
    public void updateTypeScore(List<ItemType> itemTypes) throws Exception {
        paperlessItemMapper.updateTypeScore(itemTypes);
    }
}
