package com.edu.JDBCTemplate;

import com.edu.JDBCTemplate.Entity.Book;
import com.edu.JDBCTemplate.Service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {
    @Test
    public void test() {
        ApplicationContext context = new ClassPathXmlApplicationContext("MysqlConnector.xml");
        BookService service = context.getBean("bookService", BookService.class);

        //book对象在实际应用中，应该是页面上传过来的
        Book book = new Book();
        book.setBookId("001");
        book.setBookName("数据结构");
        book.setBookStatus("未借出");

        //service.addBook(book);
        service.findOne("1");
    }
}
