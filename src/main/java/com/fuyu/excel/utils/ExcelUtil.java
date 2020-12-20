package com.fuyu.excel.utils;

import com.fuyu.excel.annotation.ExcelField;
import com.fuyu.excel.annotation.FilterAuth;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtil<T>{

    private Class<T> clazz;

    public ExcelUtil(Class<T> clazz){
        this.clazz = clazz;
    }

    public  String importExcel(MultipartFile file, List<T> dataList) throws Exception{
        String fileName = file.getOriginalFilename();
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        Workbook workbook = null;
        if(prefix.endsWith("xls")){
            workbook = new HSSFWorkbook(file.getInputStream());
        }else if(prefix.endsWith("xlsx")){
            workbook = new XSSFWorkbook(file.getInputStream());
        }
        List<Object[]> objFieldList = getObjectFieldList();
        //获取第一个工作表空间
        Sheet sheet = workbook.getSheetAt(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //获取值,从第三行开始
        StringBuilder errorMsg = new StringBuilder();
        Integer rows = sheet.getPhysicalNumberOfRows();
        for(int i=1;i<rows;i++){
            Row row = sheet.getRow(i);
            if(null != row){
                T entity = null;
                Integer errorCount = 0;
                Integer cells = row.getPhysicalNumberOfCells();
                if(null != cells && cells > 0){
                    entity = clazz.newInstance();
                    for(int j=1;j<cells;j++){
                        Object[] objects = objFieldList.get(j-1);
                        Class fieldType = (Class)objects[4];
                        Method setMethod = (Method)objects[2];
                        String cellValue = "";
                        Cell cell = row.getCell(j);
                        if(null != cell){
                            switch (cell.getCellTypeEnum()){
                                case STRING:
                                    cellValue = cell.getStringCellValue();
                                    break;
                                case NUMERIC:
                                    if(DateUtil.isCellDateFormatted(cell)){
                                        cellValue = sdf.format(cell.getDateCellValue());
                                    }else{
                                        cellValue = NumberToTextConverter.toText(cell.getNumericCellValue());
                                    }
                                    break;
                                case FORMULA:
                                    cellValue = NumberToTextConverter.toText(cell.getNumericCellValue());
                                    break;
                            }
                            if(!StringUtils.isEmpty(cellValue)){
                                Object value = null;
                                try{
                                    if(Date.class.equals(fieldType)){
                                        value = sdf.parse(cellValue);
                                    }else if(BigDecimal.class.equals(fieldType)){
                                        value = new BigDecimal(cellValue);
                                    }else{
                                        value = String.valueOf(cellValue);
                                    }
                                    setMethod.invoke(entity,value);
                                }catch (Exception e){
                                    errorCount++;
                                    break;
                                }
                            }
                        }
                    }
                }
                if(null != entity && 0==errorCount){
                    dataList.add(entity);
                }
            }
        }
        return errorMsg.toString();
    }




    private  List<Object[]> getObjectFieldList() throws Exception{
        Field[] fields = this.clazz.getDeclaredFields();
        if(fields.length == 0){
            return null;
        }
        List<Object[]> result = new ArrayList<>();
        for(Field field:fields){
            ExcelField excelField = field.getAnnotation(ExcelField.class);
            if(null == excelField){
                continue;
            }
            String fieldName = field.getName();
            Class filedType = field.getType();
            String setMethodName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
            Method setMethod = clazz.getMethod(setMethodName,filedType);
            String getMethodName = "get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
            Method getMethod = clazz.getMethod(getMethodName);
            Object[] objects = new Object[5];
            objects[0] = excelField.title();
            objects[1] = excelField.sort();
            objects[2] = setMethod;
            objects[3] = getMethod;
            objects[4] = filedType;
            result.add(objects);
        }
        //进行排序
        Collections.sort(result, new Comparator<Object[]>() {
            @Override
            public int compare(Object[] o1, Object[] o2) {
                return (Integer)o1[1] - (Integer)o2[1];
            }
        });
        return result;
    }
}
