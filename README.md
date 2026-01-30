#  Library Management System  
**Java Web Application (Servlets • JSP • JDBC • MySQL • Chart.js)**

A full-featured **Library Management System** built using **pure Java web technologies** with a clean **MVC architecture**, role-based access control, and a comprehensive **reports & analytics dashboard**.

This project is designed to be **academically strong**, **interview-ready**, and **industry-aligned**, focusing on correctness, maintainability, and real-world use cases.

---

##  Key Highlights

-  Clean **MVC architecture**
-  Role-based access (**ADMIN / LIBRARIAN / MEMBER**)
-  Full CRUD for books & loans
-  Real-time availability tracking
-  Advanced **reporting with Chart.js**
-  Centralized **Reports Dashboard**
-  Secure authentication & authorization
-  Production-ready project structure

---

##  User Roles & Capabilities

```
| Feature 					| Admin | Librarian | Member |
|---------------------------|-------|-----------|--------|
| Login / Logout 			| ✅ 	| ✅ 		| ✅ 	 |
| View Books 				| ✅ 	| ✅ 		| ✅ 	 |
| Add / Edit / Delete Books | ✅ 	| ❌ 		| ❌ 	 |
| Borrow Books 				| ❌ 	| ❌ 		| ✅ 	 |
| View Own Loans 			| ❌ 	| ❌ 		| ✅ 	 |
| Reports & Analytics 		| ✅ 	| ✅ 		| ❌ 	 |
| Dashboard Access 			| ✅ 	| ✅ 		| ❌ 	 |
```

---

##  Core Modules

###  Authentication & Security
- Secure login & registration
- Password hashing
- Authentication filter
- Authorization filter (role-based routing)
- Custom error pages (403 / 404 / 500)

---

###  Book Management
- Add new books (Admin only)
- Edit / delete books
- Search by title or author
- Filter by category & availability
- Sorting (title, author, category)
- Pagination
- Real-time availability count

---

###  Loan Management
- Members can borrow available books
- Prevent borrowing when copies are unavailable
- Track issue date
- Calculate overdue days
- View active & historical loans

---

##  Reports & Analytics (Major Feature)

All reports are powered by **optimized MySQL queries** and visualized using **Chart.js**.

###  Individual Reports

```
| Report 					| Description 					| Chart |
|---------------------------|-------------------------------|-------|
| Most Borrowed Books 		| Popular books by borrow count | Bar   |
| Currently Issued Books 	| Active issued books 			| Bar   |
| Overdue Books 			| Overdue loans 				| Bar   |
| Member Activity 			| Top borrowers 				| Bar   |
| Monthly Borrowing Trends 	| Borrowing over time 			| Line  |
| Books by Category 		| Category distribution 		| Pie   |
```

---

###  Reports Dashboard

A **single consolidated dashboard** combining all analytics:

- Most Borrowed Books (Bar)
- Currently Issued Books (Bar)
- Overdue Books (Bar)
- Member Activity (Bar)
- Monthly Borrowing Trends (Line)
- Books by Category (Pie)

 URL:
```
/reports/dashboard
```

---

##  Architecture Overview (MVC)

```
src/main/java
├── controller
│ ├── auth
│ ├── book
│ ├── loan
│ └── report
│
├── service
│ ├── auth
│ ├── book
│ ├── loan
│ └── report
│
├── dao
│ ├── book
│ ├── loan
│ ├── report
│ └── user
│
├── model
│ ├── book
│ ├── loan
│ ├── report
│ └── user
│
├── filter
│ ├── AuthFilter
│ └── AuthorizationFilter
│
├── listener
│ └── MySQLCleanupListener
│
└── util
├── DBConnectionUtil
├── PasswordUtil
└── ValidationUtil
```


---

##  Frontend Structure (JSP)

```
WEB-INF/views
├── auth
│ ├── login.jsp
│ └── register.jsp
├── book
│ ├── book-list.jsp
│ ├── book-add.jsp
│ └── book-edit.jsp
├── loan
│ └── loan-list.jsp
├── report
│ ├── dashboard.jsp
│ ├── most-borrowed-books.jsp
│ ├── currently-issued-books.jsp
│ ├── overdue-books.jsp
│ ├── member-activity.jsp
│ ├── monthly-borrowing.jsp
│ └── books-by-category.jsp
├── common
│ ├── head.jsp
│ └── header.jsp
└── error
├── 403.jsp
├── 404.jsp
└── 500.jsp
```


---

##  UI & Assets

```
assets/
├── css
│ ├── base.css
│ ├── layout.css
│ ├── dashboard.css
│ └── theme.css
└── js
├── ui.js
└── theme.js
```


- Clean JSP UI
- Centralized header & layout
- Chart rendering via **Chart.js CDN**
- Scalable CSS structure for future theming

---

## ️ Database Schema (MySQL)

### Core Tables
- `users`
- `books`
- `book_loans`

### Reporting Queries Use
- `COUNT(*)`
- `GROUP BY`
- `MONTH(issue_date)`
- `YEAR(issue_date)`
- `DATEDIFF(CURDATE(), issue_date)`
- Optimized joins for dashboards

---

## ️ Tech Stack

```
| Layer 	| Technology 			|
|-----------|-----------------------|
| Backend 	| Java, Servlets 		|
| Frontend 	| JSP, JSTL 			|
| Database 	| MySQL 				|
| Charts 	| Chart.js 				|
| Server 	| Apache Tomcat 		|
| Build 	| Dynamic Web Project 	|
| IDE 		| Eclipse 				|
```

---

## ️ How to Run the Project

1. Clone the repository  
2. Import into **Eclipse** as *Dynamic Web Project*  
3. Configure MySQL database  
4. Update credentials in `DBConnectionUtil.java`  
5. Deploy on **Apache Tomcat**  
6. Open in browser:
```
http://localhost:8080/library-management-system-web
```


---

##  Engineering Strengths

- Zero business logic in JSP
- Strong separation of concerns
- Reusable report models
- Service-driven architecture
- Prevents MySQL connection leaks
- Scalable reporting design

---

##  Future Enhancements

- PDF / Excel export for reports
- REST API version
- AJAX-based charts
- Email notifications for overdue books
- Spring Boot migration
- Role-specific dashboards

---

##  Author

**Aniket Biradar**  
Java Backend & Full-Stack Developer  

Focused on:
- Clean architecture  
- Data-driven dashboards  
- Enterprise-grade Java applications  

---

⭐ *If you find this project useful, feel free to star the repository.*
