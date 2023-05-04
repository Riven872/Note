package com.automannn.springPractice.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author chenkh
 * @time 2021/9/10 16:28
 */
public class LogTest {
    public static void main(String[] args) {
        Log log = LogFactory.getLog(LogTest.class);
        /*xxx: 默认情况下，slf4j的日志情况*/
        System.out.println(log.isTraceEnabled());
        System.out.println(log.isDebugEnabled());
        System.out.println(log.isFatalEnabled());
        System.out.println(log.isWarnEnabled());
        System.out.println(log.isInfoEnabled());
        System.out.println(log.isErrorEnabled());
        log.trace("程序开始运行");
        log.info("业务接口1执行前");
        System.out.println("业务接口1");
        log.debug("业务接口1执行后");
        log.warn("业务接口2执行前");

        System.out.println("业务接口2");
        log.error("业务接口2执行后");
        log.fatal("执行完毕");
    }
}
