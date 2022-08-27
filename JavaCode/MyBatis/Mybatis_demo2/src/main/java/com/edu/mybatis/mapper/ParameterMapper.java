package com.edu.mybatis.mapper;

import com.edu.mybatis.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ParameterMapper {
    /**
     * 查询所有员工信息
     */
    List<User> getAllUsers();

    /**
     * 验证登录（使用@param）
     */
    User checkLoginByParam(@Param("username") String username, @Param("password") String password);
}
