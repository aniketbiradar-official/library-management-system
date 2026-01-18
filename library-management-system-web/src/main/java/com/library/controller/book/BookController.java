package com.library.controller.book;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.library.model.Book;
import com.library.service.book.BookService;

@WebServlet("/books/*")
public class BookController extends HttpServlet {

    private final BookService bookService = new BookService();

    // ---------------- SECURITY HELPER ----------------
    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return false;

        Object userObj = session.getAttribute("user");
        if (userObj == null) return false;

        com.library.model.user.User user =
                (com.library.model.user.User) userObj;

        return "ADMIN".equals(user.getRole());
    }

    // ---------------- ROUTING ----------------
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getPathInfo();

        if (path == null || "/".equals(path)) {
            listBooks(request, response);
        } else if ("/add".equals(path)) {
            showAddForm(request, response);
        } else if ("/edit".equals(path)) {
            showEditForm(request, response);
        } else if ("/delete".equals(path)) {
            deleteBook(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getPathInfo();

        if ("/add".equals(path)) {
            addBook(request, response);
        } else if ("/edit".equals(path)) {
            updateBook(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // ---------------- ACTIONS ----------------
    private void listBooks(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Book> books = bookService.getAllBooks();
        request.setAttribute("books", books);
        request.getRequestDispatcher("/WEB-INF/views/book/book-list.jsp")
               .forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/book/book-add.jsp")
               .forward(request, response);
    }

    private void addBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        try {
            Book book = new Book();
            book.setTitle(request.getParameter("title"));
            book.setAuthor(request.getParameter("author"));
            book.setIsbn(request.getParameter("isbn"));
            book.setTotalCopies(Integer.parseInt(request.getParameter("totalCopies")));
            book.setCategory(request.getParameter("category"));

            bookService.addBook(book);

            response.sendRedirect(request.getContextPath() + "/books");

        } catch (RuntimeException e) {
            if ("ISBN_ALREADY_EXISTS".equals(e.getMessage())) {
                request.setAttribute("error", "A book with this ISBN already exists.");
                request.getRequestDispatcher("/WEB-INF/views/book/book-add.jsp")
                       .forward(request, response);
            } else {
                throw e;
            }
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        int id = Integer.parseInt(request.getParameter("id"));
        Book book = bookService.getBookById(id);
        request.setAttribute("book", book);

        request.getRequestDispatcher("/WEB-INF/views/book/book-edit.jsp")
               .forward(request, response);
    }

    private void updateBook(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        if (!isAdmin(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        Book book = new Book();
        book.setId(Integer.parseInt(request.getParameter("id")));
        book.setTitle(request.getParameter("title"));
        book.setAuthor(request.getParameter("author"));
        book.setIsbn(request.getParameter("isbn"));
        book.setTotalCopies(Integer.parseInt(request.getParameter("totalCopies")));
        book.setCategory(request.getParameter("category"));

        bookService.updateBook(book);

        response.sendRedirect(request.getContextPath() + "/books");
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        if (!isAdmin(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        int id = Integer.parseInt(request.getParameter("id"));
        bookService.deleteBook(id);

        response.sendRedirect(request.getContextPath() + "/books");
    }
}
