# API Routes Documentation — Library Management System

This document describes **all HTTP routes (endpoints)** exposed by the **Library Management System** web application.  
It covers **authentication**, **books**, **loans**, and **reports**, along with **role-based access control**.

---

## 1. General Information

- **Application Type:** Java Web Application (Servlet + JSP)
- **Base Context Path:** `/library-management-system-web`
- **Architecture:** MVC (Controller → Service → DAO)
- **Authentication:** Session-based
- **Authorization:** Role-based (`ADMIN`, `LIBRARIAN`, `MEMBER`)

---

## 2. Authentication Routes

### 2.1 Login

| Property 		| Value 				|
|---------------|-----------------------|
| URL 			| `/login` 				|
| Method 		| `GET` 				|
| Description 	| Displays login page 	|
| Access 		| Public 				|

| URL 			| `/login` 									|
| Method 		| `POST` 									|
| Description 	| Authenticates user and creates session 	|
| Access 		| Public 									|

---

### 2.2 Register

| Property 		| Value 						|
|---------------|-------------------------------|
| URL 			| `/register` 					|
| Method 		| `GET` 						|
| Description 	| Displays registration page 	|
| Access 		| Public 						|

| URL 			| `/register` 			|
| Method 		| `POST` 				|
| Description 	| Registers new MEMBER 	|
| Access 		| Public 				|

---

### 2.3 Logout

| Property 		| Value 				|
|---------------|-----------------------|
| URL 			| `/logout` 			|
| Method 		| `GET` 				|
| Description 	| Invalidates session 	|
| Access 		| Authenticated users 	|

---

## 3. Book Management Routes

### 3.1 List & Search Books

| Property 		| Value 										|
|---------------|-----------------------------------------------|
| URL 			| `/books` 										|
| Method 		| `GET` 										|
| Description 	| List, search, filter, sort, paginate books 	|
| Access 		| All roles 									|

**Query Parameters**
- `q` → search text
- `category`
- `availability`
- `sort`
- `order`
- `page`

---

### 3.2 Add Book

| Property 		| Value 				|
|---------------|-----------------------|
| URL 			| `/books/add` 			|
| Method 		| `GET` 				|
| Description 	| Show add book form 	|
| Access 		| `ADMIN` 				|

| URL 			| `/books/add` 			|
| Method 		| `POST` 				|
| Description 	| Add new book 			|
| Access 		| `ADMIN` 				|

---

### 3.3 Edit Book

| Property 		| Value 				|
|---------------|-----------------------|
| URL 			| `/books/edit` 		|
| Method 		| `GET` 				|
| Description 	| Show edit book form 	|
| Access 		| `ADMIN` 				|

| URL 			| `/books/edit` 		|
| Method 		| `POST` 				|
| Description 	| Update book 			|
| Access 		| `ADMIN` 				|

---

### 3.4 Delete Book

| Property 		| Value 			|
|---------------|-------------------|
| URL 			| `/books/delete` 	|
| Method 		| `GET` 			|
| Description 	| Delete book 		|
| Access 		| `ADMIN` 			|

---

## 4. Loan Management Routes

### 4.1 Borrow Book

| Property 		| Value 			|
|---------------|-------------------|
| URL 			| `/loans/borrow` 	|
| Method 		| `POST` 			|
| Description 	| Borrow a book 	|
| Access 		| `MEMBER` 			|

---

### 4.2 Return Book

| Property 		| Value 				|
|---------------|-----------------------|
| URL 			| `/loans/return` 		|
| Method 		| `POST` 				|
| Description 	| Return borrowed book 	|
| Access 		| `MEMBER`, `LIBRARIAN` |

---

### 4.3 View Loans

| Property 		| Value 								|
|---------------|---------------------------------------|
| URL 			| `/loans` 								|
| Method 		| `GET` 								|
| Description 	| View loans (own or all) 				|
| Access 		| `MEMBER` (own), `LIBRARIAN`, `ADMIN` 	|

---

## 5. Report Routes

All report routes are protected and accessible **only to `ADMIN` and `LIBRARIAN`**.

Base path:
```

/reports/*

```

---

### 5.1 Reports Dashboard

| Property 		| Value 									|
|---------------|-------------------------------------------|
| URL 			| `/reports/dashboard` 						|
| Method 		| `GET` 									|
| Description 	| Combined dashboard with charts & tables 	|
| Access 		| `ADMIN`, `LIBRARIAN` 						|

Includes:
- Most Borrowed Books (bar)
- Currently Issued Books (chart)
- Overdue Books (chart)
- Member Activity (bar)
- Monthly Trends (line)
- Books by Category (pie)

---

### 5.2 Most Borrowed Books

| Property 		| Value 				|
|---------------|-----------------------|
| URL 			| `/reports/books` 		|
| Method 		| `GET` 				|
| Description 	| Most borrowed books 	|
| Access 		| `ADMIN`, `LIBRARIAN` 	|

---

### 5.3 Currently Issued Books

| Property 		| Value 					|
|---------------|---------------------------|
| URL 			| `/reports/issued` 		|
| Method 		| `GET` 					|
| Description 	| Books currently issued 	|
| Access 		| `ADMIN`, `LIBRARIAN` 		|

---

### 5.4 Overdue Books

| Property 		| Value 				|
|---------------|-----------------------|
| URL 			| `/reports/overdue` 	|
| Method 		| `GET` 				|
| Description 	| Overdue books report 	|
| Access 		| `ADMIN`, `LIBRARIAN` 	|

---

### 5.5 Member Activity (Top Borrowers)

| Property 		| Value 							|
|---------------|-----------------------------------|
| URL 			| `/reports/members` 				|
| Method 		| `GET` 							|
| Description 	| Members with highest borrowing 	|
| Access 		| `ADMIN`, `LIBRARIAN` 				|

---

### 5.6 Monthly Borrowing Trends

| Property 		| Value 					|
|---------------|---------------------------|
| URL 			| `/reports/monthly` 		|
| Method 		| `GET` 					|
| Description 	| Monthly borrowing trends 	|
| Access 		| `ADMIN`, `LIBRARIAN` 		|

---

### 5.7 Books by Category

| Property 		| Value 						|
|---------------|-------------------------------|
| URL 			| `/reports/categories` 		|
| Method 		| `GET` 						|
| Description 	| Book distribution by category |
| Access 		| `ADMIN`, `LIBRARIAN` 			|

---

## 6. Error Routes

| URL 				| Description 	|
|-------------------|---------------|
| `/error/403.jsp` 	| Forbidden 	|
| `/error/404.jsp` 	| Not Found 	|
| `/error/500.jsp` 	| Server Error 	|

---

## 7. Security & Filters

### Filters Used
- `AuthFilter` → Session validation
- `AuthorizationFilter` → Role-based access

### Enforcement
- Unauthenticated users redirected to login
- Unauthorized access returns HTTP 403

---

## 8. Notes for Developers

- All routes are **Servlet-based**
- No REST API exposure (MVC Web App)
- Charts rendered using **Chart.js**
- Database access via DAO layer only

---

## 9. Future API Extensions

Planned enhancements:
- REST API (`/api/v1/*`)
- JSON responses for reports
- Token-based authentication
- Pagination in reports

---

## 10. Author & Project Info

- **Author:** Aniket Biradar  
- **Project:** Library Management System  
- **Technology:** Java, Servlets, JSP, MySQL, Chart.js  
- **Version:** 1.0  

---

