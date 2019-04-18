package com.ladybird.hkd.util.excel;

import com.ladybird.hkd.exception.ExcelImportException;
import com.ladybird.hkd.model.example.DepartmentExample;
import com.ladybird.hkd.model.example.GradeExample;
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
 * @description: 班级信息导入
 * @create: 2019-04-10
 */
public class ReadGradeExcel extends ReadItemExcel {
    public List<GradeExample> getExcelInfo(MultipartFile file) throws Exception {
        String filename = file.getOriginalFilename();
        List<GradeExample> results = new ArrayList<>();
        if (validateExcel(filename))
            throw new ExcelImportException("<导入授课>：文件格式错误！");
        boolean isExcel2003 = true;
        if (isExcel2007(filename))
            isExcel2003 = false;
        results = createExcel(file.getInputStream(), isExcel2003);
        return results;
    }

    private List<GradeExample> createExcel(InputStream is,boolean isExcel2003) throws Exception {
        List<GradeExample> results = new ArrayList<>();
        try {
            Workbook workbook = null;
            if (isExcel2003)
                workbook = new HSSFWorkbook(is);
            else
                workbook = new XSSFWorkbook(is);
            results = readGrade(workbook);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return results;
    }

    private List<GradeExample> readGrade(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);
        this.totalRows = sheet.getPhysicalNumberOfRows();
        if (totalRows > 1 && sheet.getRow(1)!=null)
            this.totalCells = sheet.getRow(1).getPhysicalNumberOfCells();
        List<GradeExample> results = new ArrayList<>();
        for (int r = 1;r < totalRows;r ++) {
            Row row = sheet.getRow(r);
            int count = 0;
            String dept = "";
            Integer year = null;
        
            if (row == null)
                continue;
            // GradeExample grade = new GradeExample();
            for (int c = 0;c < totalCells;c ++) {
                Cell cell = row.getCell(c);
                cell.setCellType(CellType.STRING);
                switch (c) {
                    case 0:
                        if (cell.getStringCellValue() == null || "".equals(cell.getStringCellValue().trim()))
                            throw new ExcelImportException("<导入班级信息>：专业信息出错！");
                        dept = cell.getStringCellValue().trim();
                        // grade.setDept(new DepartmentExample(null,cell.getStringCellValue().trim(),null,null));
                        break;
                    case 1:
                        if (cell.getStringCellValue() == null || "".equals(cell.getStringCellValue().trim()))
                            throw new ExcelImportException("<导入班级信息>：专业信息出错！");
                        try {
                            year = Integer.parseInt(cell.getStringCellValue().trim());
                            // grade.setG_year(year);
                        } catch (NumberFormatException e) {
                            throw new ExcelImportException("<导入班级信息>：年级信息出错！");
                        }
                        break;
                    case 2:
                        if (cell.getStringCellValue() == null || "".equals(cell.getStringCellValue().trim()))
                            throw new ExcelImportException("<导入班级信息>：年级数量出错！");
                        try {
                            count = Integer.parseInt(cell.getStringCellValue().trim());
                        } catch (NumberFormatException e) {
                            throw new ExcelImportException("<导入班级信息>：年级数量出错！");
                        }
                }
            }
            //循环添加班级
            for (int x = 1;x <= count; x ++) {
                GradeExample gradeExample = new GradeExample();
                gradeExample.setDept(new DepartmentExample(null,dept,null,null));
                gradeExample.setG_year(year);
                gradeExample.setG_class(x);
               results.add(gradeExample);
            }
        }
        return results;
    }

}
