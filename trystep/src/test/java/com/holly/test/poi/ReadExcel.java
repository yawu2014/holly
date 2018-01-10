package com.holly.test.poi;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/9 14:18
 */
public class ReadExcel {
    private static final Logger logger = LoggerFactory.getLogger("ReadExcel");
    @Test
    public void readFoodFile(){
        HSSFWorkbook hWorkbook = null;
        File file = FileUtils.getFile("E:\\github\\holly\\trystep\\src\\test\\resources\\饮食与运动标签2017-7-25v1.xls");
        if(file.exists()){
            try {
                // XSSFWorkbook是xlsx文件 HSSFWorkbook是xls文件
                hWorkbook = new HSSFWorkbook(FileUtils.openInputStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<List<String>> lists = Lists.newArrayList();
            for(int i=0;i<41;i++){
                lists.add(Lists.newArrayList());
            }
            if(hWorkbook != null){
                HSSFSheet sheet = hWorkbook.getSheet("饮食项目标签");
                for(int i=5;i<17;i++){
                    Row row = sheet.getRow(i);
                    Cell cell = null;
                    for(int j=7;j<48;j++) {
                        resolveFoodValue(cell=row.getCell(j), j, lists.get(j-7));
                    }
                }
            }else{
                logger.error("hWork is null");
            }
            for(List list:lists){
                if(list != null){
                    for(Object str:list){
                        logger.info(str.toString());
                    }
                }
            }
        }else{
            logger.error("file not exist");
        }
    }

    private void resolveFoodValue(Cell cell, int i, List<String> result) {
        if(cell != null) {
            String str = null;
            if(cell.getCellTypeEnum() == CellType.NUMERIC) {
                str = String.format("hashmap_" + i + ".put('%s','%s');", "DIABETES_" + i, (int)cell.getNumericCellValue());
            }else if(cell.getCellTypeEnum() == CellType.STRING){
                str = String.format("hashmap_" + i + ".put('%s','%s');", "DIABETES_" + i, cell.getStringCellValue());
            }
            result.add(str);
        }else{
            logger.error("cell:"+i+" is null");
        }
    }
    @Test
    public void readSportFile(){
        HSSFWorkbook hWorkbook = null;
        File file = FileUtils.getFile("E:\\github\\holly\\trystep\\src\\test\\resources\\饮食与运动标签2017-7-25v1.xls");
        if(file.exists()){
            try {
                hWorkbook = new HSSFWorkbook(FileUtils.openInputStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<List<String>> lists = Lists.newArrayList();
            for(int i=0;i<41;i++){
                lists.add(Lists.newArrayList());
            }
            if(hWorkbook != null){
                HSSFSheet sheet = hWorkbook.getSheet("运动项目标签");
                logger.info("rowNum:"+sheet.getLastRowNum());
                for(int i=6;i<95;i++){
                    Row row = sheet.getRow(i);
                    Cell cell = null;
                    for(int j=13;j<54;j++) {
                        resolveSportValue(sheet,row, j, lists.get(j-13));
                    }
                }
            }else{
                logger.error("hWork is null");
            }
            for(List list:lists){
                if(list != null){
                    for(Object str:list){
                        logger.info(str.toString());
                    }
                }
            }
        }else{
            logger.error("file not exist");
        }
    }

    private void resolveSportValue(Sheet sheet,Row row, int j, List<String> strings) {
//        logger.info("row:"+row.getRowNum()+"::column"+j);
        Cell cell = row.getCell(j);
        if(cell != null){
            String value = cell.getStringCellValue().trim();
            String res = null;
            String code = null;
            String v_code = getMergedCellValue(row.getRowNum(),4,sheet);//使用2级编码
            if(StringUtils.isEmpty(v_code)){
                code = getMergedCellValue(row.getRowNum(),2,sheet);//使用1级编码
            }else{
                code = v_code;
            }
            String percentTmp = row.getCell(12).getStringCellValue();
            String percent = getPercent(percentTmp);
            if("-".equals(value)){
                res = String.format("hashMap_"+j+".put('%s','%s');",code,percent+"#1");
            }else if("*".equals(value)){
                res = String.format("hashMap_"+j+".put('%s','%s');",code,percent+"#2");
            }else if("x".equals(value)){
                res = String.format("hashMap_"+j+".put('%s','%s');",code,percent+"#0");
            }else{
                logger.error(cell.getRowIndex()+":"+cell.getColumnIndex()+"error value:"+value);
                return;
            }
            strings.add(res);
        }
    }

    private String getPercent(String percentTmp) {
        String regx = "[^0-9]";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(percentTmp);
        return matcher.replaceAll("").trim();
    }

    @Test
    public void readSchemeAdject(){
        HSSFWorkbook hWorkbook = null;
        File file = FileUtils.getFile("E:\\github\\holly\\trystep\\src\\test\\resources\\饮食与运动标签2017-7-25v1.xls");
        if(file.exists()){
            try {
                hWorkbook = new HSSFWorkbook(FileUtils.openInputStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<List<String>> lists = Lists.newArrayList();
            for(int i=0;i<41;i++){
                lists.add(Lists.newArrayList());
            }
            if(hWorkbook != null){
                HSSFSheet sheet = hWorkbook.getSheet("方案调整");
                for(int i=5;i<17;i++){
                    Row row = sheet.getRow(i);
                    Cell cell = null;
                    for(int j=7;j<48;j++) {
                        resolveFoodValue(cell=row.getCell(j), j, lists.get(j-7));
                    }
                }
            }else{
                logger.error("hWork is null");
            }
            for(List list:lists){
                if(list != null){
                    for(Object str:list){
                        logger.info(str.toString());
                    }
                }
            }
        }else{
            logger.error("file not exist");
        }
    }

    public String getMergedCellValue(int row,int column,Sheet sheet){
        int gRow = row;
        int gColumn = column;
        for(int i=0;i<sheet.getNumMergedRegions();i++){
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    gRow = firstRow;
                    gColumn = firstColumn;
                    break;
                }
            }
        }
        Row fRow = sheet.getRow(gRow);
        Cell cell = fRow.getCell(gColumn);
        if(cell != null){
            if(cell.getCellTypeEnum() == CellType.NUMERIC) {
                 return String.valueOf(cell.getNumericCellValue());
            }else if(cell.getCellTypeEnum() == CellType.STRING){
                return cell.getStringCellValue();
            }
        }else{
            logger.error(gRow+":"+gColumn+"-"+row+":"+column+"::ERROR");
        }
        return null;
    }

}
