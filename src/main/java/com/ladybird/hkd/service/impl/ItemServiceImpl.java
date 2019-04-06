package com.ladybird.hkd.service.impl;

import com.ladybird.hkd.exception.BusinessException;
import com.ladybird.hkd.exception.ParamException;
import com.ladybird.hkd.mapper.CourseMapper;
import com.ladybird.hkd.mapper.ExamMapper;
import com.ladybird.hkd.mapper.PaperlessItemMapper;
import com.ladybird.hkd.model.example.ItemExample;
import com.ladybird.hkd.model.example.PaperEditExample;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * Created by 和泉纱雾 on 2019/3/4.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
public class ItemServiceImpl implements ItemService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private PaperlessItemMapper paperlessItemMapper;
    @Autowired
    private ExamMapper examMapper;

    @Override
    public void addItemType(String typeName) {

    }

    @Override
    public List<ItemVO> checkOutItems(String course,String item_type) throws Exception {
        List<Item> items = paperlessItemMapper.checkOutItemsByCourse(course,item_type);

        if (items.size() <= 0)
            throw new ParamException("课程号没有对应的课程！");
        List<ItemVO> vos = new ArrayList<>();
        for (Item item : items) {
            vos.add(ItemVO.Item2VOConveter(item));
        }
        return vos;
    }

    @Override
        public List<ItemsOut> getPaper(String course) throws Exception{
        Set<Item> items = paperlessItemMapper.checkOutItemsByCourseSet(course);
        PaperEditExample paperEdit =examMapper .checkOutPaperEditExm(course);
        List<ItemType> itemTypes = paperlessItemMapper.checkOutItemTypes();
//        String courseName = courseMapper.findNameById(course);
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
                if (!item.getItem_type().equalsIgnoreCase("C")) {
                    for (String s : item.getItem_choice().split("\\|\\@\\|"))
                        choice.add(s);
                    itemVO.setItem_choice(choice);
                }
                if (item.getItem_type().equals(single.getType_id())
                        &&singles.size() < paperEdit.getSingle_count()) {

                    itemVO.setItem_type(single.getType_name());

                    singles.add(itemVO);
                }
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
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    public List<Item> addItems(MultipartFile file,String course,String item_type) throws Exception {
        //创建处理Excel的类
        ReadItemExcel readItemExcel = new ReadItemExcel();
        //解析excel
        List<Item> items = null;
        int insertResult = 0;   //记录插入数
        items = readItemExcel.getExcelInfo(file,course,item_type);
        //TODO 放开注释
        /*if (!item_type.equalsIgnoreCase("C"))
            insertResult = paperlessItemMapper.checkInItems(items);
        else
            insertResult = paperlessItemMapper.checkInChecking(items);
        if (insertResult < items.size())
            throw new BusinessException("插入出错！");*/
        System.out.println(items);
        return items;
    }

    @Override
    public Item selItemById(Integer item_id) throws Exception{
        return paperlessItemMapper.selItemById(item_id);
    }

    /**
     * 修改题型
     *@param item ItemVO
     * @throws Exception
     *Date: 2019/4/3
     */
    @Override
    public ItemVO changeItem(ItemVO item) throws Exception {
        ItemVO.validItem(item);
        Item exist = paperlessItemMapper.selItemById(Integer.parseInt(item.getItem_id()));
        if (exist == null)
            throw new BusinessException("不存在这道题！");
        if (item.getItem_title() != null && !"".equals(item.getItem_title().trim()))
            exist.setItem_title(item.getItem_title());
        if (item.getTip() != null && !"".equals(item.getTip().trim()))
            exist.setTip(item.getTip());
        exist.setItem_desc(item.getItem_desc());
        //判断题单独修改
        if (item.getItem_type().trim().equalsIgnoreCase("C")) {
            if (item.getItem_valid().length != 1)
                throw new ParamException("题目类型不匹配！");
//            if (item.getItem_choice() != null || item.getItem_choice().size() != 0)
//                throw new ParamException("题目类型不匹配！");
            exist.setItem_valid(item.getItem_valid()[0]);
        }else {
            if (item.getItem_choice().size() <2)
                throw new ParamException("题目类型不匹配!");
            List<String> choices = item.getItem_choice();
            String choice = "";
            for (int i = 0;i < choices.size(); i++) {
                choice += choices.get(i);
                if (i < choices.size()-1)
                    choice += "|@|";
            }
            exist.setItem_choice(choice);
            if (item.getItem_type().trim().equalsIgnoreCase("B")) {
                if (choices.size() != 5)
                    throw new ParamException("题目类型不匹配！");
                String[] valid = item.getItem_valid();
                String v = "";
                for (int i = 0; i < valid.length; i++) {
                    v += valid[i];
                    if (i < valid.length - 1)
                        v += ",";
                }
                exist.setItem_valid(v);
            }else {
                if (item.getItem_valid().length>1)
                    throw new ParamException("题目类型不匹配！");
                if (choices.size() != 4)
                    throw new ParamException("题目类型不匹配！");
                exist.setItem_valid(item.getItem_valid()[0]);
            }

        }
        Integer result = paperlessItemMapper.changeItem(exist);
        if (result != 1)
            throw new BusinessException("修改失败了！");
        return item;
    }

    /**
     * 添加单个题目
     *@param itemVO ItemVO
     *Date: 2019/4/3
     */
    @Override
    public ItemVO addItem(ItemVO itemVO) throws Exception {
        Item item = new Item();
        //判断是否传入title和备注
        if (itemVO.getItem_title() != null &&"".equals(itemVO.getItem_title()))
            item.setItem_title(itemVO.getItem_title());
        if (itemVO.getTip() != null && "".equals(itemVO.getTip()))
            item.setTip(itemVO.getTip());
        //为判断题
        item.setItem_desc(itemVO.getItem_desc());
        item.setItem_type(itemVO.getItem_type());
        item.setCourse(itemVO.getCourse());
        if (itemVO.getItem_type().equals("C")) {
            if (itemVO.getItem_valid().length > 1)
                throw new ParamException("题型不匹配！");
            item.setItem_valid(itemVO.getItem_valid()[0]);
        }else {
            List<String> choices = new ArrayList<>();
            try {
                choices = itemVO.getItem_choice();
            } catch (NullPointerException npe) {
                throw new ParamException("选择题题型为空！");
            }
            String choice = "";
            for (int i = 0; i < choices.size(); i ++) {
                choice += choices.get(i);
                if (i < choices.size() - 1)
                    choice += "|@|";
            }
            item.setItem_choice(choice);
            //如果为多选题
            if (itemVO.getItem_type().equals("B")) {
                String valid = "";
                String[] v = itemVO.getItem_valid();
                for (int i = 0;i < v.length;i ++) {
                    //判断是否为选项
                    valid += v[i];
                    if (i < v.length - 1)
                        valid += ",";
                }
                item.setItem_valid(valid);
            }else {
                item.setItem_valid(itemVO.getItem_valid()[0]);
            }
        }
        Integer result = paperlessItemMapper.addItem(item);
        if (result != 1)
            throw new BusinessException("添加失败！");
        return itemVO;
    }

    @Override
    public Integer delItem(String item_id) throws Exception {
        int result = 0;
        try {
            result = paperlessItemMapper.delItemById(Integer.parseInt(item_id));
        } catch (NumberFormatException nfe) {
            throw new ParamException("id不合法！");
        }
        if (result != 1)
            throw new BusinessException("删除失败！");
        return result;
    }
}
