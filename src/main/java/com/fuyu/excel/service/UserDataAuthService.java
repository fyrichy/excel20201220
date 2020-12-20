package com.fuyu.excel.service;

import com.fuyu.excel.dto.DataAuthResp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDataAuthService {

    /**
     * 过滤数据权限
     * @param deptCode
     * @return
     *
     *
     */
    public DataAuthResp filterDataAuth(String deptCode){
        DataAuthResp allDataAuth = getDataAuth();
        //如果数据权限为空，返回null
        if(isBlankDataAuth(allDataAuth)){
            return null;
        }
        //如果网点编码为空，原样返回
        if(StringUtils.isBlank(deptCode)){
            return allDataAuth;
        }
        //如果有全网的权限，需要对areaCode或者deptCode进行过滤
        if(null != allDataAuth.getHeads()){
            return DataAuthResp.builder().areaCodes(getSet(deptCode)).deptCodes(getSet(deptCode)).build();
        }
        //网点代码不为空,使用网点代码过滤
        if(allDataAuth.getDeptCodes().contains(deptCode)){
            return DataAuthResp.builder().deptCodes(getSet(deptCode)).build();
        }else if(allDataAuth.getAreaCodes().contains(deptCode)){//网点编码
            return DataAuthResp.builder().areaCodes(getSet(deptCode)).build();
        }else{//是否有部分区部下的网点权限
            //根据区部代码获取网点编码
            Set<String> deptCodes = getByAreaCode(deptCode);
            Set<String> temp = allDataAuth.getDeptCodes();
            //去交集
            temp.retainAll(deptCodes);
            if(null != deptCodes && null != temp){
                return DataAuthResp.builder().deptCodes(temp).build();
            }else{
                return null;
            }
        }
    }

    private static DataAuthResp getDataAuth() {
        //return DataAuthResp.builder().areaCodes(getSet("755Y")).deptCodes(getSet("755Y")).build();
        return DataAuthResp.builder().heads(getSet("000")).build();
    }

    private static Set<String> getByAreaCode(String deptCode) {
        //进行查询
        return null;
    }

    /**
     * 是否有网点权限
     * @param allDataAuth
     * @return
     */
    private static boolean isBlankDataAuth(DataAuthResp allDataAuth) {
        if(null == allDataAuth.getHeads() && null == allDataAuth.getAreaCodes() && null == allDataAuth.getDeptCodes()){
            return true;
        }
        return false;
    }

    public static Set<String> getSet(String deptCode){
        Set<String> deptCodes = new HashSet<>(1);
        deptCodes.add(deptCode);
        return deptCodes;
    }
}
