# Software Requirements Specification (SRS)
## Library Management System

---

## 1. Introduction

### 1.1 Purpose
This document provides a **complete Software Requirements Specification (SRS)** for the **Library Management System (LMS)**.  
It defines **functional requirements, non-functional requirements, system behavior, constraints, and acceptance criteria**.

This SRS is intended for:
- Developers
- Testers
- Evaluators / Instructors
- Future maintainers

---

### 1.2 Scope
The Library Management System is a **web-based, role-driven application** that enables:

- Book inventory management
- User authentication and authorization
- Book borrowing and returning
- Reporting and analytics with charts
- Administrative control and monitoring

The system is designed using **Java Servlets, JSP, MySQL**, and follows **MVC architecture**.

---

### 1.3 Definitions, Acronyms, Abbreviations

| Term | Meaning |
|----|--------|
| LMS | Library Management System |
| ADMIN | System administrator |
| LIBRARIAN | Loan & report manager |
| MEMBER | End user who borrows books |
| DAO | Data Access Object |
| MVC | Model View Controller |
| SRS | Software Requirements Specification |

---

## 2. Overall Description

### 2.1 Product Perspective
The LMS is a **standalone web application** deployed on **Apache Tomcat**, backed by a **MySQL relational database**.

It does not rely on third-party frameworks (Spring/Hibernate) and uses:
- Java Servlets
- JSP
- JDBC
- Chart.js for analytics

---

### 2.2 Product Functions

High-level system functions include:

- User registration and login
- Role-based access control
- Book CRUD operations
- Borrowing and returning workflow
- Report generation
- Dashboard visualization

---

### 2.3 User Classes and Characteristics

| User Class | Description |
|----------|------------|
| ADMIN | Full system access |
| LIBRARIAN | Manages loans and reports |
| MEMBER | Borrows and returns books |

---

### 2.4 Operating Environment

| Component | Specification |
|--------|---------------|
| OS | Windows / Linux |
| Server | Apache Tomcat 9 |
| Language | Java 8+ |
| Database | MySQL 8.0 |
| Browser | Chrome / Firefox |

---

### 2.5 Design Constraints

- Servlet-based architecture only
- No Spring / Hibernate
- MySQL mandatory
- JSP-based frontend
- Session-based authentication

---

### 2.6 Assumptions & Dependencies

- Database server is running
- Admin creates initial users/books
- Internet required for Chart.js CDN

---

## 3. System Features & Functional Requirements

---

### 3.1 Authentication & Authorization

#### 3.1.1 Login
- Users must authenticate using username and password
- Invalid credentials must be rejected
- Sessions must be created after login

#### 3.1.2 Registration
- Members can self-register
- Password must be securely hashed

#### 3.1.3 Authorization
- Role-based access enforced on all routes
- Unauthorized access must return HTTP 403

---

### 3.2 Book Management

#### 3.2.1 View Books
- Paginated list
- Search by title/author
- Filter by category & availability
- Sort by title, author, category

#### 3.2.2 Add Book
- Only ADMIN allowed
- Total copies must be positive
- ISBN must be unique

#### 3.2.3 Edit Book
- Only ADMIN allowed
- Cannot reduce total copies below issued copies

#### 3.2.4 Delete Book
- Only ADMIN allowed
- Confirmation required

---

### 3.3 Loan Management

#### 3.3.1 Borrow Book
- Only MEMBER allowed
- Book must be available
- Available copies must decrement

#### 3.3.2 Return Book
- MEMBER and LIBRARIAN allowed
- Return date recorded
- Available copies incremented

#### 3.3.3 View Loans
- MEMBER: own loans
- ADMIN/LIBRARIAN: all loans

---

### 3.4 Reports & Analytics

The system must support the following reports:

| Report | Description |
|------|-------------|
| Most Borrowed Books | Top borrowed titles |
| Currently Issued | Active loans |
| Overdue Books | Loans beyond due date |
| Member Activity | Top borrowers |
| Monthly Trends | Borrowing per month |
| Books by Category | Distribution of books |

All reports must:
- Be role-restricted
- Support graphical visualization

---

### 3.5 Dashboard

- Unified reports dashboard
- Displays multiple charts:
  - Bar charts
  - Line charts
  - Pie charts
- Charts must render without scrolling
- Data must be real-time

---

## 4. External Interface Requirements

### 4.1 User Interface
- JSP-based pages
- Header navigation by role
- Tables for data
- Charts using Chart.js

---

### 4.2 Hardware Interface
- No special hardware required

---

### 4.3 Software Interface
- JDBC for database access
- Chart.js for visualization

---

### 4.4 Communication Interface
- HTTP over TCP/IP
- Session-based communication

---

## 5. Non-Functional Requirements

### 5.1 Performance
- Page response < 2 seconds
- Optimized SQL queries with indexing

---

### 5.2 Security
- BCrypt password hashing
- Session validation
- SQL injection prevention
- Role-based route protection

---

### 5.3 Reliability
- Graceful error handling
- Transaction-safe database operations

---

### 5.4 Maintainability
- Layered architecture (Controller, Service, DAO)
- Clear separation of concerns
- Reusable components

---

### 5.5 Scalability
- Easily add new reports
- Support increasing data volume

---

## 6. Data Requirements

- Relational schema (3NF)
- Foreign key constraints
- ENUM usage for controlled values
- Audit-ready transactional data

---

## 7. Use Case Summary

| Use Case | Actor |
|--------|-------|
| Login | All users |
| Register | Member |
| Add/Edit/Delete Book | Admin |
| Borrow/Return Book | Member |
| View Reports | Admin/Librarian |
| View Dashboard | Admin/Librarian |

---

## 8. Acceptance Criteria

The system is accepted if:

- All roles function correctly
- Reports are accurate
- Dashboard loads fully
- No unauthorized access
- CRUD operations stable

---

## 9. Future Enhancements

- Fine & penalty system
- Book reservations
- Email notifications
- REST API
- Mobile application

---

## 10. Author & Document Info

- **Author:** Aniket Biradar  
- **Project:** Library Management System  
- **Document:** Software Requirements Specification  
- **Version:** 1.0  

---
