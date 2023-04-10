package com.zcy.querytest.controller;

import com.zcy.querytest.service.QueryService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class QueryController {

    @Autowired
    QueryService queryService;

    @Value("${config.numIteration}")
    private int numIteration;

    @Value("${config.frequency}")
    private long frequency;

    @GetMapping("/test")
    public void test() throws InterruptedException {
        log.info("开始查询");
        for(int i = 0; i < numIteration; i++) {
            queryService.queryThread();
            Thread.sleep(frequency);
        }
    }

    @GetMapping("/num")
    public int getNumRequest(){
        return QueryService.numRequest;
    }
}
