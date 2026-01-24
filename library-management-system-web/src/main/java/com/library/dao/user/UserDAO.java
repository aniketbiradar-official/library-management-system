package com.library.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import com.library.model.user.User;
import com.library.util.DBConnectionUtil;
import com.library.util.PasswordUtil;

public class UserDAO {
	public User authenticate(String username, String password) {

	    String sql = "SELECT id, username, password, role FROM users WHERE username=?";

	    try (Connection conn = DBConnectionUtil.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, username);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            String hashedPassword = rs.getString("password");

	            if (PasswordUtil.verifyPassword(password, hashedPassword)) {
	                User user = new User();
	                user.setId(rs.getInt("id"));
	                user.setUsername(rs.getString("username"));
	                user.setRole(rs.getString("role"));
	                return user;
	            }
	        }

	    } catch (SQLException e) {
	        throw new RuntimeException("Authentication failed", e);
	    }

	    return null;
	}
	
	public void registerMember(User user) {

	    String sql = """
	        INSERT INTO users (username, password, role)
	        VALUES (?, ?, 'MEMBER')
	    """;

	    try (Connection conn = DBConnectionUtil.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, user.getUsername());
	        ps.setString(2, user.getPassword());

	        ps.executeUpdate();

	    } catch (SQLIntegrityConstraintViolationException e) {
	        throw new RuntimeException("USERNAME_EXISTS");
	    } catch (SQLException e) {
	        throw new RuntimeException("Error registering member", e);
	    }
	}



}
