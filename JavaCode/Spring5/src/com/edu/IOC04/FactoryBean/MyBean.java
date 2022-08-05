package com.edu.IOC04.FactoryBean;

import com.edu.bean.Emp;
import org.springframework.beans.factory.FactoryBean;

public class MyBean implements FactoryBean<Emp> {
    @Override
    public Emp getObject() throws Exception {
        return new Emp();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
