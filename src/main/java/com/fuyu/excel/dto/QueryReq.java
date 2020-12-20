package com.fuyu.excel.dto;

import lombok.Data;

@Data
public class QueryReq extends BaseQueryDTO{
    private static final long serialVersionUID = -5818468683573209905L;
    private String deptCode;
}
