package com.edu.mybatis.mapper;

import com.edu.mybatis.entity.Dept;
import org.apache.ibatis.annotations.Param;

public interface DeptMapper {
    /**
     * 获取部门以及部门中所有的员工信息
     */
    Dept getDeptAndEmp(@Param("did") Integer did);
}
