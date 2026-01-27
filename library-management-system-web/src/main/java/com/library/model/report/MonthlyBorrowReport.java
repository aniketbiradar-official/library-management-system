package com.library.model.report;

public class MonthlyBorrowReport {

    private int year;
    private int month;
    private long totalBorrowed;

    // ===== Getters & Setters =====
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public long getTotalBorrowed() {
        return totalBorrowed;
    }

    public void setTotalBorrowed(long totalBorrowed) {
        this.totalBorrowed = totalBorrowed;
    }
}
