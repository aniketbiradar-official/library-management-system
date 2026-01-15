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
}
