package com.example.strategy;

import com.example.annotation.WelfareCode;
import com.example.enums.WelfareCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author riven
 * @date 2025/3/28 0028 18:52
 * @description 策略上下文类，加载类到容器中，并向外提供获取策略的方法
 */
@Component
@Slf4j
public class WelfareStrategyContext {
    /**
     * 聚合所有策略
     */
    private final Map<String, WelfareStrategy> STRATEGIES = new HashMap<>();

    @Resource
    private ApplicationContext applicationContext;

    /**
     * 读取自定义注解标注的策略类，并将策略类加载到容器中
     */
    @PostConstruct
    private void init() {
        // 获取自定义注解标注的类
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(WelfareCode.class);

        log.info("权益注解获取完成，beansWithAnnotation = [{}]", beansWithAnnotation);

        // 权益策略加载到容器中
        beansWithAnnotation.forEach((key, value) -> {
            STRATEGIES.put(value.getClass().getAnnotation(WelfareCode.class).welfareCode().getCode(),
                    (WelfareStrategy) value);
        });

        log.info("权益策略加载完毕，STRATEGIES = [{}]", STRATEGIES);
    }

    /**
     * 根据权益标识获取对应的策略
     *
     * @param welfareCode 权益标识
     * @return 权益策略
     */
    public WelfareStrategy getWelfareStrategy(WelfareCodeEnum welfareCodeEnum) {
        WelfareStrategy welfareStrategy = STRATEGIES.get(welfareCodeEnum.getCode());

        if (Objects.isNull(welfareStrategy)) {
            log.error("获取权益策略失败，权益标识 welfareCode = [{}]", welfareCodeEnum.getCode());
            return null;
        }

        return welfareStrategy;
    }
}
