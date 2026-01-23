package com.library.dao.book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.library.model.Book;
import com.library.util.DBConnectionUtil;

public class BookDAO {

    public void addBook(Book book) {
        String sql = """
            INSERT INTO books
            (title, author, isbn, total_copies, available_copies, category)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getIsbn());
            ps.setInt(4, book.getTotalCopies());
            ps.setInt(5, book.getAvailableCopies());
            ps.setString(6, book.getCategory());

            ps.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RuntimeException("ISBN_ALREADY_EXISTS");
        } catch (SQLException e) {
            throw new RuntimeException("Error adding book", e);
        }
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setIsbn(rs.getString("isbn"));
                book.setTotalCopies(rs.getInt("total_copies"));
                book.setAvailableCopies(rs.getInt("available_copies"));
                book.setCategory(rs.getString("category"));
                books.add(book);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching books", e);
        }
        return books;
    }

    public Book getBookById(int id) {
        String sql = "SELECT * FROM books WHERE id=?";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Book book = new Book();
                book.setId(id);
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setIsbn(rs.getString("isbn"));
                book.setTotalCopies(rs.getInt("total_copies"));
                book.setAvailableCopies(rs.getInt("available_copies"));
                book.setCategory(rs.getString("category"));
                return book;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching book", e);
        }
        return null;
    }

    private int getIssuedCopies(int bookId) throws SQLException {
        String sql = "SELECT total_copies, available_copies FROM books WHERE id=?";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("total_copies") - rs.getInt("available_copies");
            }
        }
        return 0;
    }

    public void updateBook(Book book) {
        String sql = """
            UPDATE books
            SET title=?, author=?, isbn=?, total_copies=?, available_copies=?, category=?
            WHERE id=?
        """;

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int issuedCopies = getIssuedCopies(book.getId());
            int newAvailable = book.getTotalCopies() - issuedCopies;

            if (newAvailable < 0) {
                throw new RuntimeException(
                    "Total copies cannot be less than already issued copies"
                );
            }

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getIsbn());
            ps.setInt(4, book.getTotalCopies());
            ps.setInt(5, newAvailable);
            ps.setString(6, book.getCategory());
            ps.setInt(7, book.getId());

            ps.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RuntimeException("ISBN_ALREADY_EXISTS");
        } catch (SQLException e) {
            throw new RuntimeException("Error updating book", e);
        }
    }

    public void deleteBook(int id) {
        String sql = "DELETE FROM books WHERE id=?";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting book", e);
        }
    }
    
    public List<Book> searchBooks(String q, String category, String availability) {

        List<Book> books = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
            "SELECT * FROM books WHERE 1=1"
        );

        List<Object> params = new ArrayList<>();

        if (q != null && !q.trim().isEmpty()) {
            sql.append(" AND (title LIKE ? OR author LIKE ?)");
            params.add("%" + q + "%");
            params.add("%" + q + "%");
        }

        if (category != null && !category.trim().isEmpty()) {
            sql.append(" AND category = ?");
            params.add(category);
        }

        if ("available".equals(availability)) {
            sql.append(" AND available_copies > 0");
        } else if ("unavailable".equals(availability)) {
            sql.append(" AND available_copies = 0");
        }

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setIsbn(rs.getString("isbn"));
                book.setTotalCopies(rs.getInt("total_copies"));
                book.setAvailableCopies(rs.getInt("available_copies"));
                book.setCategory(rs.getString("category"));
                books.add(book);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error searching books", e);
        }

        return books;
    }
    
    public List<String> getAllCategories() {

        List<String> categories = new ArrayList<>();

        String sql = "SELECT DISTINCT category FROM books WHERE category IS NOT NULL";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                categories.add(rs.getString("category"));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching categories", e);
        }

        return categories;
    }


}
