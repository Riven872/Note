package com.edu.mybatis.mapper;

import com.edu.mybatis.entity.Emp;

import java.util.List;

public interface DynamicMapper {
    /**
     * 多条件查询
     */
    List<Emp> getEmpCondition(Emp emp);
}
