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
	
	public Book getBookById(int id) {
	    return bookDAO.getBookById(id);
	}

	public void updateBook(Book book) {
	    bookDAO.updateBook(book);
	}

	public void deleteBook(int id) {
	    bookDAO.deleteBook(id);
	}
	
	public List<Book> searchBooks(String q, String category, String availability) {
	    return bookDAO.searchBooks(q, category, availability);
	}
	
	public List<String> getAllCategories() {
	    return bookDAO.getAllCategories();
	}



}
