package com.ladybird.hkd.service.impl;

import com.ladybird.hkd.exception.BusinessException;
import com.ladybird.hkd.exception.ParamException;
import com.ladybird.hkd.mapper.CourseMapper;
import com.ladybird.hkd.mapper.ExamMapper;
import com.ladybird.hkd.mapper.PaperlessItemMapper;
import com.ladybird.hkd.model.example.ItemExample;
import com.ladybird.hkd.model.example.PaperEditExample;
import com.ladybird.hkd.model.json.ItemListOut;
import com.ladybird.hkd.model.json.ItemsOut;
import com.ladybird.hkd.model.json.PageBean;
import com.ladybird.hkd.model.pojo.Course;
import com.ladybird.hkd.model.pojo.Item;
import com.ladybird.hkd.model.pojo.ItemType;
import com.ladybird.hkd.model.pojo.PaperEdit;
import com.ladybird.hkd.model.vo.ItemVO;
import com.ladybird.hkd.service.ItemService;
import com.ladybird.hkd.util.ConstConfig;
import com.ladybird.hkd.util.JsonUtil;
import com.ladybird.hkd.util.TencentCOSFileUtils;
import com.ladybird.hkd.util.UrlConf;
import com.ladybird.hkd.util.excel.ReadItemExcel;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.spring.web.json.Json;

import java.io.*;
import java.text.SimpleDateFormat;
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
    public PageBean checkOutItems(String course,String item_type,Integer curPage,Integer pageCount) throws Exception {
//        if (items.size() <= 0)
//            throw new ParamException("课程号没有对应的课程！");
        if (course == null || "".equals(course.trim()))
            course = null;
        if (item_type == null || "".equals(item_type.trim()))
            item_type = null;
        PageBean<List<ItemVO>> pageBean = new PageBean<>();
        pageBean.setCurPage(curPage);
        Integer totalCount = paperlessItemMapper.itemCount(course, item_type);
        pageBean.setTotalCount(totalCount);
        pageBean.setPageCount(pageCount);
        pageBean.setTotalPages((int) Math.ceil(totalCount/pageCount));
        List<ItemExample> items = paperlessItemMapper.checkOutItemsPage(course,item_type,(curPage-1)*pageCount,pageCount);
        List<ItemVO> vos = new ArrayList<>();
        for (ItemExample item : items) {
            vos.add(ItemVO.Item2VOConveter(item));
        }
        pageBean.setData(vos);
        return pageBean;
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
    public List<ItemVO> addItems(MultipartFile file,String course,String item_type) throws Exception {
        //创建处理Excel的类
        ReadItemExcel readItemExcel = new ReadItemExcel();
        //解析excel
        List<ItemVO> itemVOS = null;
        int insertResult = 0;   //记录插入数
        itemVOS = readItemExcel.getExcelInfo(file,course,item_type);
        //TODO 放开注释
        List<Item> items = ItemVO.itemVOList2ItemList(itemVOS);
        if (!item_type.equalsIgnoreCase("C"))
            //添加选择题
            insertResult = paperlessItemMapper.checkInItems(items);
        else
            //添加判断题
            insertResult = paperlessItemMapper.checkInChecking(items);
        if (insertResult < items.size()) {

            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new BusinessException("插入出错！");
        }
        //得到对象存储地址
//        if (!synchronizedItems(course))
//            throw new BusinessException("出错了！");
        return itemVOS;
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
    public ItemVO changeItem( ItemVO item) throws Exception {
//        ItemVO.validItem(item);
        if (item.getItem_id() == null)
            throw new ParamException("参数错误！");
        //查找数据库的题目
        Item exist = paperlessItemMapper.selItemById(Integer.parseInt(item.getItem_id()));
        if (exist == null)
            throw new BusinessException("不存在这道题！");
        //如果传参有title则改变
        if (item.getItem_title() != null && !"".equals(item.getItem_title().trim()))
            exist.setItem_title(item.getItem_title());
        if (item.getTip() != null && !"".equals(item.getTip().trim()))
            exist.setTip(item.getTip());
        exist.setItem_desc(item.getItem_desc());
        //判断题单独修改
        if (exist.getItem_type().trim().equalsIgnoreCase("C")) {
            if (item.getItem_valid().length != 1)
                throw new ParamException("题目类型不匹配！");
//            if (item.getItem_choice() != null || item.getItem_choice().size() != 0)
//                throw new ParamException("题目类型不匹配！");
            exist.setItem_valid(item.getItem_valid()[0]);
        }else {
            //传参没有选项，不修改，有则修改
            if (item.getItem_choice()!= null && item.getItem_choice().size() != 0){
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
            }
            if (item.getItem_valid() != null &&item.getItem_valid().length > 0) {
                if (exist.getItem_type().trim().equalsIgnoreCase("B")) {
                    String[] valid = item.getItem_valid();
                    if (valid.length >5 || valid.length < 1)
                        throw new ParamException("修改选项出错!");
                    String v = "";
                    for (int i = 0; i < valid.length; i++) {
                        v += valid[i];
                        if (i < valid.length - 1)
                            v += ",";
                    }
                    exist.setItem_valid(v);
                } else {
                    if (item.getItem_valid().length > 1)
                        throw new ParamException("题目类型不匹配！");
                    exist.setItem_valid(item.getItem_valid()[0]);
                }
            }

        }
        Integer result = paperlessItemMapper.changeItem(exist);
        if (result != 1)
            throw new BusinessException("修改失败了！");
        //同步服务器题库
//        synchronizedItems(item.getCourse().getC_id());
//        randomPaper(exist.getCourse());
        //TODO 开启上传线程
//        UploadThread uploadThread = new UploadThread(exist.getCourse());
//        uploadThread.start();
//        synchronizedItems(exist.getCourse());
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
        item.setCourse(itemVO.getCourse().getC_id());
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
        //同步服务器题库
//        synchronizedItems(itemVO.getCourse().getC_id());
        randomPaper("","");
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
        String course = paperlessItemMapper.selItemById(Integer.parseInt(item_id)).getCourse();
        //同步服务器题库
//        synchronizedItems(course);

        return result;
    }

    private class UploadThread extends Thread{
        private String course;
        public UploadThread(String course) {
            this.course = course;
        }

        @Override
        public void run() {
            try {
                int result = synchronizedItems(course);
                if (result < 300)
                    throw new BusinessException("Upload error! Upload again!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //将题目组装并同步到云存储当中
    public Integer synchronizedItems(String course) throws Exception {
        paperlessItemMapper.delCOSByCourse(course);
        int types = paperlessItemMapper.selTotalType(course);
        if (types < 3)
            return -1;
        int index = 1;
        boolean flag = false;
        List<List<ItemListOut>> lists = new ArrayList<>();
        while (index < 5) {
            lists.add(randomPaper(course));
            index++;
        }

        for (List<ItemListOut> result : lists){
            String url = "";
            try {
//            outputStream = new FileOutputStream(UrlConf.LOCAL_UPLOAD_PATH + "/items.txt");
                //TODO 放行服务器地址
//            outputStream = new FileOutputStream(UrlConf.SERVER_UPLOAD_PATH + "/items.txt");
                String in = JsonUtil.objectToJson(result);
                File file = new File(UrlConf.LOCAL_UPLOAD_PATH + "/item" + "_" + course + "_" + index + ".txt");
                //将json数据写入文件
                FileUtils.writeStringToFile(file, in, "UTF-8");
                //将文件上传到对象存储
                //  course/****.txt
                url = TencentCOSFileUtils.uploadFile(course + "/" + file.getName(), file);
                //将url数据存到数据库

                //存到数据库

                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
                // 随即散列 待做

                int r = 0;
                r = paperlessItemMapper.checkInCOS(format.format(new Date()) + course, course, url);
                if (r != 1)
                    throw new ParamException("url插入失败！");
                flag = true;
                //删除临时文件
                FileUtils.deleteQuietly(file);
            } catch (IOException ioe) {
                ioe.printStackTrace();
                flag = false;
            }
            index++;
        }
        return index;
    }

    @Override
    public List<String> getPaperUrl(String c_id) throws Exception {
        return paperlessItemMapper.checkOutUrl(c_id);
    }

    private List<ItemListOut> randomPaper(String course) throws Exception {
        List<ItemListOut> outs = new ArrayList<>();
        List<ItemType> itemTypes = paperlessItemMapper.checkOutItemTypes();
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
        String[] chapters = examMapper.checkOutChapter(course).get(0).getNumbers().split("\\|");
        Set<ItemExample> set = new HashSet<>();
        List<ItemVO> singles = new ArrayList<>();
        List<ItemVO> multiples = new ArrayList<>();
        List<ItemVO> checkings = new ArrayList<>();
        int index = 0;
        for (String s : chapters) {
            String curChap = "第" + ConstConfig.number2China[index] + "章";
            String[] ss = s.split(",");
            //装填选择题
            singles.addAll(ItemVO.Item2VOConveter(paperlessItemMapper.checkOutRandItems(course, single.getType_id(), curChap, Integer.parseInt(ss[0]))));
            multiples.addAll(ItemVO.Item2VOConveter(paperlessItemMapper.checkOutRandItems(course, multiple.getType_id(), curChap, Integer.parseInt(ss[1]))));
            checkings.addAll(ItemVO.Item2VOConveter(paperlessItemMapper.checkOutRandItems(course, checking.getType_id(), curChap, Integer.parseInt(ss[2]))));
            index++;
        }
        outs.add(new ItemListOut(single,singles));
        outs.add(new ItemListOut(multiple, multiples));
        outs.add(new ItemListOut(checking, checkings));

        System.out.println(outs);
        System.out.println(randomPaper("", ""));
        return outs;
    }


    private String randomPaper(String chapter,String course) throws Exception{
        //各章节的题目
        String[] chapters = chapter.split("\\|");
        Set<ItemExample> items = new HashSet<>();
        items.addAll(paperlessItemMapper.checkOutItems(course, null));
        List<ItemType> itemTypes = paperlessItemMapper.checkOutItemTypes();
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
        int i = 0;
        //循环300份卷子
        // 20 20 20 1,1,1|2,2,2|1,1,1|1,1,1|1,1,1|1,1,1|1,1,1|1,1,1|1,1,1|2,2,2|2,2,2|2,2,2|2,2,2|2,2,2

            //组装单份卷子
            List<ItemListOut> listOuts = new ArrayList<>();
            Set<ItemVO> singles = new HashSet<>();
            Set<ItemVO> multiples = new HashSet<>();
            Set<ItemVO> checkings = new HashSet<>();
            //循环章节添加题目
            for (int x = 0;x < chapters.length;x ++) {
                //切开一个章节的三个题型的配置
                String chinaCur = ConstConfig.number2China[i];
                String[] s = chapters[x].split(",");
                int sin = Integer.parseInt(s[0]);
                int mul = Integer.parseInt(s[1]);
                int che = Integer.parseInt(s[2]);
                //每添加一次，就将标记减少一，减少到0，就停止
                while (sin != 0 && mul != 0 && che != 0) {
                    for (ItemExample item : items) {
                        List<String> choice = new ArrayList<>();
                        ItemVO itemVO = new ItemVO();
                        BeanUtils.copyProperties(item,itemVO);
                        String[] valids = item.getItem_valid().split(",");
                        for (String v:valids)
                            v.trim();
                        itemVO.setItem_valid(valids);
                        if (!item.getItem_type().getType_id().equalsIgnoreCase("C")) {
                            for (String sa : item.getItem_choice().split("\\|\\@\\|"))
                                choice.add(sa);
                            itemVO.setItem_choice(choice);
                        }
                        if (item.getItem_type().getType_id().equals(single.getType_id())
                                &&item.getTip().equals("第" + chinaCur + "章")
                                &&sin != 0) {
                            itemVO.setItem_type(single.getType_name());
                            singles.add(itemVO);
                            sin--;
                        }
                        if (item.getItem_type().getType_id().equals(multiple.getType_id())
                                &&item.getTip().equals("第" + chinaCur + "章")
                                && mul != 0){
                            itemVO.setItem_type(multiple.getType_name());
                            multiples.add(itemVO);
                            mul--;
                        }
                        if (item.getItem_type().getType_id().equals(checking.getType_id())
                                &&item.getTip().equals("第" + chinaCur + "章")
                                && che != 0){
                            itemVO.setItem_type(checking.getType_name());
                            checkings.add(itemVO);
                            che--;
                        }

                    }
                }
            }
            List<ItemsOut> outs = new ArrayList<>();
            outs.add(new ItemsOut(single, singles));
            outs.add(new ItemsOut(multiple, multiples));
            outs.add(new ItemsOut(checking, checkings));
            i++;
        return "";
    }

}
