package com.edu.springcloud.dao;

import com.edu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 支付模块数据库访问对象类
 */
@Mapper
public interface PaymentDao {
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
