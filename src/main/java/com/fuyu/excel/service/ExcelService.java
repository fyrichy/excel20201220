package com.fuyu.excel.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.fuyu.excel.dto.ExcelDTO;
import com.fuyu.excel.utils.LocalDateUtil;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ExcelService {

    public void exportExcel(HttpServletRequest request, HttpServletResponse response){
        List<ExcelDTO> excelDTOS = new ArrayList<>();
        for(int i=1;i<=20;i++){
            ExcelDTO excelDTO = ExcelDTO.builder()
                    .string("字符串"+i)
                    .date(new Date())
                    .doubleData(5.6)
                    .invalid(LocalDate.now())
                    .valid(LocalDateTime.now())
                    .dataState((i % 6)+"")
                    .oprType(i % 4).build();
            excelDTOS.add(excelDTO);
        }
        try {
            String fileName = "中转班次-"+ LocalDateUtil.dataTimeToStr(LocalDateTime.now(),"yyyyMMddHHmmss");
            String userAgent = request.getHeader("User-Agent");
            if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            }
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.addHeader("Content-Disposition", "filename=" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), ExcelDTO.class).sheet("sheet").doWrite(excelDTOS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HorizontalCellStyleStrategy getCellStyle(){
        // 头的策略
        WriteCellStyle headCellStyle = new WriteCellStyle();
        // 背景设置为白色
        headCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        //设置水平对齐方式
        headCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
        WriteFont headWriteFont = new WriteFont();
        //字体大小为10，粗体
        headWriteFont.setFontHeightInPoints((short)10);
        headCellStyle.setWriteFont(headWriteFont);

        // 内容的策略
        WriteCellStyle contentCellStyle = new WriteCellStyle();
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short)10);
        contentCellStyle.setWriteFont(contentWriteFont);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(headCellStyle, contentCellStyle);
        return horizontalCellStyleStrategy;
    }
}
