package com.library.controller.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.library.model.user.User;
import com.library.service.auth.AuthService;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	
	private final AuthService authService = new AuthService();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		User user = authService.login(username, password);
		
		if (user != null) {
			HttpSession session = req.getSession();
			session.setAttribute("user", user);
			resp.sendRedirect(req.getContextPath() + "/books");
		}
		else {
			req.setAttribute("error", "Invalid username or password");
			req.getRequestDispatcher("/login.jsp").forward(req, resp);
		}
	}
}
