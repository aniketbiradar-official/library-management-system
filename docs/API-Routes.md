# API Routes
## Library Management System

---

## Authentication

| Method | URL       | Description |
|------|-----------|-------------|
| POST | /login    | User login |
| GET  | /logout   | User logout |

---

## Books

| Method | URL              | Description |
|-------|------------------|-------------|
| GET   | /books           | List all books |
| GET   | /books/add       | Show add book form |
| POST  | /books/add       | Add new book |
| GET   | /books/edit      | Show edit book form |
| POST  | /books/edit      | Update book |
| GET   | /books/delete    | Delete book |

---

## Loans

| Method | URL          | Description |
|-------|--------------|-------------|
| GET   | /loans/list  | View issued books |
| POST  | /loans       | Issue or return book |
