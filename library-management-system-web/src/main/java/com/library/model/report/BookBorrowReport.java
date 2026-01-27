package com.library.model.report;

/**
 * DTO for Most Borrowed Books report
 * Represents aggregated data, not a database entity
 */
public class BookBorrowReport {

    private String bookTitle;
    private int borrowCount;

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public int getBorrowCount() {
        return borrowCount;
    }

    public void setBorrowCount(int borrowCount) {
        this.borrowCount = borrowCount;
    }
}
