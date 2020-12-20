package com.fuyu.excel.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 操作
 */
public enum OprTypeEnum {
    COMMIT("提交",0),
    REVIEW("审核",1),
    CANCEL("作废",2),
    REJECT("驳回",3)
    ;

    private String label;
    private Integer value;

    OprTypeEnum(String label,Integer value){
        this.label = label;
        this.value = value;
    }

    public static String getLable(Integer value){
        for(OprTypeEnum oprTypeEnum:OprTypeEnum.values()){
            if(StringUtils.equals(String.valueOf(value),String.valueOf(oprTypeEnum.getValue()))){
                return oprTypeEnum.getLabel();
            }
        }
        return "";
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
