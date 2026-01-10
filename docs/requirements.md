\# Library Management System – Requirements Document



\## 1. Introduction

The Library Management System (LMS) is a web-based application designed to manage library operations such as book cataloging, borrowing, returning, and user management. The system is built using Java Servlets, JSP, JDBC, and MySQL, following the MVC architecture.



This document lists the functional and non-functional requirements of the system.



---



\## 2. User Roles



\### 2.1 Admin

\- Full control over the system

\- Manages librarians and members

\- Manages books and categories

\- Views system-wide reports



\### 2.2 Librarian

\- Manages books (add, update, view)

\- Issues and returns books

\- Views issued-books reports



\### 2.3 Member

\- Searches books

\- Borrows and returns books

\- Views borrowed book history



---



\## 3. Functional Requirements



\### 3.1 Authentication

\- Users must be able to log in and log out.

\- The system must support role-based access control.

\- Unauthorized users must not access protected pages.



---



\### 3.2 Book Management

\- Admin and Librarian can add new books.

\- Admin and Librarian can update book details.

\- Admin can delete books.

\- Each book must track total copies and available copies.



---



\### 3.3 Search \& Browse

\- Users can search books by title, author, category, or ISBN.

\- Users can view a list of all available books.



---



\### 3.4 Borrow \& Return

\- Members can borrow books if copies are available.

\- Members can return borrowed books.

\- The system must update available copies automatically.



---



\### 3.5 User Management

\- Admin can add, update, and remove members.

\- Admin can manage librarian accounts.



---



\### 3.6 Reports

\- Admin and Librarian can view issued books.

\- Admin can view system statistics (total books, users).



---



\## 4. Non-Functional Requirements



\### 4.1 Performance

\- Search results should load within 2 seconds.

\- System should support multiple concurrent users.



---



\### 4.2 Security

\- SQL Injection must be prevented using PreparedStatements.

\- Passwords must be stored in hashed format.

\- Sessions must expire after inactivity.



---



\### 4.3 Usability

\- Simple and intuitive user interface.

\- Proper validation messages for forms.

\- Consistent layout across pages.



---



\### 4.4 Maintainability

\- System must follow MVC architecture.

\- Database logic must be separated using DAO pattern.

\- Code should be well-documented and modular.



---



\### 4.5 Compatibility

\- Must run on Apache Tomcat.

\- Must use MySQL database.

\- Must support modern web browsers.



---



\## 5. Constraints

\- Java 8 or higher

\- Apache Tomcat server

\- MySQL database

\- Eclipse IDE



---



\## 6. Assumptions

\- Users have basic knowledge of web usage.

\- System will be deployed initially on a local or small-scale server.



