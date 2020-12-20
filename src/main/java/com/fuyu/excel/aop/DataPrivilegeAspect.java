package com.fuyu.excel.aop;

import com.fuyu.excel.annotation.FilterAuth;
import com.fuyu.excel.dto.DataAuthResp;
import com.fuyu.excel.service.UserDataAuthService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @desc:
 * 权限拦截器
 * @author:Richy
 * @date:2020/12/20/0020
 */
@Aspect
@Component
public class DataPrivilegeAspect {

    private static final String METHOD_GET = "get";
    private static final String METHOD_SET = "set";

    @Autowired
    private UserDataAuthService userDataAuthService;

    @Pointcut("execution(* com.fuyu.excel.controller..*(..))")
    private void pointcut(){}

    
    @Before("pointcut()")
    public void before(JoinPoint joinPoint) throws Exception{
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        //获取拦截的方法
        Method method = methodSignature.getMethod();
        FilterAuth filterAuth = method.getAnnotation(FilterAuth.class);
        Object[] args = joinPoint.getArgs();
        if(null == args || args.length == 0){
            return;
        }
        Object obj = args[0];
        doFilterAuth(obj,filterAuth);
    }

    /**
     * 权限过滤，通过传递的网点，进行过滤后，返回拥有的网点权限
     * @param obj：对象
     * @param filterAuth：根据这个注解判断是否需要进行数据权限过滤
     * @throws Exception
     */
    private void doFilterAuth(Object obj, FilterAuth filterAuth) throws Exception{
        if(null == filterAuth){
            return;
        }
        if(StringUtils.isBlank(filterAuth.value())){
            throw new Exception("请指定数据权限要校验的字段");
        }
        String property = filterAuth.value();
        //校验是否有property
        if(!hasProperty(obj,property)){
            throw new Exception("字段："+property+"不存在");
        }
        //校验是否有DataAuthResp类型的属性
        if(!hasField(obj, DataAuthResp.class)){
            throw new Exception("请定义DataAuthResp类型的字段");
        }
        Method getMethod = getMethod(obj,property,METHOD_GET);
        String filterProValue = getMethod.invoke(obj) == null?"":String.valueOf(getMethod.invoke(obj));
        DataAuthResp dataAuthResp = userDataAuthService.filterDataAuth(filterProValue);
        if(null == dataAuthResp && StringUtils.isNotBlank(filterProValue)){
            throw new Exception("无 "+ filterProValue +" 网点数据权限");
        }
        Method setMethod = getMethod(obj,DataAuthResp.class,METHOD_SET);
        setMethod.invoke(obj,dataAuthResp);
    }

    private Method getMethod(Object obj, Class clazz,String methodType) throws Exception{
        String property = getProperty(obj,clazz);
        String methodName = getMethodName(property,methodType);
        return obj.getClass().getMethod(methodName,clazz);
    }

    /**
     * 根据属性获取名称
     * @param obj
     * @param clazz
     * @return
     */
    private String getProperty(Object obj,Class clazz){
        List<Field> fields = getFields(obj);
        if(CollectionUtils.isEmpty(fields)){
            return "";
        }
        for(Field field:fields){
            if(StringUtils.equals(field.getType().getName(),clazz.getName())){
                return field.getName();
            }
        }
        return "";
    }
    /**
     * 根据
     * @param obj
     * @param property
     * @return
     */
    private Method getMethod(Object obj, String property,String methodType) throws Exception{
        String methodName = getMethodName(property,methodType);
        return obj.getClass().getMethod(methodName);
    }

    private String getMethodName(String property, String methodType) {
        return methodType+property.substring(0,1).toUpperCase()+property.substring(1);
    }

    /**
     * 校验是否有指定类型的字段
     * @param obj
     * @param clazz
     */
    private boolean hasField(Object obj, Class clazz) {
        List<Field> fields = getFields(obj);
        if(CollectionUtils.isEmpty(fields)){
            return false;
        }
        for(Field field:fields){
            System.out.println(field.getType().getName()+"<----->"+clazz.getName());
            if(StringUtils.equals(field.getType().getName(),clazz.getName())){
                return true;
            }
        }
        return false;
    }

    /**
     * 获取属性
     * @param obj
     * @return
     */
    public List<Field> getFields(Object obj){
        Class clazz = obj.getClass();
        List<Field> fields = new ArrayList<>();
        while(null != clazz){
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    /**
     * 校验属性是否存在
     * @param obj
     * @param property
     * @return
     */
    public boolean hasProperty(Object obj,String property){
        List<Field> fields = getFields(obj);
        if(CollectionUtils.isEmpty(fields)){
            return false;
        }
        for(Field field:fields){
            if(StringUtils.equals(field.getName(),property)){
                return true;
            }
        }
        return false;
    }
}
