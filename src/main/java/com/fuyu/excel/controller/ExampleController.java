package com.fuyu.excel.controller;

import com.fuyu.excel.annotation.FilterAuth;
import com.fuyu.excel.dto.QueryReq;
import com.fuyu.excel.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("example")
public class ExampleController {

    @Autowired
    private ExcelService excelService;

    @PostMapping("query")
    @FilterAuth("deptCode")
    public Object query(@RequestBody QueryReq req){
        return req;
    }

    @GetMapping("exportExcel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response){
        excelService.exportExcel(request,response);
    }
}
