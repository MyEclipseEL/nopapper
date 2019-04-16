package com.ladybird.hkd.util.excel;

import com.ladybird.hkd.exception.ExcelImportException;
import com.ladybird.hkd.model.example.TeacherExample;
import com.ladybird.hkd.model.json.TeacherJsonOut;
import com.ladybird.hkd.model.pojo.Faculty;
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
 * @description: 教师信息导入
 * @create: 2019-04-21
 */
public class ReadTeacherExcel extends ReadItemExcel{
    public List<TeacherExample> getExcelInfo(MultipartFile file) throws Exception {
        String filename = file.getOriginalFilename();
        List<TeacherExample> result = new ArrayList<>();
        if (validateExcel(filename))
            throw new ExcelImportException("<导入教师信息>：文件格式错误！");
        boolean isExcel2003 = true;
        if (isExcel2007(filename))
            isExcel2003 = false;
        result = createExcel(file.getInputStream(), isExcel2003);
        return result;
    }

    public List<TeacherExample> createExcel(InputStream is,boolean isExcel2003) throws Exception {
        List<TeacherExample> result = new ArrayList<>();
        try {
            Workbook workbook = null;
            if (isExcel2003)
                workbook = new HSSFWorkbook(is);
            else
                workbook = new XSSFWorkbook(is);
            result = readTeachers(workbook);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return result;
    }

    public List<TeacherExample> readTeachers(Workbook workbook) throws Exception {
        Sheet sheet = workbook.getSheetAt(0);
        this.totalRows = sheet.getPhysicalNumberOfRows();
        if (totalRows > 1 && sheet.getRow(1) != null)
            this.totalCells = sheet.getRow(1).getPhysicalNumberOfCells();
        List<TeacherExample> results = new ArrayList<>();
        for (int r = 1;r < totalRows;r ++) {
            Row row = sheet.getRow(r);
            if (row == null)
                continue;
            TeacherExample teacherExample = new TeacherExample();
            for (int c = 0;c < this.totalCells;c ++) {
                Cell cell = row.getCell(c);
                cell.setCellType(CellType.STRING);
                if (cell == null)
                    continue;
                switch (c) {
                    case 0 :
                        if (cell.getStringCellValue() == null || "".equals(cell.getStringCellValue().trim()))
                            throw new ExcelImportException("<导入教师信息>：工号为空！");
                        teacherExample.setT_num(cell.getStringCellValue().trim());
                        break;
                    case 1 :
                        if (cell.getStringCellValue() == null || "".equals(cell.getStringCellValue().trim()))
                            throw new ExcelImportException("<导入教师信息>：教师姓名为空！");
                        teacherExample.setT_name(cell.getStringCellValue().trim());
                        break;
                    case 2 :
                        if (cell.getStringCellValue() == null || "".equals(cell.getStringCellValue().trim()))
                            throw new ExcelImportException("<导入教师信息>：学院为空！");
                        teacherExample.setT_faculty(new Faculty(null,cell.getStringCellValue().trim(),null));
                        break;
                }
            }
            results.add(teacherExample);
        }
        return results;
    }
}
