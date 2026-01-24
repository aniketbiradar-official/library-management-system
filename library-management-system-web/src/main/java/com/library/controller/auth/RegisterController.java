package com.library.controller.auth;

import com.library.model.user.User;
import com.library.service.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/register")
public class RegisterController extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/views/auth/register.jsp")
           .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Basic validation
        if (username == null || username.isBlank()
                || password == null || password.length() < 6) {

            req.setAttribute("error", "Username required and password must be at least 6 characters");
            req.getRequestDispatcher("/WEB-INF/views/auth/register.jsp")
               .forward(req, resp);
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        try {
            userService.registerMember(user);
            resp.sendRedirect(req.getContextPath() + "/login");
        } catch (RuntimeException e) {
            if ("USERNAME_EXISTS".equals(e.getMessage())) {
                req.setAttribute("error", "Username already exists");
                req.getRequestDispatcher("/WEB-INF/views/auth/register.jsp")
                   .forward(req, resp);
            } else {
                throw e;
            }
        }
    }
}
