# System Architecture & Design
## Library Management System

---

## 1. Introduction

This document describes the overall system architecture and design of the **Library Management System**, a web-based application built using **Java Servlets, JSP, JDBC, and MySQL**.  
The system follows the **MVC (Model–View–Controller)** architectural pattern to ensure clean separation of concerns, maintainability, and scalability.

---

## 2. Architectural Overview

The application is designed as a **three-tier web application**:

1. Presentation Layer (View)
2. Application Layer (Controller)
3. Data Access Layer (Model / DAO)

### High-Level Request Flow

1. User sends an HTTP request from a browser.
2. The request is received by a Servlet (Controller).
3. The Servlet processes the request and interacts with the DAO layer.
4. DAO layer communicates with the MySQL database using JDBC.
5. Data is returned to the Servlet.
6. Servlet forwards data to a JSP.
7. JSP renders the HTML response to the user.

---

## 3. MVC Architecture

### 3.1 Model Layer

**Purpose:**
- Represents business data
- Encapsulates application state

**Components:**
- Java POJO classes
- DAO classes for database interaction

**Examples:**
- Book
- User
- Loan
- Category

**Rules:**
- No HTML
- No HTTP logic
- No database connection code inside model classes

---

### 3.2 View Layer

**Purpose:**
- Handles UI and presentation
- Displays data to users

**Technology:**
- JSP
- HTML
- CSS
- JSTL

**Examples:**
- login.jsp
- book-list.jsp
- add-book.jsp

**Rules:**
- No SQL code
- No business logic
- Only presentation logic

---

### 3.3 Controller Layer

**Purpose:**
- Handles HTTP requests and responses
- Coordinates between View and Model

**Technology:**
- Java Servlets

**Examples:**
- LoginServlet
- ListBooksServlet
- AddBookServlet

**Rules:**
- No HTML generation
- No direct database access
- Uses DAO classes

---

## 4. Package Structure

Library-Management-System
│
├── docs/
│
├── src/
│   └── main/
│       └── java/
│           └── com/library/
│               ├── model/
│               │   ├── Book.java
│               │   ├── User.java
│               │   ├── Loan.java
│               │   └── Category.java
│               │
│               ├── dao/
│               │   ├── BookDAO.java
│               │   ├── UserDAO.java
│               │   └── LoanDAO.java
│               │
│               ├── servlet/
│               │   ├── auth/
│               │   │   ├── LoginServlet.java
│               │   │   └── LogoutServlet.java
│               │   │
│               │   ├── book/
│               │   │   ├── ListBooksServlet.java
│               │   │   ├── AddBookServlet.java
│               │   │   ├── EditBookServlet.java
│               │   │   └── DeleteBookServlet.java
│               │
│               ├── util/
│               │   ├── DBConnectionUtil.java
│               │   └── PasswordUtil.java
│               │
│               └── filter/
│                   └── AuthFilter.java
│
└── src/
    └── main/
        └── webapp/
            ├── css/
            ├── js/
            ├── jsp/
            │   ├── common/
            │   │   ├── header.jsp
            │   │   └── footer.jsp
            │   │
            │   ├── auth/
            │   │   └── login.jsp
            │   │
            │   ├── book/
            │   │   ├── book-list.jsp
            │   │   ├── add-book.jsp
            │   │   └── edit-book.jsp
            │
            └── WEB-INF/
                └── web.xml


---

## 5. Database Design

### 5.1 Core Tables

- users
- books
- categories
- loans

---

### 5.2 Table Responsibilities

#### users
Stores authentication and role information for Admins, Librarians, and Members.

#### books
Stores book details and inventory information.

#### categories
Stores book classification data.

#### loans
Tracks issued and returned books.

---

### 5.3 Relationships

- One User can have many Loans
- One Book can have many Loans
- One Category can have many Books

---

## 6. DAO Layer Design

**Purpose:**
- Isolate database logic
- Provide CRUD operations
- Prevent SQL injection using PreparedStatement

### Example DAO Responsibilities

**BookDAO**
- getAllBooks()
- getBookById(int id)
- addBook(Book book)
- updateBook(Book book)
- deleteBook(int id)

---

## 7. Session & Security Design

### Authentication
- Users log in via LoginServlet
- On success:
  - HttpSession stores user object
  - Role is stored for authorization

### Authorization
- AuthFilter checks:
  - User login status
  - Role permissions before accessing protected resources

---

## 8. Configuration Strategy

- Database credentials stored in a properties file
- Loaded using DBConnectionUtil
- Credentials are never hard-coded

---

## 9. UI Design Overview

### Common Pages
- Header (navigation bar)
- Footer

### Book Pages
- Book list with search
- Add book form
- Edit book form

### Authentication Pages
- Login page
- Logout action

---

## 10. Design Principles Followed

- MVC architecture
- DAO pattern
- Separation of concerns
- Modular and extensible design
- Industry-standard naming conventions

---

## 11. Future Enhancements

- Email notifications
- Fine calculation
- REST API support
- Mobile application

---

## 12. Conclusion

This architecture ensures that the Library Management System is clean, scalable, maintainable, and aligned with industry best practices. The design provides a solid foundation for implementation and future enhancements.
