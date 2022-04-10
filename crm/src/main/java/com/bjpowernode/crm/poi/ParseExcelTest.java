package com.bjpowernode.crm.poi;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;

import static com.bjpowernode.crm.comons.utils.HSSFUtils.getCellValueForStr;

/*
使用插件来解析excel文件
 */
public class ParseExcelTest {
    public static void main(String[] args) throws Exception{
        //根据excel文件生成HSSFWorkbook对象，封装excel文件的所有信息
        InputStream is =  new FileInputStream("E:\\aaa\\activity.xls");
        HSSFWorkbook wb = new HSSFWorkbook(is);
        //根据wb获取HFFSheet页的对象，封装一页所有的数据
        HSSFSheet sheet =  wb.getSheetAt(0);
        //根据sheet获取HSSFRow对象，封装一行所有的信息
        HSSFRow row = null;
        HSSFCell cell =null;
        for(int i=0;i<=sheet.getLastRowNum();i++){
            row =  sheet.getRow(i);
            //根据rows获取HSSFCell对象，封装一列所有的信息
            for (int j=0;j<row.getLastCellNum();j++){
                cell = row.getCell(j);

                //获取列中的数据
                System.out.print(getCellValueForStr(cell)+" ");
            }
            //每一行中所有列打完，打印一个换行
            System.out.println();
        }
    }

}
