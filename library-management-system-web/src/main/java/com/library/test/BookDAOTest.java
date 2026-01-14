package com.library.test;

import com.library.model.Book;
import com.library.service.book.BookService;

public class BookDAOTest {

    public static void main(String[] args) {

        BookService bookService = new BookService();

        Book book = new Book();
        book.setTitle("Clean Code");
        book.setAuthor("Robert C. Martin");
        book.setIsbn("9780132350884");
        book.setTotalCopies(5);
        book.setCategory("Programming");

        bookService.addBook(book);

        System.out.println("Book inserted successfully");
    }
}
