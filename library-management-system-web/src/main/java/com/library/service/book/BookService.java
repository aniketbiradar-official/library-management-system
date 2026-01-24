package com.library.service.book;

import java.util.List;

import com.library.dao.book.BookDAO;
import com.library.model.Book;

public class BookService {

    private final BookDAO bookDAO = new BookDAO();
    private static final int PAGE_SIZE = 5;

    public void addBook(Book book) {
        // Available copies always start equal to total
        book.setAvailableCopies(book.getTotalCopies());
        bookDAO.addBook(book);
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

    // Search + Filter + Sort + Pagination
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
}
