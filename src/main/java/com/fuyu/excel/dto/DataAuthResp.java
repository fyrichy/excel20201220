package com.fuyu.excel.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.Set;

@Data
@Builder
public class DataAuthResp {

    private Set<String> heads;
    private Set<String> areaCodes;
    private Set<String> deptCodes;

    @Tolerate
    public DataAuthResp(){}

}
