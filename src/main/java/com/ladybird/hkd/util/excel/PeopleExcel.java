package com.ladybird.hkd.util.excel;

import com.ladybird.hkd.exception.ExcelImportException;

import com.ladybird.hkd.mapper.DeptMapper;
import com.ladybird.hkd.mapper.FacultyMapper;
import com.ladybird.hkd.mapper.GradeMapper;
import com.ladybird.hkd.model.example.GradeExample;
import com.ladybird.hkd.model.example.StudentExample;
import com.ladybird.hkd.model.pojo.*;

import com.ladybird.hkd.service.impl.MessageServiceImpl;
import com.ladybird.hkd.service.impl.StudentManageServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PeopleExcel  extends ReadItemExcel{

    public List<StudentExample> getExcelInfo(MultipartFile file) throws Exception {
        String filename = file.getOriginalFilename();   //获取文件名
        List<StudentExample> result = new ArrayList<>();
        if (validateExcel(filename))
            throw new ExcelImportException("导入授课：文件格式错误！");
        boolean isExcel2003 = true;
        if (isExcel2007(filename))
            isExcel2003 = false;
        result = createExcel(file.getInputStream(), isExcel2003);

        return result;
    }
    private List<StudentExample> createExcel(InputStream is,boolean isExcel2003) throws Exception {
        List<StudentExample> result = new ArrayList<>();
        try {
            Workbook workbook = null;
            if (isExcel2003) {
                workbook = new HSSFWorkbook(is);
            } else {
                workbook = new XSSFWorkbook(is);
            }
            result = readStudents(workbook);
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        return result;
    }

    private List<StudentExample> readStudents(Workbook workbook) throws Exception {
        Sheet sheet = workbook.getSheetAt(0);
        this.totalRows = sheet.getPhysicalNumberOfRows();
        if (totalRows > 1 && sheet.getRow(1) != null)
            this.totalCells = sheet.getRow(1).getPhysicalNumberOfCells();
        List<StudentExample> results = new ArrayList<>();
        for (int r = 1;r < totalRows;r ++) {
            Row row = sheet.getRow(r);
            if (row == null)
                continue;
            StudentExample studentExample = new StudentExample();

            String deptExample = null;
            String year = null;
            String clazz= null;
            for (int c = 0;c < this.totalCells;c ++) {
                Cell cell = row.getCell(c);
                cell.setCellType(CellType.STRING);
                if (cell == null)
                    continue;
                switch (c){
                    case 0:
                        if (cell.getStringCellValue() == null || "".equals(cell.getStringCellValue().trim()))
                            throw new ExcelImportException("导入学生: 学号为空!");
                        studentExample.setStu_num(cell.getStringCellValue());
                        break;
                    case 1:
                        if (cell.getStringCellValue() == null || "".equals(cell.getStringCellValue().trim()))
                            throw new ExcelImportException("导入学生: 姓名为空!");
                        studentExample.setStu_name(cell.getStringCellValue());
                        break;
                    case 2:
                        if (cell.getStringCellValue() == null || "".equals(cell.getStringCellValue().trim()))
                            throw new ExcelImportException("导入学生: 身份证为空!");
                        studentExample.setStu_ID(cell.getStringCellValue());
                        break;
                    case 3:
                        if (cell.getStringCellValue() == null || "".equals(cell.getStringCellValue().trim()))
                            throw new ExcelImportException("导入学生: 学院为空!");
                        Faculty faculty = new Faculty();
                        faculty.setFac_name(cell.getStringCellValue());
                        studentExample.setFaculty(faculty);
                        break;
                    case 4:
                        if (cell.getStringCellValue() == null || "".equals(cell.getStringCellValue().trim()))
                            throw new ExcelImportException("导入学生: 专业为空!");
                        Department department = new Department();
                        department.setDept_name(cell.getStringCellValue());
                        studentExample.setDepartment(department);
                        break;
                    case 5:
                        if (cell.getStringCellValue() == null || "".equals(cell.getStringCellValue().trim()))
                            throw new ExcelImportException("导入学生: 年级为空!");
                        year = cell.getStringCellValue();
                        break;
                    case 6:
                        if (cell.getStringCellValue() == null || "".equals(cell.getStringCellValue().trim()))
                            throw new ExcelImportException("导入学生: 班级为空!");
                        clazz = cell.getStringCellValue();
                        break;
                }
            }
            GradeExample gradeExample = new GradeExample();
            gradeExample.setG_year(Integer.parseInt(year));
            gradeExample.setG_class(Integer.parseInt(clazz));
            studentExample.setGrade(gradeExample);
            results.add(studentExample);
        }
        return results;
    }
}
