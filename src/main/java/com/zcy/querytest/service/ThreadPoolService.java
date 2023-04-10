package com.zcy.querytest.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ThreadPoolService {
    public static ExecutorService cachedExecutor = Executors.newCachedThreadPool();
    public static ExecutorService fixedExecutor = Executors.newFixedThreadPool(200);
}
