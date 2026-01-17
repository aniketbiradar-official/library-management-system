package com.library.model.loan;

import java.time.LocalDateTime;

public class Loan {

	private int id;
	private int bookId;
	private String borrowerName;
	private LocalDateTime issueDate;
	private LocalDateTime returnDate;
	private String status;
	private String bookTitle;
	
	public Loan() {
		super();
	}

	public Loan(int id, int bookId, String borrowerName, LocalDateTime issueDate, LocalDateTime returnDate,
			String status, String bookTitle) {
		super();
		this.id = id;
		this.bookId = bookId;
		this.borrowerName = borrowerName;
		this.issueDate = issueDate;
		this.returnDate = returnDate;
		this.status = status;
		this.bookTitle = bookTitle;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getBorrowerName() {
		return borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	public LocalDateTime getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(LocalDateTime issueDate) {
		this.issueDate = issueDate;
	}

	public LocalDateTime getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDateTime returnDate) {
		this.returnDate = returnDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getBookTitle() {
	    return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
	    this.bookTitle = bookTitle;
	}
	
	
}
