package com.ladybird.hkd.util.excel;

import com.ladybird.hkd.model.pojo.Exam;
import com.ladybird.hkd.model.pojo.Item;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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




    public List<Item> getExcelInfo(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();    //获取文件名
        List<Item> items = new ArrayList<>();
        try {
            if (validateExcel(fileName))
                return null;
            //根据文件名判断Excel版本
            boolean isExcel2003 = true;
            if (isExcel2007(fileName))
                isExcel2003 = false;
            items = createExcel(multipartFile.getInputStream(), isExcel2003);
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
    public List<Item> createExcel(InputStream is, boolean isExcel2003) {
        List<Item> items = new ArrayList<>();
        try {
            Workbook workbook = null;
            if (isExcel2003) {
                workbook = new HSSFWorkbook(is);
            } else {
                workbook = new XSSFWorkbook(is);
            }
            items = readExcelValue(workbook);
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        return items;
    }


    private List<Item> readExcelValue(Workbook workbook) {
        //得到第一个shell
        Sheet sheet = workbook.getSheetAt(0);
        //得到Excel的行数
        this.totalRows = sheet.getPhysicalNumberOfRows();
        //得到Excel的列数
        if (totalRows>1 && sheet.getRow(1)!=null) {
            this.totalCells = sheet.getRow(1).getPhysicalNumberOfCells();
        }
        List<Item> items = new ArrayList<>();
        //读取Excel，循环行列
        for (int r = 0 ; r < totalRows;r ++) {
            Row row = sheet.getRow(r);
            if (row == null)
                continue;
            Item item = new Item();
            //循环Excel的列
            for (int c = 1;c < this.totalCells;c++) {
                Cell cell = row.getCell(c);
                if (cell == null)
                    continue;
                switch (c) {
                    case 0:item.setItem_id(cell.getStringCellValue());
                        break;
                    case 1:item.setItem_title(cell.getStringCellValue());
                        break;
                    case 2:item.setItem_desc(cell.getStringCellValue());
                        break;
                    case 3:item.setItem_valid(cell.getStringCellValue());
                        break;
                    case 4:item.setItem_choice(cell.getStringCellValue());
                        break;
//                    case 5:item.setItem_type(cell.getStringCellValue());
//                        break;
                    case 5:item.setCourse(cell.getStringCellValue());
                        break;
                    case 6:item.setTip(cell.getStringCellValue());
                }

            }
            if (item.getItem_desc() != null)
                items.add(item);
        }
        return items;

    }

//    private String getValue(Cell cell) {
//
//    }

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
