# Software Requirements Specification (SRS)
## Library Management System

---

## 1. Introduction

The Library Management System is a web-based application developed using Java Servlets, JSP, JDBC, and MySQL.  
The system is designed to manage library operations such as maintaining a book catalog, issuing and returning books, and managing user access through roles.

This project follows industry-standard MVC architecture and is intended for educational and practical full-stack Java development.

---

## 2. User Roles

### 2.1 Admin
- Add, edit, and delete books
- View complete book inventory
- View issued books and reports
- Manage system-level operations

### 2.2 Librarian
- Issue books to borrowers
- Return issued books
- View available books and issued books

### 2.3 Member
- View available books
- View books issued to them (future enhancement)

---

## 3. Functional Requirements

### 3.1 Authentication
- The system shall allow users to log in using username and password.
- The system shall validate credentials securely using encrypted passwords.
- The system shall maintain user sessions.

### 3.2 Book Management
- Admin shall be able to add new books.
- Admin shall be able to edit existing book details.
- Admin shall be able to delete books.
- All users shall be able to view the book list.

### 3.3 Loan Management
- Librarian/Admin shall be able to issue books if copies are available.
- Librarian/Admin shall be able to return issued books.
- The system shall automatically update available copies.

### 3.4 Authorization
- The system shall restrict actions based on user roles.
- Unauthorized users shall not access protected resources.

---

## 4. Non-Functional Requirements

### 4.1 Performance
- The system should handle multiple users efficiently.
- Database queries should be optimized using indexes.

### 4.2 Security
- Passwords must be stored using secure hashing (BCrypt).
- Session-based authentication must be enforced.
- Role-based authorization must be applied.

### 4.3 Usability
- The UI should be simple and easy to navigate.
- Error messages should be user-friendly.

### 4.4 Maintainability
- The system should follow clean code principles.
- Code should be modular and well-documented.

---

## 5. Constraints

- The application uses Java Servlets and JSP (no Spring framework).
- MySQL is used as the database.
- The system is deployed on Apache Tomcat.
- The project is intended for learning and demonstration purposes.
