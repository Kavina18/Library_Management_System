package com.library.service;

import com.library.dao.BorrowDAO;
import com.library.exception.*;
import com.library.model.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class LibraryService {
    private final BookService books=new BookService();
    private final MemberService members=new MemberService();
    private final BorrowDAO borrows=new BorrowDAO();

    public int borrowBook(int bookId,int memberId)throws Exception{
        Book b=books.getById(bookId);members.getById(memberId);
        if(b.getAvailableCopies()<=0)throw new BookUnavailableException("No available copies for "+b.getTitle());
        LocalDate today=LocalDate.now();
        return borrows.borrow(bookId,memberId,today,today.plusDays(14));
    }
    public long returnBook(int borrowId)throws Exception{
        BorrowRecord r=borrows.findActiveById(borrowId);
        if(r==null)throw new BorrowRecordNotFoundException("Active borrow not found: "+borrowId);
        LocalDate today=LocalDate.now();borrows.returnBook(borrowId,r.getBookId(),today);
        return Math.max(0,ChronoUnit.DAYS.between(r.getDueDate(),today));
    }
    public List<BorrowRecord> getAllBorrowRecords()throws SQLException{return borrows.findAll();}
    public List<BorrowRecord> getMemberHistory(int id)throws SQLException{return borrows.findByMember(id);}
}
