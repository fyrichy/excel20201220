package com.fuyu.excel.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询基类
 */
@Data
public class BaseQueryDTO implements Serializable {
    private static final long serialVersionUID = 4275553842263491046L;
    DataAuthResp dataAuthResp;
}
