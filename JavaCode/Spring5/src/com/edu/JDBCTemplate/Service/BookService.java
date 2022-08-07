package com.edu.JDBCTemplate.Service;

import com.edu.JDBCTemplate.Dao.IBookDao;
import com.edu.JDBCTemplate.Entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    //注入Dao
    @Autowired
    private IBookDao bookDao;

    //添加的方法
    public void addBook(Book book) {
        bookDao.add(book);
    }

    //查询整表
    public void findOne(String id){
        System.out.println(bookDao.findOne(id));;
    }
}
