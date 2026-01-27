package com.library.service.report;

import java.util.List;

import com.library.dao.report.ReportDAO;
import com.library.model.report.BookBorrowReport;

/**
 * Service layer for reports
 */
public class ReportService {

    private final ReportDAO reportDAO = new ReportDAO();

    public List<BookBorrowReport> getMostBorrowedBooks() {
        return reportDAO.getMostBorrowedBooks();
    }
}
