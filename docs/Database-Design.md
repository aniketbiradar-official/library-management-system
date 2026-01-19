# Database Design
## Library Management System

---

## 1. Database Overview

Database Name: `library_db`

The database is designed to store books, users, and loan transactions.

---

## 2. Tables

### 2.1 users

| Column     | Type            | Description |
|------------|-----------------|-------------|
| id         | INT (PK)        | User ID |
| username   | VARCHAR(50)     | Unique username |
| password   | VARCHAR(255)    | Hashed password |
| role       | ENUM            | ADMIN / LIBRARIAN |
| created_at | TIMESTAMP       | Account creation time |

---

### 2.2 books

| Column            | Type            | Description |
|-------------------|-----------------|-------------|
| id                | INT (PK)        | Book ID |
| title             | VARCHAR(200)    | Book title |
| author            | VARCHAR(150)    | Author name |
| isbn              | VARCHAR(20)     | Unique ISBN |
| total_copies      | INT             | Total copies |
| available_copies  | INT             | Available copies |
| category           | VARCHAR(100)    | Book category |
| created_at         | TIMESTAMP       | Entry time |

---

### 2.3 book_loans

| Column        | Type        | Description |
|---------------|-------------|-------------|
| id            | INT (PK)    | Loan ID |
| book_id       | INT (FK)    | Reference to books |
| borrower_name | VARCHAR(100)| Borrower name |
| issue_date    | TIMESTAMP   | Issue time |
| return_date   | TIMESTAMP   | Return time |
| status        | ENUM        | ISSUED / RETURNED |

---

## 3. Relationships

- One book can have multiple loan records.
- `book_loans.book_id` references `books.id`.

---

## 4. Indexes & Constraints

- ISBN is unique
- Foreign key ensures data consistency
