package com.edu.springcloud.controller;

import com.edu.springcloud.entities.CommonResult;
import com.edu.springcloud.entities.Payment;
import com.edu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 支付模块控制器
 */
@RestController
@RequestMapping("/payment")
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    /**
     * 测试使用
     *
     * @param payment
     * @return
     */
    @PostMapping("/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        if (result > 0) {
            return new CommonResult(200, "插入数据成功，serverPort：" + serverPort, result);
        } else {
            return new CommonResult<>(444, "数据插入失败", null);
        }
    }

    /**
     * 测试使用
     *
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        if (payment != null) {
            return new CommonResult(200, "查询成功，serverPort：" + serverPort, payment);
        } else {
            return new CommonResult(444, "没有对应的数据，查询id为：" + id, null);
        }
    }

    @GetMapping("/lb")
    public String getPaymentLB() {
        return this.serverPort;
    }

    /**
     * 获取注册中心中每个微服务的信息
     *
     * @return
     */
    @GetMapping("/discovery")
    public Object discovery() {
        //region 获取注册中心中，所有的服务
        List<String> services = discoveryClient.getServices();
        services.forEach(service -> {
            log.info("注册中心存的服务为：" + service);
        });
        //endregion

        //region 获取注册中心中某个服务中的信息
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info("CLOUD-PAYMENT-SERVICE实例中的服务信息为：" +
                    instance.getServiceId() + "，" +
                    instance.getHost() + "，" +
                    instance.getPort() + "" +
                    instance.getUri());
        }
        //endregion

        return this.discoveryClient;
    }

    @GetMapping("/zipkin")
    public String paymentZipkin() {
        return "服务提供端9001的zipkin的回调";
    }
}
