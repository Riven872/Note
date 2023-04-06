package com.automannn.springPractice.configuration;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;

/**
 * @author chenkh
 * @time 2021/9/27 9:51
 */
public class ConfigurerBean {

    @EventListener
    public void onEvent(ApplicationEvent applicationEvent){
        System.out.println("got a message: "+ applicationEvent.getSource());
    }
}
