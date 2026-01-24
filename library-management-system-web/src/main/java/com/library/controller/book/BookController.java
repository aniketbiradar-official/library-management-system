package com.library.controller.book;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.library.model.Book;
import com.library.model.user.User;
import com.library.service.book.BookService;
import com.library.util.ValidationUtil;

@WebServlet("/books/*")
public class BookController extends HttpServlet {

    private final BookService bookService = new BookService();

    // ================= SECURITY HELPER =================
    // Centralized admin role check
    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return false;

        User user = (User) session.getAttribute("user");
        return user != null && "ADMIN".equals(user.getRole());
    }

    // ================= ROUTING =================
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

    // ================= LIST BOOKS (SEARCH + SORT + PAGINATION) =================
    private void listBooks(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ----- Filters -----
        String q = request.getParameter("q");
        String category = request.getParameter("category");
        String availability = request.getParameter("availability");

        // ----- Pagination (safe parsing) -----
        int page = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null && pageParam.matches("\\d+")) {
            page = Integer.parseInt(pageParam);
        }

        // ----- Sorting (safe defaults) -----
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");

        if (sort == null || sort.isBlank()) sort = "title";
        order = "desc".equalsIgnoreCase(order) ? "desc" : "asc";

        // ----- Fetch data -----
        List<Book> books =
                bookService.searchBooks(q, category, availability, sort, order, page);

        int totalBooks = bookService.countBooks(q, category, availability);
        int pageSize = bookService.getPageSize();
        int totalPages = (int) Math.ceil((double) totalBooks / pageSize);

        // ----- Attributes for JSP -----
        request.setAttribute("books", books);
        request.setAttribute("categories", bookService.getAllCategories());

        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        request.setAttribute("q", q);
        request.setAttribute("category", category);
        request.setAttribute("availability", availability);
        request.setAttribute("sort", sort);
        request.setAttribute("order", order);

        request.getRequestDispatcher("/WEB-INF/views/book/book-list.jsp")
               .forward(request, response);
    }

    // ================= ADD BOOK =================
    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/book/book-add.jsp")
               .forward(request, response);
    }

    private void addBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String isbn = request.getParameter("isbn");
        String totalCopies = request.getParameter("totalCopies");
        String category = request.getParameter("category");

        // Validation
        if (ValidationUtil.isNullOrEmpty(title) ||
            ValidationUtil.isNullOrEmpty(author) ||
            ValidationUtil.isNullOrEmpty(isbn) ||
            ValidationUtil.isNullOrEmpty(totalCopies)) {

            request.setAttribute("error", "All fields are required.");
            preserveAddFormInput(request, title, author, isbn, totalCopies, category);
            request.getRequestDispatcher("/WEB-INF/views/book/book-add.jsp")
                   .forward(request, response);
            return;
        }

        if (!ValidationUtil.isValidISBN(isbn)) {
            request.setAttribute("error", "Invalid ISBN format.");
            preserveAddFormInput(request, title, author, isbn, totalCopies, category);
            request.getRequestDispatcher("/WEB-INF/views/book/book-add.jsp")
                   .forward(request, response);
            return;
        }

        if (!ValidationUtil.isPositiveNumber(totalCopies)) {
            request.setAttribute("error", "Total copies must be positive.");
            preserveAddFormInput(request, title, author, isbn, totalCopies, category);
            request.getRequestDispatcher("/WEB-INF/views/book/book-add.jsp")
                   .forward(request, response);
            return;
        }

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setTotalCopies(Integer.parseInt(totalCopies));
        book.setCategory(category);

        try {
            bookService.addBook(book);
            response.sendRedirect(request.getContextPath() + "/books");
        } catch (RuntimeException e) {
            if ("ISBN_ALREADY_EXISTS".equals(e.getMessage())) {
                request.setAttribute("error", "ISBN already exists.");
                preserveAddFormInput(request, title, author, isbn, totalCopies, category);
                request.getRequestDispatcher("/WEB-INF/views/book/book-add.jsp")
                       .forward(request, response);
            } else {
                throw e;
            }
        }
    }

    private void preserveAddFormInput(HttpServletRequest request,
                                      String title,
                                      String author,
                                      String isbn,
                                      String totalCopies,
                                      String category) {

        request.setAttribute("title", title);
        request.setAttribute("author", author);
        request.setAttribute("isbn", isbn);
        request.setAttribute("totalCopies", totalCopies);
        request.setAttribute("category", category);
    }

    // ================= EDIT / DELETE =================
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        int id = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("book", bookService.getBookById(id));

        request.getRequestDispatcher("/WEB-INF/views/book/book-edit.jsp")
               .forward(request, response);
    }

    private void updateBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        int id = Integer.parseInt(request.getParameter("id"));

        Book book = new Book();
        book.setId(id);
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
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        int id = Integer.parseInt(request.getParameter("id"));
        bookService.deleteBook(id);

        response.sendRedirect(request.getContextPath() + "/books");
    }
}
