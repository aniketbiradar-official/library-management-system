package com.library.service.book;

import java.util.List;

import com.library.dao.book.BookDAO;
import com.library.model.Book;

public class BookService {

	private final BookDAO bookDAO = new BookDAO();
	
	public void addBook(Book book) {
		
		book.setAvailableCopies(book.getTotalCopies());
		bookDAO.addBook(book);
	}
	
	public List<Book> getAllBooks() {
		return bookDAO.getAllBooks();
	}
}
