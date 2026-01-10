\# Software Requirements Specification (SRS)

\## Library Management System



---



\## 1. Introduction



\### 1.1 Purpose

This document specifies the software requirements for the Library Management System (LMS). It serves as a reference for developers, testers, and stakeholders involved in the project.



---



\### 1.2 Scope

The LMS is a Java-based web application that allows libraries to manage books, members, and borrowing operations efficiently using a centralized system.



---



\### 1.3 Definitions

\- LMS: Library Management System

\- DAO: Data Access Object

\- MVC: Model View Controller



---



\## 2. Overall Description



\### 2.1 Product Perspective

The system is a standalone web application developed using Java Servlets, JSP, JDBC, and MySQL. It follows MVC architecture to ensure separation of concerns.



---



\### 2.2 Product Functions

\- User authentication and authorization

\- Book catalog management

\- Borrowing and returning books

\- User management

\- Reporting and monitoring



---



\### 2.3 User Classes

| User		| Description 				|

|---------------|---------------------------------------|

| Admin 	| System administrator with full access |

| Librarian 	| Manages daily library operations 	|

| Member 	| End user who borrows books 		|



---



\### 2.4 Operating Environment

\- Server: Apache Tomcat

\- Database: MySQL

\- Client: Web browser

\- OS: Windows / Linux / macOS



---



\## 3. Functional Requirements



\### FR-01: User Login

\- The system shall authenticate users using username and password.

\- The system shall redirect users based on role.



---



\### FR-02: Book Management

\- The system shall allow adding, updating, and deleting books.

\- The system shall maintain book availability.



---



\### FR-03: Search Books

\- The system shall allow users to search books by multiple attributes.



---



\### FR-04: Borrow and Return

\- The system shall allow members to borrow available books.

\- The system shall update inventory on return.



---



\### FR-05: User Management

\- The system shall allow Admin to manage users.



---



\## 4. Non-Functional Requirements



\### NFR-01: Security

\- The system shall prevent SQL Injection.

\- The system shall enforce role-based access.



---



\### NFR-02: Performance

\- The system shall respond to requests within acceptable time limits.



---



\### NFR-03: Reliability

\- The system shall maintain data consistency during failures.



---



\### NFR-04: Maintainability

\- The system shall follow coding standards.

\- The system shall be easy to extend.



---



\## 5. External Interface Requirements



\### 5.1 User Interface

\- Web-based UI using HTML, CSS, and JSP.

\- Consistent header and footer across pages.



---



\### 5.2 Hardware Interfaces

\- None



---



\### 5.3 Software Interfaces

\- MySQL database

\- JDBC driver



---



\## 6. Constraints

\- Java 8+

\- Apache Tomcat

\- MySQL



---



\## 7. Future Enhancements

\- Email notifications

\- Fine calculation

\- REST API integration

\- Mobile application



---



\## 8. Approval

This SRS document serves as the baseline agreement for system development.



