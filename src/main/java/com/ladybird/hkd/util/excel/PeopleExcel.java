package com.ladybird.hkd.util.excel;

import com.ladybird.hkd.model.pojo.Student;
import com.ladybird.hkd.model.pojo.Teacher;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PeopleExcel {
    public List<Student> batchInputStudent(String fileName, MultipartFile file) throws Exception {
        boolean notNull = false;
        List<Student> StudentList = new ArrayList<>();

        boolean isExcel2003 = true;

        InputStream is = file.getInputStream();
        Workbook wb = null;

        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }

        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if(sheet!=null){
            notNull = true;
        }

        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            Student student = new Student();
            if (row == null){
                continue;
            }

            student = StudentExcelGet(row, student);

            StudentList.add(student);
        }


        return StudentList;
    }

    public List<Teacher> batchInputTeacher(String fileName, MultipartFile file) throws Exception {
        boolean notNull = false;
        List<Teacher> teacherList = new ArrayList<>();

        boolean isExcel2003 = true;

        InputStream is = file.getInputStream();
        Workbook wb = null;

        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }

        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if(sheet!=null){
            notNull = true;
        }

        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            Teacher teacher = new Teacher();
            if (row == null){
                continue;
            }

            teacher = TeacherExcelGet(row, teacher);

            teacherList.add(teacher);
        }


        return teacherList;
    }
    private Student StudentExcelGet(Row row, Student student) {

        /**
         *      private String stu_num;  //学号
         *     private String stu_ID;   //身份证号
         *     private String stu_name; //姓名
         *     private String stu_faculty;//就读学院
         *     private String dept; //就读专业
         *     private String grade;      //就读班级
         *     private String stu_pwd;  //登录密码
         */
        // 将列中的内容都设置成String类型格式 row.getCell(要设置的列数，0).setCellType(Cell.CELL_TYPE_STRING);
        Cell cell = row.getCell(0);
        cell.setCellType(CellType.STRING);
        String stu_num = row.getCell(0).getStringCellValue();
//        if(pId == null || pId.isEmpty()){
//            pId = "0";
//        }
        student.setStu_num(stu_num);


        String stu_ID = row.getCell(1).getStringCellValue();
//        if(name==null || name.isEmpty()){
//            name = "0";
//        }
        student.setStu_ID(stu_ID);

        String stu_name = row.getCell(2).getStringCellValue();
        student.setStu_name(stu_name);

        String stu_faculty = row.getCell(3).getStringCellValue();
        student.setStu_faculty(stu_faculty);

        String dept = row.getCell(4).getStringCellValue();
        student.setDept(dept);

        String grade = row.getCell(5).getStringCellValue();
        student.setGrade(grade);

        String stu_pwd = row.getCell(6).getStringCellValue();
        student.setStu_pwd(stu_pwd);
        return  student;
    }

    private Teacher TeacherExcelGet(Row row, Teacher teacher) {

       /* private String t_num;        //教师工号
        private String t_name;       //教师姓名
        private String t_faculty;    //所属学院
        private String t_dept;       //所属专业
        private String t_office;     //所属教研室
        private String t_pwd;        //登陆密码
        private String group_id;     //权限组
*/
       return teacher;
    }
}
