package com.automannn.springPractice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.xml.ws.WebServiceRef;

/**
 * @author chenkh
 * @time 2021/9/27 10:13
 */
@Component
@ImportStrategy(highPerformance = true)
@Import(value = {ImportBeanTest.MyBeanDefintionRegistrar.class,ImportBeanTest.MyImportSelector.class,ImportBeanTest.class}) //第四优先级
public class ConfigurerBeanTwo {

//    @EJB //通用注解
    private Object myEjg;

//    @Resource
    private ConfigurerBean configurerBean;

//    @WebServiceRef
    private Object myWebService;

    @PostConstruct
    public void postConstruct(){
        System.out.println("postConstruct");
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println("preDestroy");
    }
}
