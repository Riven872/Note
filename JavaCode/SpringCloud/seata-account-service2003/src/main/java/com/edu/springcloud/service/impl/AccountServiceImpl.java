package com.edu.springcloud.service.impl;

import com.edu.springcloud.dao.AccountDao;
import com.edu.springcloud.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
    @Resource
    private AccountDao accountDao;

    @Override
    public void decrease(Long userId, BigDecimal money) {
        log.info("------>account-service中扣减账户余额开始");
        //TimeUnit.SECONDS.sleep(30);
        accountDao.decrease(userId, money);
        log.info("------>account-service中扣减账户余额结束");
    }
}
