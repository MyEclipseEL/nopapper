package com.ladybird.hkd.model.vo;

import com.ladybird.hkd.exception.ParamException;
import com.ladybird.hkd.model.example.ItemExample;
import com.ladybird.hkd.model.pojo.Course;
import com.ladybird.hkd.model.pojo.Item;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Shen
 * @description: itemView 方便前端的数据返回类
 * @create: 2019-03-18
 */
@ApiModel
public class ItemVO implements Serializable{
    @ApiModelProperty("题目编号")
    private String item_id;         //题目编号
    @ApiModelProperty("题目标题")
    private String item_title;      //题目标题
    @ApiModelProperty("题目描述")
    private String item_desc;       //题目描述
    @ApiModelProperty("题目正确答案")
    private String[] item_valid;      //题目正确答案
    @ApiModelProperty("迷惑答案")
    private List<String> item_choice;      //选项
    @ApiModelProperty("题型")
    private String item_type;       //题型
    @ApiModelProperty("科目")
    private Course course;          //科目
    private String tip;

    public static void validItem(ItemVO item) throws Exception{
        if (item.getItem_type() != null && !"".equals(item.getItem_type().trim())) {
            if (item.getItem_type().trim().equalsIgnoreCase("A")){
                if (item.getItem_choice().size() != 4)
                    throw new ParamException("单选题选项出错！");
                if (item.getItem_valid().length != 1)
                    throw new ParamException("单选题答案错误！");
                for (String s : item.getItem_valid())
                    if (!s.equalsIgnoreCase("A")&&!s.equalsIgnoreCase("B")
                            &&!s.equalsIgnoreCase("C")&&!s.equalsIgnoreCase("D"))
                        throw new ParamException("选项只能是‘A'‘B’‘C’‘D’");
            }else if (item.getItem_type().trim().equalsIgnoreCase("B")) {
                if (item.getItem_choice().size() !=5 )
                    throw new ParamException("多选题选项出错！");
                if (item.getItem_valid().length == 0 || item.getItem_valid().length > 5)
                    throw new ParamException("多选题答案出错！多选题答案最多为5");
                for (String s : item.getItem_valid())
                    if (!s.equalsIgnoreCase("A")&&!s.equalsIgnoreCase("B")
                            &&!s.equalsIgnoreCase("C")&&!s.equalsIgnoreCase("D")
                            &&!s.equalsIgnoreCase("E"))
                        throw new ParamException("选项只能是‘A'‘B’‘C’‘D’‘E’");
            } else if (item.getItem_type().trim().equalsIgnoreCase("C")) {
                if (item.getItem_valid().length != 1)
                    throw new ParamException("判断题答案出错！");
                if (!item.getItem_valid()[0].equals("正确") || !item.getItem_valid()[0].equals("错误"))
                    throw new ParamException("判断题答案只允许为‘正确’‘错误’");
            }else
                throw new ParamException("题型出错！");
            if (item.getItem_desc() == null || "".equals(item.getItem_desc().trim()))
                throw new ParamException("题目缺失！");
        }
    }

    //将Item转为ItemVO
    public static ItemVO Item2VOConveter(ItemExample item) throws Exception{
        ItemVO result = new ItemVO();
        if (item.getItem_id() != null)
            result.setItem_id(item.getItem_id());
        if (item.getItem_title() != null &&! "".equals(item.getItem_title().trim()))
            result.setItem_title(item.getItem_title());
        if (item.getTip() != null && !"".equals(item.getTip().trim()))
            result.setTip(item.getTip());
//        if (item.getItem_desc() == null || "".equals(item.getItem_desc().trim()))
//            throw new ParamException("题目为空！");
        result.setItem_desc(item.getItem_desc());
        if (item.getCourse() == null)
            throw new ParamException("课程为空！");
        result.setCourse(item.getCourse());
        if (item.getItem_type() == null )
            throw new ParamException("题型为空！");
        if (item.getItem_type().getType_id().equalsIgnoreCase("C")) {
            result.setItem_type(item.getItem_type().getType_id());
            if (!item.getItem_valid().trim().equals("正确") && !item.getItem_valid().trim().equals("错误"))
                throw new ParamException("判断题答案只允许为‘正确’‘错误’");
            result.setItem_valid(new String[]{item.getItem_valid()});
        } else {
            result.setItem_type(item.getItem_type().getType_id());
            if (item.getItem_choice() == null || "".equals(item.getItem_choice()))
                throw new ParamException("选项为空！");
            List<String> choices = new ArrayList<>();
            String[] c = item.getItem_choice().split("\\|\\@\\|");
            for (String s : c)
                choices.add(s);
            result.setItem_choice(choices);
//            if (item.getItem_type().equals("B")) {
            String[] v = item.getItem_valid().split(",");
            for (int i = 0;i < v.length; i ++)
                v[i] = v[i].trim();
            result.setItem_valid(v);
//            }else
//                result.setItem_valid(new String[]{item.getItem_valid()});
        }
        return result;
    }

    //将Item转为ItemVO
    public static List<ItemVO> Item2VOConveter(List<ItemExample> items) throws Exception{
        List<ItemVO> results = new ArrayList<>();
        for (ItemExample item : items) {
            ItemVO result = new ItemVO();
            if (item.getItem_id() != null)
                result.setItem_id(item.getItem_id());
            if (item.getItem_title() != null && !"".equals(item.getItem_title().trim()))
                result.setItem_title(item.getItem_title());
            if (item.getTip() != null && !"".equals(item.getTip().trim()))
                result.setTip(item.getTip());
//        if (item.getItem_desc() == null || "".equals(item.getItem_desc().trim()))
//            throw new ParamException("题目为空！");
            result.setItem_desc(item.getItem_desc());
            if (item.getCourse() == null)
                throw new ParamException("课程为空！");
            result.setCourse(item.getCourse());
            if (item.getItem_type() == null)
                throw new ParamException("题型为空！");
            if (item.getItem_type().getType_id().equalsIgnoreCase("C")) {
                result.setItem_type(item.getItem_type().getType_id());
                if (!item.getItem_valid().trim().equals("正确") && !item.getItem_valid().trim().equals("错误"))
                    throw new ParamException("判断题答案只允许为‘正确’‘错误’");
                result.setItem_valid(new String[]{item.getItem_valid()});
            } else {
                result.setItem_type(item.getItem_type().getType_id());
                if (item.getItem_choice() == null || "".equals(item.getItem_choice()))
                    throw new ParamException("选项为空！");
                List<String> choices = new ArrayList<>();
                String[] c = item.getItem_choice().split("\\|\\@\\|");
                for (String s : c)
                    choices.add(s);
                result.setItem_choice(choices);
                if (item.getItem_type().equals("B")) {
                    String[] v = item.getItem_valid().split(",");
                    for (int i = 0; i < v.length; i++)
                        v[i] = v[i].trim();
                    result.setItem_valid(v);
                } else
                    result.setItem_valid(new String[]{item.getItem_valid()});
            }
            results.add(result);
        }
        return results;
    }

    public static List<Item> itemVOList2ItemList(List<ItemVO> list) {
        List<Item> items = new ArrayList<>();
        for (ItemVO itemVO : list) {
            Item item = new Item();
            BeanUtils.copyProperties(itemVO, item);
            item.setCourse(itemVO.getCourse().getC_id());
            if (!itemVO.getItem_type().equalsIgnoreCase("C")) {
                List<String> choices = itemVO.getItem_choice();
                String choice = "";
                for (int i = 0; i < choices.size(); i++) {
                    choice += choices.get(i);
                    if (i < choices.size() - 1)
                        choice += "|@|";
                }
                item.setItem_choice(choice);
                String[] valid = itemVO.getItem_valid();
                String v = "";
                for (int i = 0;i < valid.length; i ++) {
                    v += valid[i];
                    if (i < valid.length - 1)
                        v += ",";
                }
                item.setItem_valid(v);
            }else {
                item.setItem_valid(itemVO.getItem_valid()[0]);
            }
            items.add(item);
        }
        return items;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public String getItem_desc() {
        return item_desc;
    }

    public void setItem_desc(String item_desc) {
        this.item_desc = item_desc;
    }

    public String[] getItem_valid() {
        return item_valid;
    }

    public void setItem_valid(String[] item_valid) {
        this.item_valid = item_valid;
    }

    public List<String> getItem_choice() {
        return item_choice;
    }

    public void setItem_choice(List<String> item_choice) {
        this.item_choice = item_choice;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
