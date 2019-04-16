package com.ladybird.hkd.util.excel;

import com.ladybird.hkd.exception.ExcelImportException;
import com.ladybird.hkd.model.example.DepartmentExample;
import com.ladybird.hkd.model.example.GradeExample;
import com.ladybird.hkd.model.example.TeachExample;
import com.ladybird.hkd.model.pojo.Course;
import com.ladybird.hkd.model.pojo.Grade;
import com.ladybird.hkd.model.pojo.Teach;
import com.ladybird.hkd.model.pojo.Teacher;
import org.apache.commons.io.FileUtils;
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
 * @description:
 * @create: 2019-04-21
 */
public class ReadTeachesExcel extends ReadItemExcel{

    public List<TeachExample> getExcelInfo(MultipartFile file) throws Exception {
        String filename = file.getOriginalFilename();   //获取文件名
        List<TeachExample> result = new ArrayList<>();
        if (validateExcel(filename))
            throw new ExcelImportException("导入授课：文件格式错误！");
        boolean isExcel2003 = true;
        if (isExcel2007(filename))
            isExcel2003 = false;
        result = createExcel(file.getInputStream(), isExcel2003);

        return result;
    }

    private List<TeachExample> createExcel(InputStream is,boolean isExcel2003) throws Exception {
        List<TeachExample> result = new ArrayList<>();
        try {
            Workbook workbook = null;
            if (isExcel2003) {
                workbook = new HSSFWorkbook(is);
            } else {
                workbook = new XSSFWorkbook(is);
            }
            result = readTeaches(workbook);
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        return result;
    }

    private List<TeachExample> readTeaches(Workbook workbook) throws Exception{
        Sheet sheet = workbook.getSheetAt(0);
        this.totalRows = sheet.getPhysicalNumberOfRows();
        if (totalRows > 1 && sheet.getRow(1) != null)
            this.totalCells = sheet.getRow(1).getPhysicalNumberOfCells();
        List<TeachExample> results = new ArrayList<>();
        for (int r = 1;r < totalRows;r ++) {
            Row row = sheet.getRow(r);
            if (row == null)
                continue;
            TeachExample teach = new TeachExample();
            String year = null;
            String clazz = null;
            List<GradeExample> grades = new ArrayList<>();
            for (int c = 0;c < this.totalCells;c ++) {
                Cell cell = row.getCell(c);
                cell.setCellType(CellType.STRING);
                if (cell == null)
                    continue;
                switch (c) {
                    case 0 :
                        if (cell.getStringCellValue() == null || "".equals(cell.getStringCellValue().trim()))
                            throw new ExcelImportException("导入授课：课程为空！");
                        Course course = new Course();
                        course.setC_name(cell.getStringCellValue().trim());
                        teach.setCourse(course);
                        break;
                    case 2 :
                        if (cell.getStringCellValue() == null || "".equals(cell.getStringCellValue().trim()))
                            throw new ExcelImportException("导入授课：教师工号为空！");
                        Teacher teacher = new Teacher();
                        teacher.setT_num(cell.getStringCellValue().trim());
                        teach.setTeacher(teacher);
                        break;
                    case 3 :
                        if (cell.getStringCellValue() == null || "".equals(cell.getStringCellValue().trim()))
                            throw new ExcelImportException("导入授课：专业为空！");
                        DepartmentExample dept = new DepartmentExample();
                        dept.setDept_name(cell.getStringCellValue().trim());
                        teach.setDept(dept);
                        break;
                    case 4 :
                        if (cell.getStringCellValue() == null ||"".equals(cell.getStringCellValue().trim()))
                            throw new ExcelImportException("导入授课：年级为空！");
                        year = cell.getStringCellValue().trim();
                        break;
                    case 5 :
                        if (cell.getStringCellValue() == null || "".equals(cell.getStringCellValue().trim()))
                            throw new ExcelImportException("导入授课：班级列为空！");
                        clazz = cell.getStringCellValue().trim();
                        break;
                }
            }
            //数据装填
            for (String s : clazz.split("\\ ")) {
                grades.add(new GradeExample(null, Integer.parseInt(year), Integer.parseInt(s), null));
            }
            teach.setGrades(grades);
            results.add(teach);
        }
        return results;
    }
}
