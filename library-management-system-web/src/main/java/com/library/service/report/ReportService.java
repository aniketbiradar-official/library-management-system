package com.library.service.report;

import java.util.List;

import com.library.dao.report.ReportDAO;
import com.library.model.report.IssuedBookReport;
import com.library.model.report.OverdueBookReport;

public class ReportService {

    private final ReportDAO reportDAO = new ReportDAO();

    // ================= REPORT 1 =================
    public List<?> getMostBorrowedBooks() {
        return reportDAO.getMostBorrowedBooks();
    }

    // ================= REPORT 2 (FIX) =================
    public List<IssuedBookReport> getCurrentlyIssuedBooks() {
        return reportDAO.getCurrentlyIssuedBooks();
    }
    
    public List<OverdueBookReport> getOverdueBooks() {
        return reportDAO.getOverdueBooks();
    }

}
