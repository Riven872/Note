package com.edu.mybatis;

import com.edu.mybatis.entity.Dept;
import com.edu.mybatis.entity.Emp;
import com.edu.mybatis.mapper.DeptMapper;
import com.edu.mybatis.mapper.DynamicMapper;
import com.edu.mybatis.mapper.EmpMapper;
import com.edu.mybatis.utils.sqlSessionUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class ResultMapTest {
    @Test
    public void testGetAllEmp() {
        SqlSession sqlSession = sqlSessionUtils.getSqlSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        List<Emp> allEmp = mapper.getAllEmp();
        allEmp.forEach(s -> System.out.println(s));
    }

    @Test
    public void testGetDeptAndEmp() {
        SqlSession sqlSession = sqlSessionUtils.getSqlSession();
        DeptMapper mapper = sqlSession.getMapper(DeptMapper.class);
        Dept dept = mapper.getDeptAndEmp(1);
        System.out.println(dept);
    }

    @Test
    public void testGetEmpCondition() {
        SqlSession sqlSession = sqlSessionUtils.getSqlSession();
        DynamicMapper mapper = sqlSession.getMapper(DynamicMapper.class);
        List<Emp> emps = mapper.getEmpCondition(new Emp(null, "张三", 23, "男", "123@qq.com", null));
        System.out.println(emps);
    }
}
