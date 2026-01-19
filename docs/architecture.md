# System Architecture
## Library Management System

---

## 1. Architectural Pattern

The application follows the **Model–View–Controller (MVC)** architecture.

- **Model**: Represents business entities such as Book, Loan, and User.
- **View**: JSP pages responsible for rendering UI.
- **Controller**: Servlets that handle HTTP requests and responses.

---

## 2. High-Level Architecture

Client (Browser)
   ↓
JSP Views
   ↓
Servlet Controllers
   ↓
Service Layer
   ↓
DAO Layer
   ↓
MySQL Database

---

## 3. Package Structure

```text
com.library
 ├── controller
 │    ├── auth
 │    ├── book
 │    ├── loan
 │    └── home
 ├── service
 │    ├── auth
 │    └── book
 ├── dao
 │    ├── book
 │    ├── loan
 │    └── user
 ├── model
 │    ├── Book
 │    ├── Loan
 │    └── User
 ├── filter
 │    ├── AuthFilter
 │    └── AuthorizationFilter
 └── util
      ├── DBConnectionUtil
      └── PasswordUtil
