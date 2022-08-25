package com.edu.mybatis.mapper;

public interface UserMapper {

    /**
     * 添加用户信息
     *
     * @return
     */
    int insertUser();

    /**
     * 修改用户信息
     *
     * @return
     */
    int updateUser();
}
