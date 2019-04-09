package com.ladybird.hkd.util.excel;

import com.ladybird.hkd.exception.ExcelImportException;
import com.ladybird.hkd.exception.ParamException;
import com.ladybird.hkd.model.pojo.Course;
import com.ladybird.hkd.model.pojo.Exam;
import com.ladybird.hkd.model.pojo.Item;
import com.ladybird.hkd.model.vo.ItemVO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Shen
 * @description: 读取题库excel
 * @create: 2019-03-12
 */
public class ReadItemExcel {
    //总行数
    private int totalRows = 0;
    //总列数
    private int totalCells = 0;
    //错误信息
    private String errorMsg;

    public ReadItemExcel() {

    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalCells() {
        return totalCells;
    }

    public String getErrorMsg() {
        return errorMsg;
    }




    public List<ItemVO> getExcelInfo(MultipartFile multipartFile,String course,String item_type) throws ExcelImportException{
        String fileName = multipartFile.getOriginalFilename();    //获取文件名
        List<ItemVO> items = new ArrayList<>();
        try {
            if (validateExcel(fileName))
                return null;
            //根据文件名判断Excel版本
            boolean isExcel2003 = true;
            if (isExcel2007(fileName))
                isExcel2003 = false;
            items = createExcel(multipartFile.getInputStream(), isExcel2003,course,item_type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    /**
     * 根据excel里面的内容读取信息
     * @param is    输入流
     * @param isExcel2003 excel是2003还是2007版本
     * @throws IOException
     */
    public List<ItemVO> createExcel(InputStream is, boolean isExcel2003,String course,String item_type) throws Exception{
        List<ItemVO> items = new ArrayList<>();
        try {
            Workbook workbook = null;
            if (isExcel2003) {
                workbook = new HSSFWorkbook(is);
            } else {
                workbook = new XSSFWorkbook(is);
            }
            if ("A".equalsIgnoreCase(item_type))
                items = readSingleChoice(workbook,course,item_type);
            else if("B".equalsIgnoreCase(item_type))
                items = readMultipleChoice(workbook, course, item_type);
            else
                items = readCheckings(workbook, course, item_type);
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        return items;
    }


    private List<ItemVO> readSingleChoice(Workbook workbook,String course,String item_type) throws Exception{
        //得到第一个shell
        Sheet sheet = workbook.getSheetAt(0);
        //得到Excel的行数
        this.totalRows = sheet.getPhysicalNumberOfRows();
        //得到Excel的列数
        if (totalRows>1 && sheet.getRow(1)!=null) {
            this.totalCells = sheet.getRow(1).getPhysicalNumberOfCells();
        }
        List<ItemVO> items = new ArrayList<>();
        //读取Excel，循环行列
        for (int r = 1 ; r < totalRows;r ++) {
            Row row = sheet.getRow(r);
            if (row == null)
                continue;
            ItemVO item = new ItemVO();
            Course course1 = new Course();
            course1.setC_id(course);
            item.setCourse(course1);
            item.setItem_type(item_type);
            //循环Excel的列
            List<String> choice = new ArrayList<>();
            for (int c = 1;c < this.totalCells;c++) {
                Cell cell = row.getCell(c);
                cell.setCellType(CellType.STRING);
                if (cell == null)
                    continue;
                switch (c) {
                    case 1:if (cell.getStringCellValue() == null || cell.getStringCellValue().trim().equals(""))
                        throw new ExcelImportException("题目为空！");
                        item.setItem_desc(cell.getStringCellValue());
                        break;
                    case 2:if (cell.getStringCellValue() == null || cell.getStringCellValue().trim().equals(""))
                        throw new ExcelImportException("选项A为空！");
                        choice.add(cell.getStringCellValue());
                        break;
                    case 3:if (cell.getStringCellValue() == null || cell.getStringCellValue().trim().equals(""))
                        throw new ExcelImportException("选项B为空！");
                        choice.add(cell.getStringCellValue());
                        break;
                    case 4:if (cell.getStringCellValue() == null || cell.getStringCellValue().trim().equals(""))
                        throw new ExcelImportException("选项C为空！");
                        choice.add(cell.getStringCellValue());
                        break;
                    case 5:if (cell.getStringCellValue() == null || cell.getStringCellValue().trim().equals(""))
                        throw new ExcelImportException("选项D为空！");
                        choice.add(cell.getStringCellValue());
                        break;
                    case 6:
                        char v = cell.getStringCellValue().charAt(0);
                        String value = v + "";

                        if (value == null || value.length() < 1)
                            throw new ExcelImportException("没有正确选项！");
//                        if (value.length()>1)
//                            throw new ExcelImportException("单选题有多个选项！");

                        if (!value.equalsIgnoreCase("A")&&!value.equalsIgnoreCase("B")
                                &&!value.equalsIgnoreCase("C")&&!value.equalsIgnoreCase("D"))
                            throw new ExcelImportException("单选题选项必须为A,B,C,D选项必须为");
                        item.setItem_valid(new String[]{value.toUpperCase()});
                        break;
                    case 7:if (cell.getStringCellValue() == null || cell.getStringCellValue().trim().equals(""))
                        item.setTip(null);
                        item.setTip(cell.getStringCellValue());
                        break;
                }
            }
            item.setItem_choice(choice);
            if (item.getItem_desc() != null)
                items.add(item);
        }
        return items;
    }


    private List<ItemVO> readMultipleChoice(Workbook workbook,String course,String item_type) throws Exception{
        //得到第一个shell
        Sheet sheet = workbook.getSheetAt(0);
        //得到Excel的行数
        this.totalRows = sheet.getPhysicalNumberOfRows();
        //得到Excel的列数
        if (totalRows>1 && sheet.getRow(1)!=null) {
            this.totalCells = sheet.getRow(1).getPhysicalNumberOfCells();
        }
        List<ItemVO> items = new ArrayList<>();
        //读取Excel，循环行列
        for (int r = 1 ; r < totalRows;r ++) {
            Row row = sheet.getRow(r);
            if (row == null)
                continue;
            ItemVO item = new ItemVO();
            Course course1 = new Course();
            course1.setC_id(course);
            item.setCourse(course1);
            item.setItem_type(item_type);
            //循环Excel的列
            List<String> choice = new ArrayList<>();
            for (int c = 1;c < this.totalCells;c++) {
                Cell cell = row.getCell(c);
                cell.setCellType(CellType.STRING);
                if (cell == null)
                    continue;
                switch (c) {
                    case 0:if (cell.getStringCellValue() == null || cell.getStringCellValue().trim().equals(""))
                        throw new ExcelImportException("题目为空！");
                        item.setItem_desc(cell.getStringCellValue());
                        break;
                    case 1:if (cell.getStringCellValue() == null || cell.getStringCellValue().trim().equals(""))
                        throw new ExcelImportException("选项A为空！");
                        choice.add(cell.getStringCellValue());
                        break;
                    case 2:if (cell.getStringCellValue() == null || cell.getStringCellValue().trim().equals(""))
                        throw new ExcelImportException("选项B为空！");
                        choice.add(cell.getStringCellValue());
                        break;
                    case 3:if (cell.getStringCellValue() == null || cell.getStringCellValue().trim().equals(""))
                        throw new ExcelImportException("选项C为空！");
                        choice.add(cell.getStringCellValue());
                        break;
                    case 4:if (cell.getStringCellValue() == null || cell.getStringCellValue().trim().equals(""))
                        throw new ExcelImportException("选项D为空！");
                        choice.add(cell.getStringCellValue());
                        break;
                    case 5:if (cell.getStringCellValue() == null || cell.getStringCellValue().trim().equals(""))
                        throw new ExcelImportException("选项E为空！");
                        choice.add(cell.getStringCellValue());
                    case 6:String value = cell.getStringCellValue();
                        if (value == null || value.length() < 1)
                            throw new ExcelImportException("没有正确选项！");
                        if (!value.equalsIgnoreCase("A")&&!value.equalsIgnoreCase("B")
                                &&!value.equalsIgnoreCase("C")&&!value.equalsIgnoreCase("D")
                                &&!value.equalsIgnoreCase("E"))
                            throw new ParamException("选项必须是A,B,C,D,E");
                        String valid = cell.getStringCellValue().replaceAll("\\ ","");
                        valid.replaceAll("，", ",");
                        item.setItem_valid(valid.toUpperCase().split(","));
                        break;
                    case 7:if (cell.getStringCellValue() == null || cell.getStringCellValue().trim().equals(""))
                        item.setTip(null);
                        item.setTip(cell.getStringCellValue());
                        break;
                }

            }

//            choice.replaceAll(" ", "");
            item.setItem_choice(choice);
            if (item.getItem_desc() != null)
                items.add(item);
        }
        return items;
    }

    private List<ItemVO> readCheckings(Workbook workbook,String course,String item_type) throws Exception{
        //得到第一个shell
        Sheet sheet = workbook.getSheetAt(0);
        //得到Excel的行数
        this.totalRows = sheet.getPhysicalNumberOfRows();
        //得到Excel的列数
        if (totalRows>1 && sheet.getRow(1)!=null) {
            this.totalCells = sheet.getRow(1).getPhysicalNumberOfCells();
        }
        List<ItemVO> items = new ArrayList<>();
        //读取Excel，循环行列
        for (int r = 1 ; r < totalRows;r ++) {
            Row row = sheet.getRow(r);
            if (row == null)
                continue;
            ItemVO item = new ItemVO();
            Course course1 = new Course();
            course1.setC_id(course);
            item.setCourse(course1);
            item.setItem_type(item_type);
            //循环Excel的列
            for (int c = 1;c < this.totalCells;c++) {
                Cell cell = row.getCell(c);
                cell.setCellType(CellType.STRING);
                if (cell == null)
                    continue;
                switch (c) {
                    case 1:if (cell.getStringCellValue() == null || cell.getStringCellValue().trim().equals(""))
                        throw new ExcelImportException("题目为空！");
                        item.setItem_desc(cell.getStringCellValue());
                        break;
                    case 2:if (cell.getStringCellValue() == null || cell.getStringCellValue().trim().equals(""))
                            throw new ExcelImportException("答案为空！");
                        if (!cell.getStringCellValue().trim().equals("正确") && !cell.getStringCellValue().trim().equals("错误")
                                && !cell.getStringCellValue().trim().equals("对")&& !cell.getStringCellValue().trim().equals("错")  )
                            throw new ParamException("判断题答案只允许为‘正确’‘错误’");
                        if (cell.getStringCellValue().equals("对"))
                            item.setItem_valid(new String[]{cell.getStringCellValue().replaceAll("对","正确")});
                        if (cell.getStringCellValue().equals("错"))
                            item.setItem_valid(new String[]{cell.getStringCellValue().replaceAll("错","错误")});
                        break;
                    case 3:if (cell.getStringCellValue() == null || cell.getStringCellValue().trim().equals(""))
                        item.setTip(null);
                        item.setTip(cell.getStringCellValue());
                        break;
                }
            }
            if (item.getItem_desc() != null)
                items.add(item);
        }
        return items;
    }



    public boolean validateExcel(String fileName) {
        //文件名为空,或者不是2003 或2007
        if (fileName == null || !isExcel2003(fileName) || !isExcel2007(fileName)) {
            errorMsg = "文件格式错误！";
            return false;
        }
        return true;
    }

    //是否是2003的excel，返回true是2003
    public static boolean isExcel2003(String fileName) {
        return fileName.matches("^.+\\.(?i)(xls)$");
    }

    //是否是2007的excel，返回true是2007
    public static boolean isExcel2007(String fileName) {
        return fileName.matches("^.+\\.(?i)(xlsx)$");
    }
}
