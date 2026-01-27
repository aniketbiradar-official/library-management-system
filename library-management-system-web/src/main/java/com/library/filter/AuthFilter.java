package com.library.filter;

import com.library.model.user.User;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
//import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import javax.servlet.http.*;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();

        // ✅ Always allow login controller (GET + POST)
        if (uri.equals(contextPath + "/login")) {
            chain.doFilter(request, response);
            return;
        }
        
        // ✅ Allow registration (GET + POST)
        if (uri.equals(contextPath + "/register")) {
            chain.doFilter(request, response);
            return;
        }

        // ✅ Allow static resources (safe)
        if (uri.startsWith(contextPath + "/css") ||
            uri.startsWith(contextPath + "/js") ||
            uri.startsWith(contextPath + "/images")) {
            chain.doFilter(request, response);
            return;
        }
        
        // Allow reports for authenticated users
        if (uri.startsWith(contextPath + "/reports") ||
        	uri.startsWith(contextPath + "/js") ||
            uri.startsWith(contextPath + "/images")) {
            chain.doFilter(request, response);
            return;
        }


        HttpSession session = req.getSession(false);
        User user = (session != null)
                ? (User) session.getAttribute("user")
                : null;

        if (user == null) {
            resp.sendRedirect(contextPath + "/login");
            return;
        }

        chain.doFilter(request, response);
    }
}
