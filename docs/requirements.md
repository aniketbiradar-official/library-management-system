# Requirements Specification — Library Management System

This document defines the **complete functional and non-functional requirements** for the **Library Management System (LMS)**.  
It serves as a formal reference for **development, testing, evaluation, and future enhancement**.

---

## 1. Project Overview

### 1.1 Project Name
**Library Management System**

### 1.2 Objective
To design and implement a **secure, scalable, role-based library system** that allows:
- Managing books and inventory
- Handling borrowing and returns
- Tracking users and roles
- Generating analytical reports and dashboards

---

## 2. Stakeholders

| Stakeholder 				| Responsibility 			|
|---------------------------|---------------------------|
| Admin 					| Full system control 		|
| Librarian 				| Manage loans & reports 	|
| Member 					| Borrow and return books 	|
| Developer 				| Build & maintain system 	|
| Instructor / Evaluator 	| Review system 			|

---

## 3. User Roles & Permissions

| Role 			| Permissions 										|
|---------------|---------------------------------------------------|
| **ADMIN** 	| Full access (books, users, reports, dashboard) 	|
| **LIBRARIAN** | Loan management, reports, dashboard 				|
| **MEMBER** 	| Browse books, borrow/return, view own loans 		|

---

## 4. Functional Requirements

### 4.1 Authentication & Authorization

- Users **must register and log in**
- Passwords **must be securely hashed**
- System **must enforce role-based access**
- Sessions **must be validated on every request**

---

### 4.2 Book Management

#### 4.2.1 View Books
- Users can view books in a paginated list
- Books can be searched by title or author
- Filters by category and availability supported
- Sorting supported on title, author, category

#### 4.2.2 Add Book
- Only ADMIN can add books
- Available copies must initialize equal to total copies

#### 4.2.3 Edit Book
- Only ADMIN can edit book details

#### 4.2.4 Delete Book
- Only ADMIN can delete books
- Confirmation required before deletion

---

### 4.3 Loan Management

#### 4.3.1 Borrow Book
- Only MEMBERS can borrow books
- Book must have available copies
- Available copies must decrease on issue

#### 4.3.2 Return Book
- Members and Librarians can return books
- Return date must be recorded
- Available copies must increase

#### 4.3.3 View Loans
- Members can view their own loans
- Librarians and Admins can view all loans

---

### 4.4 Reporting & Analytics

The system **must generate real-time reports** using database queries.

#### Mandatory Reports:
1. Most Borrowed Books
2. Currently Issued Books
3. Overdue Books
4. Member Activity (Top Borrowers)
5. Monthly Borrowing Trends
6. Books by Category

Each report must:
- Be accessible only to ADMIN and LIBRARIAN
- Support graphical visualization (charts)

---

### 4.5 Reports Dashboard

- A unified dashboard must display:
  - Bar charts
  - Line charts
  - Pie charts
  - Summary tables
- Dashboard must present reports in logical order
- Charts must render without page scrolling

---

## 5. Non-Functional Requirements

### 5.1 Performance
- Page load time < 2 seconds
- Reports should execute efficiently using indexed queries

### 5.2 Security
- Passwords stored using BCrypt hashing
- No direct database exposure
- Role-based filters must block unauthorized access

### 5.3 Scalability
- Design must support adding new reports
- Database schema must support growth

### 5.4 Reliability
- System must handle invalid input gracefully
- Errors must redirect to appropriate error pages

### 5.5 Maintainability
- MVC architecture must be followed
- Clear separation of Controller, Service, DAO layers

---

## 6. Technical Requirements

### 6.1 Technology Stack

| Layer 	| Technology 			|
|-----------|-----------------------|
| Frontend 	| JSP, HTML, Chart.js 	|
| Backend 	| Java Servlets 		|
| Database 	| MySQL 8.0 			|
| Security 	| Session-based auth 	|
| Server 	| Apache Tomcat 9 		|

---

## 7. Constraints

- Must run on Java 8+
- Must use MySQL
- No external frameworks (Spring, Hibernate)
- Servlet-based architecture only

---

## 8. Assumptions

- Users have basic browser access
- Database is locally hosted
- Admin creates initial system data

---

## 9. Future Enhancements (Out of Scope)

- REST API layer
- Mobile application
- Fine & penalty payments
- Book reservation system
- Email notifications

---

## 10. Acceptance Criteria

The system is considered complete when:
- All roles function correctly
- Reports render accurately
- Dashboard displays all charts
- No unauthorized access possible
- All CRUD operations are stable

---

## 11. Author & Project Info

- **Author:** Aniket Biradar  
- **Project:** Library Management System  
- **Document Type:** Requirements Specification  
- **Version:** 1.0  

---
