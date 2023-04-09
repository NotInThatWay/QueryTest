package com.zcy.querytest.controller;

import com.zcy.querytest.service.QueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class QueryController {

    @Autowired
    QueryService queryService;
    @GetMapping("/test")
    public void test(){
        log.info("开始查询");
        queryService.queryThread();
    }
}
