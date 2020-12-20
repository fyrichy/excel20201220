package com.fuyu.excel.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateUtil {


    private static final String DATE_TIME_FORMATE = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMATE = "yyyy-MM-dd";

    public static String dataToStr(LocalDate localDate){
        if(null == localDate){
            return "";
        }
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATE);
            return formatter.format(localDate);
        }catch (Exception e){

        }
        return "";
    }

    public static String dataTimeToStr(LocalDateTime localDateTime){
        if(null == localDateTime){
            return "";
        }
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMATE);
            return formatter.format(localDateTime);
        }catch (Exception e){

        }
        return "";
    }

    public static String dataTimeToStr(LocalDateTime localDateTime,String formate){
        if(null == localDateTime){
            return "";
        }
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formate);
            return formatter.format(localDateTime);
        }catch (Exception e){

        }
        return "";
    }
}
