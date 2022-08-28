package com.edu.mybatis.mapper;

import com.edu.mybatis.entity.Emp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmpMapper {
    /**
     * 查询所有的员工信息
     */
    List<Emp> getAllEmp();

    /**
     * 根据员工id查询员工以及员工所对应的部门信息
     */
    Emp getEmpAndDept(@Param("eid") Integer eid);
}
