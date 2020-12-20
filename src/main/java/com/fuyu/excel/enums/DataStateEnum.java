package com.fuyu.excel.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 数据状态
 */
public enum DataStateEnum {
    NOT_COMMIT("待提交","0"),
    NOT_REVIEW("待审核","1"),
    PUBLISHED("已发布","2"),
    ADJUST("待调整","3"),
    CANCEL("已作废","4"),
    CANCEL_REVIEW("作废-待审核","5")
    ;

    DataStateEnum(String label,String value){
        this.label = label;
        this.value = value;
    }

    public static String getLabel(String value){
        for(DataStateEnum dataStateEnum:DataStateEnum.values()){
            if(StringUtils.endsWith(dataStateEnum.getValue(),value)){
                return dataStateEnum.getLabel();
            }
        }
        return "";
    }

    private String label;
    private String value;

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
