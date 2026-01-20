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
import com.library.util.ValidationUtil;

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

 // ONLY showing corrected addBook() and updateBook()
 // rest of your controller is fine

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
	             request.setAttribute("error", "A book with this ISBN already exists.");
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
            throws IOException, ServletException {

        if (!isAdmin(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String idStr = request.getParameter("id");
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String isbn = request.getParameter("isbn");
        String totalCopies = request.getParameter("totalCopies");
        String category = request.getParameter("category");

        // Basic validation
        if (ValidationUtil.isNullOrEmpty(idStr) ||
            ValidationUtil.isNullOrEmpty(title) ||
            ValidationUtil.isNullOrEmpty(author) ||
            ValidationUtil.isNullOrEmpty(isbn) ||
            ValidationUtil.isNullOrEmpty(totalCopies)) {

            request.setAttribute("error", "All fields are required.");
            forwardBackToEdit(request, response, idStr);
            return;
        }

        if (!ValidationUtil.isPositiveNumber(idStr)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (!ValidationUtil.isValidISBN(isbn)) {
            request.setAttribute("error", "Invalid ISBN format.");
            forwardBackToEdit(request, response, idStr);
            return;
        }

        if (!ValidationUtil.isPositiveNumber(totalCopies)) {
            request.setAttribute("error", "Total copies must be a positive number.");
            forwardBackToEdit(request, response, idStr);
            return;
        }

        // Safe conversion
        Book book = new Book();
        book.setId(Integer.parseInt(idStr));
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setTotalCopies(Integer.parseInt(totalCopies));
        book.setCategory(category);

        bookService.updateBook(book);
        response.sendRedirect(request.getContextPath() + "/books");
    }
    
    private void forwardBackToEdit(HttpServletRequest request, HttpServletResponse response,
            String id) throws ServletException, IOException {

    	int bookId = Integer.parseInt(id);
    	Book book = bookService.getBookById(bookId);
    	request.setAttribute("book", book);

    	request.getRequestDispatcher("/WEB-INF/views/book/book-edit.jsp")
    	.forward(request, response);
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
