package com.library.model;

import java.time.LocalDate;

public class BorrowRecord {
    private int id,bookId,memberId;
    private String bookTitle,memberName,status;
    private LocalDate borrowDate,dueDate,returnDate;

    public int getId(){return id;} public void setId(int v){id=v;}
    public int getBookId(){return bookId;} public void setBookId(int v){bookId=v;}
    public int getMemberId(){return memberId;} public void setMemberId(int v){memberId=v;}
    public String getBookTitle(){return bookTitle;} public void setBookTitle(String v){bookTitle=v;}
    public String getMemberName(){return memberName;} public void setMemberName(String v){memberName=v;}
    public LocalDate getBorrowDate(){return borrowDate;} public void setBorrowDate(LocalDate v){borrowDate=v;}
    public LocalDate getDueDate(){return dueDate;} public void setDueDate(LocalDate v){dueDate=v;}
    public LocalDate getReturnDate(){return returnDate;} public void setReturnDate(LocalDate v){returnDate=v;}
    public String getStatus(){return status;} public void setStatus(String v){status=v;}

    public String toString(){
        return String.format("ID=%d | Book=%s | Member=%s | Borrowed=%s | Due=%s | Returned=%s | Status=%s",
            id,bookTitle,memberName,borrowDate,dueDate,returnDate,status);
    }
}
