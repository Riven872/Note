package com.edu.springcloud.service;

import com.edu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

/**
 * 支付模块功能接口
 */
public interface PaymentService {
    /**
     * 写操作
     *
     * @param payment
     * @return
     */
    public int create(Payment payment);

    /**
     * 读操作
     *
     * @return
     */
    public Payment getPaymentById(@Param("id") Long id);
}
