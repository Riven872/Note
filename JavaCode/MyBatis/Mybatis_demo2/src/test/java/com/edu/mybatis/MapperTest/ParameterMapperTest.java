package com.edu.mybatis.MapperTest;

import com.edu.mybatis.entity.User;
import com.edu.mybatis.mapper.ParameterMapper;
import com.edu.mybatis.utils.sqlSessionUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class ParameterMapperTest {
    @Test
    public void testGetAllUses(){
        SqlSession sqlSession = sqlSessionUtils.getSqlSession();
        ParameterMapper mapper = sqlSession.getMapper(ParameterMapper.class);
        List<User> allUsers = mapper.getAllUsers();
        allUsers.forEach(e -> System.out.println(e));
    }

    @Test
    public void testCheckLoginByParam(){
        SqlSession sqlSession = sqlSessionUtils.getSqlSession();
        ParameterMapper mapper = sqlSession.getMapper(ParameterMapper.class);
        mapper.checkLoginByParam("admin", "123456");
    }
}
