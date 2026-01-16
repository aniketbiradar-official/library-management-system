package com.library.dao.book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import com.library.model.Book;
import com.library.util.DBConnectionUtil;

public class BookDAO {
	
	public void addBook(Book book) {
		String sql = "insert into books " +
				"(title, author, isbn, total_copies, available_copies, category) " +
				"values (?, ?, ?, ?, ?, ?)";
		
		try (Connection conn = DBConnectionUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setString(3, book.getIsbn());
			ps.setInt(4, book.getTotalCopies());
			ps.setInt(5, book.getAvailableCopies());
			ps.setString(6, book.getCategory());
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException("Error adding book", e);
		}
	}
	
	public List<Book> getAllBooks() {
		
		List<Book> books = new ArrayList<>();
		String sql = "select * from books";
		
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
		} catch (SQLIntegrityConstraintViolationException e) {
	        throw new RuntimeException("ISBN_ALREADY_EXISTS");
	    } catch (SQLException e) {
			throw new RuntimeException("Error fetching books", e);
		}
		
		return books;
	}
	
	public Book getBookById(int id) {
		String sql = "select * from books where id = ?";
		
		try (Connection conn = DBConnectionUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				Book book = new Book();
				book.setId(rs.getInt("id"));
				book.setTitle(rs.getString("title"));
				book.setAuthor(rs.getString("author"));
				book.setIsbn(rs.getString("isbn"));
				book.setTotalCopies(rs.getInt("total_copies"));
				book.setAvailableCopies(rs.getInt("available_copies"));
				book.setCategory(rs.getString("category"));
				return book;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error fetching book by id", e);
		}
		return null;
	}
	
	public void updateBook(Book book) {
	    String sql = "UPDATE books SET title=?, author=?, isbn=?, total_copies=?, category=? WHERE id=?";

	    try (Connection conn = DBConnectionUtil.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, book.getTitle());
	        ps.setString(2, book.getAuthor());
	        ps.setString(3, book.getIsbn());
	        ps.setInt(4, book.getTotalCopies());
	        ps.setString(5, book.getCategory());
	        ps.setInt(6, book.getId());

	        ps.executeUpdate();

	    } catch (SQLException e) {
	        throw new RuntimeException("Error updating book", e);
	    }
	}
	
	public void deleteBook(int id) {
	    String sql = "DELETE FROM books WHERE id = ?";

	    try (Connection conn = DBConnectionUtil.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setInt(1, id);
	        ps.executeUpdate();

	    } catch (SQLException e) {
	        throw new RuntimeException("Error deleting book", e);
	    }
	}
}
