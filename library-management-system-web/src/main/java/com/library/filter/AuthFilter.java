package com.library.filter;

import com.library.model.user.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter(
    urlPatterns = "/*",
    dispatcherTypes = DispatcherType.REQUEST
)
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        String context = req.getContextPath();

        /* ===============================
           1️⃣ STATIC RESOURCES — ALWAYS ALLOW
           =============================== */
        if (uri.startsWith(context + "/assets/")) {
            chain.doFilter(request, response);
            return;
        }

        /* ===============================
           2️⃣ AUTH PAGES — ALWAYS ALLOW
           =============================== */
        if (uri.equals(context + "/login") ||
            uri.equals(context + "/register")) {
            chain.doFilter(request, response);
            return;
        }

        /* ===============================
           3️⃣ SESSION CHECK
           =============================== */
        HttpSession session = req.getSession(false);
        User user = (session != null)
                ? (User) session.getAttribute("user")
                : null;

        if (user == null) {
            resp.sendRedirect(context + "/login");
            return;
        }

        /* ===============================
           4️⃣ AUTHENTICATED REQUEST
           =============================== */
        chain.doFilter(request, response);
    }
}
