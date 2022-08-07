package com.edu.JDBCTemplate.Dao;

import com.edu.JDBCTemplate.Entity.Book;

import java.util.List;

public interface IBookDao {
    //添加的方法
    void add(Book book);

    //查询表记录数
    int selectCount();

    //根据Id查询表对象
    Book findOne(String id);

    //批量添加
    void batchAddBook(List<Object[]> batchArgs);
}
