# Library Management System – Architecture

## 1. Overview

This project follows a **classic layered MVC architecture** using **Java (Servlets + JSP)** for the web layer, **Service & DAO layers** for business logic and persistence, and **JDBC + MySQL** for data storage. The UI is built with JSP, CSS (Glass UI theme), JavaScript, and Chart.js for reports.

The architecture cleanly separates **presentation**, **controller**, **business logic**, and **data access**, making the system maintainable and extensible.

---

## 2. High-Level Architecture

```
Browser (Client)
   │
   ▼
JSP Views (UI Layer)
   │
   ▼
Servlet Controllers (Controller Layer)
   │
   ▼
Service Layer (Business Logic)
   │
   ▼
DAO Layer (Data Access)
   │
   ▼
MySQL Database
```

---

## 3. Package Structure (Backend)

### 3.1 Controller Layer (`com.library.controller.*`)

Handles HTTP requests, validates input, calls services, and forwards to JSP views.

* **auth**

  * `LoginController`
  * `LogoutController`
  * `RegisterController`

* **book**

  * `BookController`

* **loan**

  * `LoanController`

* **report**

  * `ReportController`

**Responsibilities**:

* URL mapping (`@WebServlet`)
* Request/response handling
* Session management
* Forwarding to JSP pages

---

### 3.2 Service Layer (`com.library.service.*`)

Contains business rules and application logic. Acts as a bridge between controllers and DAOs.

* **auth**

  * `AuthService`

* **book**

  * `BookService`

* **loan**

  * `LoanService`

* **report**

  * `ReportService`

* **user**

  * `UserService`

**Responsibilities**:

* Business validation
* Transaction coordination
* Aggregating data from multiple DAOs

---

### 3.3 DAO Layer (`com.library.dao.*`)

Handles all database interactions using JDBC.

* **book**

  * `BookDAO`

* **loan**

  * `LoanDAO`

* **report**

  * `ReportDAO`

* **user**

  * `UserDAO`

**Responsibilities**:

* SQL queries
* Mapping `ResultSet` → Model objects
* Isolating database logic from services

---

### 3.4 Model Layer (`com.library.model.*`)

Plain Java Objects (POJOs) representing domain entities.

* **book**

  * `Book`

* **loan**

  * `Loan`
  * `BookLoan`

* **user**

  * `User`

* **report**

  * `BookBorrowReport`
  * `IssuedBookReport`
  * `OverdueBookReport`
  * `MemberActivityReport`
  * `MonthlyBorrowReport`
  * `CategoryReport`

**Responsibilities**:

* Data representation
* Transporting data between layers

---

### 3.5 Filters & Listeners

* **Filters (****`com.library.filter`****)**

  * `AuthFilter` – Authentication check
  * `AuthorizationFilter` – Role-based access control

* **Listeners (****`com.library.listener`****)**

  * `MySQLCleanupListener` – Resource cleanup on shutdown

---

### 3.6 Utilities (`com.library.util`)

Reusable helper classes.

* `DBConnectionUtil` – JDBC connection management
* `PasswordUtil` – Password hashing
* `ValidationUtil` – Input validation

---

## 4. Frontend Structure (Web Layer)

### 4.1 Assets (`src/main/webapp/assets`)

* **CSS**

  * `base.css` – Reset & utilities
  * `layout.css` – Layout & structure
  * `theme.css` – Dark/Light theme & design system
  * `dashboard.css` – Charts & dashboard styling

* **JavaScript**

  * `theme.js` – Theme toggle
  * `ui.js` – UI interactions

---

### 4.2 JSP Views (`WEB-INF/views`)

#### Authentication

* `auth/login.jsp`
* `auth/register.jsp`

#### Books

* `book/book-list.jsp`
* `book/book-add.jsp`
* `book/book-edit.jsp`

#### Loans

* `loan/loan-list.jsp`

#### Reports

* `report/dashboard.jsp`
* `report/most-borrowed-books.jsp`
* `report/currently-issued-books.jsp`
* `report/overdue-books.jsp`
* `report/member-activity.jsp`
* `report/monthly-borrowing.jsp`
* `report/books-by-category.jsp`

#### Common

* `common/header.jsp`
* `common/head.jsp`

#### Errors

* `error/403.jsp`
* `error/404.jsp`
* `error/500.jsp`

**Note**: JSPs are protected under `WEB-INF` and accessed only via controllers.

---

## 5. Reporting & Charts Architecture

* **Chart Library**: Chart.js
* **Data Flow**:

```
Controller → ReportService → ReportDAO → DB
Controller → JSP → Chart.js (Canvas)
```

* Charts are **real, interactive canvases**, not images
* Medium-size responsive charts controlled via `dashboard.css`
* Hover, tooltip, and animation enabled via Chart.js defaults

---

## 6. Security Architecture

* Password hashing via `PasswordUtil`
* Session-based authentication
* Filters enforce:

  * Login required
  * Role-based authorization

---

## 7. Build & Deployment

* Java Web Application (WAR)
* Runs on **Apache Tomcat**
* Uses **MySQL** as database
* Standard `web.xml` configuration

---

## 8. Key Architectural Strengths

* Clear separation of concerns
* Scalable service & DAO layers
* Secure authentication & authorization
* Professional UI with reusable theme system
* Portfolio-grade reporting dashboard

---

## 9. Future Enhancements

* REST API layer
* Pagination & caching
* Export reports (PDF/CSV)
* Role-based dashboards
* Unit & integration tests

---

**Author**: Library Management System Project

**Aniket Biradar**