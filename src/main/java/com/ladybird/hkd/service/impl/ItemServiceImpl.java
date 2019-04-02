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
import com.ladybird.hkd.util.excel.ReadItemExcel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    public List<Item> checkOutItems(String course) throws Exception {
        List<Item> items = paperlessItemMapper.checkOutItemsByCourse(course);

        if (items.size() <= 0)
            throw new ParamException("课程号没有对应的课程！");
        return items;
    }

    @Override
    public List<ItemsOut> getPaper(String course) throws Exception{
        Set<Item> items = paperlessItemMapper.checkOutItemsByCourseSet(course);
        PaperEdit paperEdit = paperlessItemMapper.checkOutPaperEdit(course);
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
        single.setType_score(paperEdit.getSingle_score());
        multiple.setType_score(paperEdit.getMultiple_score());
        checking.setType_score(paperEdit.getChecking_score());
        Set<ItemVO> singles = new HashSet<>();
        Set<ItemVO> multiples = new HashSet<>();
        Set<ItemVO> checkings = new HashSet<>();
        while (singles.size() < paperEdit.getSingle_count()
                || multiples.size() < paperEdit.getMultiple_count()
                || checkings.size() < paperEdit.getChecking_count()) {
            for (Item item : items) {
                List<String> choice = new ArrayList<>();
                ItemVO itemVO = new ItemVO();
                BeanUtils.copyProperties(item,itemVO);
                String[] valids = item.getItem_valid().split(",");
                for (String v:valids)
                    v.trim();
                itemVO.setItem_valid(valids);
                for (String s:item.getItem_choice().split("\\|\\@\\|"))
                    choice.add(s);
                if (item.getItem_type().equals(single.getType_id())
                        &&singles.size() < paperEdit.getSingle_count()) {

                    itemVO.setItem_type(single.getType_name());

                    singles.add(itemVO);
                }
                itemVO.setItem_choice(choice);
                if (item.getItem_type().equals(multiple.getType_id())
                        &&multiples.size() < paperEdit.getMultiple_count()){
                    itemVO.setItem_type(multiple.getType_name());
                    multiples.add(itemVO);
                }
                if (item.getItem_type().equals(checking.getType_id())
                        &&checkings.size() < paperEdit.getChecking_count()){
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

    @Override
    public String addItems(MultipartFile file) throws Exception {
        //创建处理Excel的类
        ReadItemExcel readItemExcel = new ReadItemExcel();
        //解析excel
        List<Item> items = null;
        int insertResult = 0;   //记录插入数
        String insertMsg = "";
        try {
            items = readItemExcel.getExcelInfo(file);
            System.out.println(items);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(insertMsg);

        }
        return insertMsg;
    }


}
