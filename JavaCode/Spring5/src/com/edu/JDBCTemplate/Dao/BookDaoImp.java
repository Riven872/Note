package com.edu.JDBCTemplate.Dao;

import com.edu.JDBCTemplate.Entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDaoImp implements IBookDao {

    //注入JDBCTemplate
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void add(Book book) {
        //1、创建sql语句
        String sql = "INSERT INTO book VALUES(?,?,?)";
        //2、调用方法实现
        Object[] args = {book.getBookId(), book.getBookName(), book.getBookStatus()};
        int updateNum = jdbcTemplate.update(sql, args);

        System.out.println(updateNum);
    }

    @Override
    public int selectCount() {
        String sql = "SELECT COUNT(1) FROM book";
        //第二个参数是返回类型的Class
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count;
    }

    @Override
    public Book findOne(String id) {
        String sql = "SELECT * FROM book WHERE bookid = ?";
        Book book = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Book>(Book.class), id);
        return book;
    }

    /**
     * 批量添加
     *
     * @param batchArgs 批量添加的值
     */
    @Override
    public void batchAddBook(List<Object[]> batchArgs) {
        String sql = "INSERT INTO book VALUES(?, ?, ?)";
        int count = jdbcTemplate.batchUpdate(sql, batchArgs).length;
    }
}
