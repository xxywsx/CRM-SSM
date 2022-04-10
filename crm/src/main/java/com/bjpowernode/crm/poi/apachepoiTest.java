package com.bjpowernode.crm.poi;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class apachepoiTest {

    public static void main(String[] args) throws Exception{
        //创建一个excel文件
        HSSFWorkbook we = new HSSFWorkbook();
        //创建一页文件
        HSSFSheet sh = we.createSheet("学生信息");
        //创建一行
        HSSFRow ro = sh.createRow(0);//行号，从0开始
        //创建一列
        HSSFCell ce = ro.createCell(0);//列号，从0开始
        //使用rows创建对象
        ce.setCellValue("姓名");
        HSSFCell ce1 = ro.createCell(1);
        ce1.setCellValue("学号");
        HSSFCell ce2 = ro.createCell(2);
        ce2.setCellValue("年龄");
        //创建style
        HSSFCellStyle style = we.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER );

        //使用sh创建10个对象，对应sheet中的10行
        for(int i=1;i <= 10;i++) {
            ro = sh.createRow(i);

            ce = ro.createCell(0);
            ce.setCellValue("name" + i);
            ce1 = ro.createCell(1);
            ce1.setCellValue("100" + i);
            ce2 = ro.createCell(2);
            ce2.setCellStyle(style);
            ce2.setCellValue("10" + i);
        }

        //调用工具函数
        OutputStream os =  new FileOutputStream("E:\\新建文件夹\\演示文件\\studentList.xls");
        we.write(os);

        os.close();
        we.close();

        System.out.println("---------------");
    }
}
