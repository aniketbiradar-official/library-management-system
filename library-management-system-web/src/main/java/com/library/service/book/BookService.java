package com.library.service.book;

import java.util.List;

import com.library.dao.book.BookDAO;
import com.library.model.Book;

public class BookService {

	private final BookDAO bookDAO = new BookDAO();
	private static final int PAGE_SIZE = 5;
	
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
	
	public List<Book> searchBooks(
	        String q,
	        String category,
	        String availability,
	        String sort,
	        String order,
	        int page) {

	    int offset = (page - 1) * PAGE_SIZE;
	    return bookDAO.searchBooks(
	            q, category, availability, sort, order, PAGE_SIZE, offset
	    );
	}

	
	public int countBooks(String q, String category, String availability) {
	    return bookDAO.countBooks(q, category, availability);
	}

	public int getPageSize() {
	    return PAGE_SIZE;
	}
	
	public List<String> getAllCategories() {
	    return bookDAO.getAllCategories();
	}

	public List<Book> getBooksPaged(
	        int page,
	        int size,
	        String sortBy,
	        String sortOrder
	) {
	    int offset = (page - 1) * size;
	    return bookDAO.findBooksPaged(offset, size, sortBy, sortOrder);
	}

	public int getTotalPages(int size) {
	    int total = bookDAO.getTotalBookCount();
	    return (int) Math.ceil((double) total / size);
	}


}
