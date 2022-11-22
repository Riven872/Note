package com.edu.springcloud.service.impl;

import com.edu.springcloud.dao.PaymentDao;
import com.edu.springcloud.entities.Payment;
import com.edu.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 支付模块功能接口实现类
 */
@Service
public class PaymentServiceImpl implements PaymentService {
    @Resource
    private PaymentDao paymentDao;

    /**
     * 写操作
     *
     * @param payment
     * @return
     */
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    /**
     * 读操作
     *
     * @return
     */
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
