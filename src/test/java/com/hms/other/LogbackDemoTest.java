package com.hms.other;

import org.junit.Test;


public class LogbackDemoTest {

    @Test
    public void loadLogbackConfig() {
        LogbackDemo.loadLogbackConfig();
    }

    @Test
    public void test1() {
        LogbackDemo.loadLogbackConfig();
        LogbackDemo.test();
    }
}