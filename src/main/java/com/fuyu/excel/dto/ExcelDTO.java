package com.fuyu.excel.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.fuyu.excel.converter.DataStateConverter;
import com.fuyu.excel.converter.LocalDateConverter;
import com.fuyu.excel.converter.LocalDateTimeConverter;
import com.fuyu.excel.converter.OprTypeConverter;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@HeadFontStyle(fontHeightInPoints=10)
@HeadStyle(horizontalAlignment= HorizontalAlignment.LEFT,fillForegroundColor=9)
@ContentFontStyle(fontHeightInPoints=10)
public class ExcelDTO {

    @ExcelProperty(value = "字符串标题", index = 0)
    private String string;

    @ExcelProperty(value = "日期标题", index = 1)
    private Date date;

    @ExcelProperty(value = "数字标题", index = 2)
    private Double doubleData;

    @ExcelProperty(value = "生效日期", index = 3,converter = LocalDateConverter.class)
    private LocalDate invalid;

    @ExcelProperty(value = "失效日期", index = 4,converter = LocalDateTimeConverter.class)
    private LocalDateTime valid;

    @ExcelProperty(value = "操作", index = 5,converter = OprTypeConverter.class)
    private Integer oprType;

    @ExcelProperty(value = "数据状态", index = 6,converter = DataStateConverter.class)
    private String dataState;

    @Tolerate
    public ExcelDTO(){}
}
