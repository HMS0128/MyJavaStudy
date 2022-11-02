package com.hms.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.nanoTime();   //获取开始时间
        System.out.println("----------------------------------------------开始----------------------------------------------");

        Logger logger = LoggerFactory.getLogger("LoggerName（记录器名称）");

        logger.trace("test_trace");
        logger.debug("test_debug");
        logger.info("test_info");
        logger.warn("test_warn");
        logger.error("test_error");


        System.out.println("----------------------------------------------结束----------------------------------------------");
        long endTime = System.nanoTime(); //获取结束时间
        System.out.println("程序运行耗时： " + (endTime - startTime) / 1000000 + "毫秒");

    }

}
