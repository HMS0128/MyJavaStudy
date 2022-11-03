package com.hms.other;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackDemo {

    public static Logger LOG1 = LoggerFactory.getLogger(LogbackDemo.class);
    public static Logger LOG2 = LoggerFactory.getLogger("记录器2");
    public static Logger LOG3 = LoggerFactory.getLogger("记录器3");
//    public static void main(String[] args) {
//        long startTime = System.nanoTime();   //获取开始时间
//        System.out.println("----------------------------------------------开始----------------------------------------------");
//
//        test();
//
//        System.out.println("----------------------------------------------结束----------------------------------------------");
//        long endTime = System.nanoTime(); //获取结束时间
//        System.out.println("程序运行耗时： " + (endTime - startTime) / 1000000 + "毫秒");
//    }

    public static void test() {
        // Logger记录器，getLogger(记录器名称)

        LOG1.debug("主程序的debug");
        LOG1.info("主程序的info");
        LOG1.warn("主程序的warn");
        LOG1.error("主程序的error");

        LOG2.debug("oneInfo的debug");
        LOG2.info("oneInfo的info");
        LOG2.warn("oneInfo的warn");
        LOG2.error("oneInfo的error");

        LOG3.debug("twoInfo的debug");
        LOG3.info("twoInfo的info");
        LOG3.warn("twoInfo的warn");
        LOG3.error("twoInfo的error");
    }
}

