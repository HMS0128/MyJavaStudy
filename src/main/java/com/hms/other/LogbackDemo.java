package com.hms.other;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import com.hms.util.FileOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class LogbackDemo {

    public static Logger LOG1 = LoggerFactory.getLogger(LogbackDemo.class);
    public static Logger LOG2 = LoggerFactory.getLogger("记录器2");
    public static Logger LOG3 = LoggerFactory.getLogger("记录器3");


    public static void test() {
        // Logger记录器，getLogger(记录器名称)
        LOG1.trace("主程序的trace");
        LOG1.debug("主程序的debug");
        LOG1.info("主程序的info");
        LOG1.warn("主程序的warn");
        LOG1.error("主程序的error");

        LOG2.trace("oneInfo的trace");
        LOG2.debug("oneInfo的debug");
        LOG2.info("oneInfo的info");
        LOG2.warn("oneInfo的warn");
        LOG2.error("oneInfo的error");

        LOG2.trace("twoInfo的trace");
        LOG3.debug("twoInfo的debug");
        LOG3.info("twoInfo的info");
        LOG3.warn("twoInfo的warn");
        LOG3.error("twoInfo的error");

    }

    /**
     * 自定义配置文件位置
     */
    public static void loadLogbackConfig() {
        /*
            示例：指定配置文件位置为未编译时resources目录下的 conf/logback.xml
            1、项目打包后 classes目录下包含java编译后文件（包变目录形式）和resources目录下所有文件。
            2、先获取classes目录位置，再获取classes目录下的conf/logback.xml文件路径，
            3、去除获取的结果中开头的多余字符 "file:/"
         */
        String configFilePath = String.valueOf(LogbackDemo.class.getClassLoader().getResource("conf/logback.xml"));
        configFilePath = configFilePath.substring(6);

        // 记录器上下文实例
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        // 配置文件
        File configFile = new File(configFilePath);

        if (new FileOperations().isValidExistFile(configFilePath)) {
            // 配置器
            JoranConfigurator configurator = new JoranConfigurator();
            // 设置上下文
            configurator.setContext(lc);
            // 清除记录器上下文内部属性
            lc.reset();
            try {
                // 指定文件为配置文件
                configurator.doConfigure(configFile.getPath());
            } catch (JoranException e) {
                e.printStackTrace();
            }
            // 打印上下文状态的内容，但前提是它们包含警告或错误。
            StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
        }

    }
}

