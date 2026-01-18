package com.library.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.library.model.user.User;
import com.library.util.DBConnectionUtil;

public class UserDAO {
	public User authenticate(String username, String password) {

	    String sql = "select id, username, role from users where username=? and password=?";

	    try (Connection conn = DBConnectionUtil.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, username);
	        ps.setString(2, password);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            User user = new User();
	            user.setId(rs.getInt("id"));
	            user.setUsername(rs.getString("username"));
	            user.setRole(rs.getString("role"));
	            return user;
	        }

	    } catch (SQLException e) {
	        throw new RuntimeException("Authentication failed", e);
	    }
	    return null;
	}

}
