package com.edu.springcloud.service.impl;

import com.edu.springcloud.dao.StorageDao;
import com.edu.springcloud.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class StorageServiceImpl implements StorageService {
    @Resource
    private StorageDao storageDao;

    @Override
    public void decrease(Long productId, Integer count) {
        log.info("------>StorageService扣减库存开始");
        storageDao.decrease(productId, count);
        log.info("------>StorageService扣减库存结束");
    }
}
