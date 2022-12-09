package com.edu.springcloud.service.impl;

import com.edu.springcloud.dao.OrderDao;
import com.edu.springcloud.entity.Order;
import com.edu.springcloud.service.AccountService;
import com.edu.springcloud.service.OrderService;
import com.edu.springcloud.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;

    @Resource
    private StorageService storageService;

    @Resource
    private AccountService accountService;

    @Override
    @GlobalTransactional(name = "foo-create-order", rollbackFor = Exception.class)
    public void create(Order order) {
        log.info("------>开始创建新订单");
        orderDao.create(order);

        log.info("------>订单微服务开始调用库存，做扣减Count");
        storageService.decrease(order.getProductId(), order.getCount());
        log.info("------>订单微服务开始调用库存，做扣减结束");

        log.info("------>订单微服务开始调用账户，做扣减Money");
        accountService.decrease(order.getUserId(), order.getMoney());
        log.info("------>订单微服务开始调用账户，做扣减结束");

        log.info("------>修改订单状态开始");
        orderDao.update(order.getUserId(), 0);
        log.info("------>修改订单状态结束");

        log.info("------>订单服务全部结束");
    }
}
