package com.bjpowernode.crm.comons.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * 关于excel文件的工具类
 */
public class HSSFUtils {
    /**
     * 从指定的HSSFCell中获取列的值
     */

    public static String getCellValueForStr(HSSFCell cell){
        String ret = "";
        if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING){
            ret = cell.getStringCellValue();
        }else if(cell.getCellType()==HSSFCell.CELL_TYPE_BOOLEAN){
            ret = cell.getBooleanCellValue()+"";
        }else if (cell.getCellType()==HSSFCell.CELL_TYPE_FORMULA){
            ret = cell.getCellFormula();
        }else if (cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
            ret = cell.getNumericCellValue()+"";
        }else {
            ret = "";
        }
        return ret;
    }
}
