# Database Design — Library Management System

This document provides a **complete and professional description of the database design** used in the **Library Management System**.  
It is based on the actual MySQL schema you implemented and validated through live data.

---

## 1. Database Overview

- **Database Name:** `library_db`
- **Database Engine:** MySQL 8.0
- **Design Paradigm:** Relational Database (3NF)
- **Primary Goals:**
  - Manage books and inventory
  - Track book borrowing and returning
  - Maintain role-based users
  - Support reporting, analytics, and dashboards

---

## 2. Tables Summary

| Table Name   | Description |
|-------------|------------|
| `books`     | Stores book details and inventory |
| `users`     | Stores system users with roles |
| `book_loans`| Tracks book issue/return transactions |

---

## 3. Table: `books`

Stores all book metadata and inventory status.

### Schema

```sql
CREATE TABLE books (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    author VARCHAR(150) NOT NULL,
    isbn VARCHAR(20) UNIQUE,
    total_copies INT NOT NULL,
    available_copies INT NOT NULL,
    category VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
````

### Columns Explained

| Column             | Description                     |
| ------------------ | ------------------------------- |
| `id`               | Unique identifier for each book |
| `title`            | Book title                      |
| `author`           | Author name                     |
| `isbn`             | Unique ISBN number              |
| `total_copies`     | Total copies owned by library   |
| `available_copies` | Copies currently available      |
| `category`         | Book category/genre             |
| `created_at`       | Record creation timestamp       |

### Design Notes

* `available_copies` is dynamically updated on issue/return.
* ISBN is unique but optional.
* Category is flexible (not normalized yet).

---

## 4. Table: `users`

Stores all application users with role-based access.

### Schema

```sql
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN','LIBRARIAN','MEMBER') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Columns Explained

| Column       | Description            |
| ------------ | ---------------------- |
| `id`         | User identifier        |
| `username`   | Login username         |
| `password`   | BCrypt hashed password |
| `role`       | User role              |
| `created_at` | Account creation time  |

### User Roles

| Role        | Permissions             |
| ----------- | ----------------------- |
| `ADMIN`     | Full system access      |
| `LIBRARIAN` | Manage loans & reports  |
| `MEMBER`    | Borrow and return books |

---

## 5. Table: `book_loans`

Tracks the lifecycle of book borrowing.

### Schema

```sql
CREATE TABLE book_loans (
    id INT PRIMARY KEY AUTO_INCREMENT,
    book_id INT NOT NULL,
    user_id INT NOT NULL,
    issue_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    return_date TIMESTAMP NULL,
    status ENUM('ISSUED','RETURNED') DEFAULT 'ISSUED',

    FOREIGN KEY (book_id) REFERENCES books(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

### Columns Explained

| Column        | Description                     |
| ------------- | ------------------------------- |
| `id`          | Loan record ID                  |
| `book_id`     | Reference to `books.id`         |
| `user_id`     | Reference to `users.id`         |
| `issue_date`  | Date of issue                   |
| `return_date` | Date of return (NULL if issued) |
| `status`      | Loan status                     |

### Design Notes

* `status='ISSUED'` + `return_date IS NULL` → currently issued
* Supports overdue calculation using date difference
* Historical data preserved

---

## 6. Entity Relationships

```
USERS (1) ───────< BOOK_LOANS >─────── (1) BOOKS
```

### Relationship Explanation

* One user can have many loan records
* One book can have many loan records
* Ensures full borrowing history

---

## 7. Indexing Strategy

| Table        | Indexed Columns                |
| ------------ | ------------------------------ |
| `books`      | `id`, `isbn`, `category`       |
| `users`      | `id`, `username`               |
| `book_loans` | `book_id`, `user_id`, `status` |

Indexes improve:

* Search & filtering
* Report generation
* Chart performance

---

## 8. Reporting & Analytics Support

The schema directly supports:

*  Most Borrowed Books
*  Currently Issued Books
*  Overdue Books
*  Member Activity (Top Borrowers)
*  Monthly Borrowing Trends
*  Books by Category

All reports are generated using aggregate SQL queries on `book_loans`.

---

## 9. Data Integrity & Security

### Integrity

* Foreign keys enforce referential integrity
* ENUMs restrict invalid states
* No destructive deletes on transactional data

### Security

* Passwords stored using BCrypt hashing
* Role-based access enforced at application layer
* No direct database exposure to UI

---

## 10. Future Enhancements

Planned improvements:

* `categories` master table
* `fine_payments` for overdue penalties
* `audit_logs` for admin actions
* Soft delete flags (`is_active`)
* Book reservation support

---

## 11. Conclusion

This database design is:

 Normalized
 Scalable
 Reporting-friendly
 Enterprise-ready

It cleanly separates **data**, **transactions**, and **analytics**, making it suitable for real-world production use.

---

**Author:** Aniket Biradar
**Database:** MySQL 8.0
**Project:** Library Management System
