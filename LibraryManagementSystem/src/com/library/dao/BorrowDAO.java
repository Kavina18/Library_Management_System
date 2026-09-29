package com.library.dao;

import com.library.config.DBConnection;
import com.library.model.BorrowRecord;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class BorrowDAO {
    public int borrow(int bookId,int memberId,LocalDate borrow,LocalDate due)throws SQLException{
        try(Connection c=DBConnection.getConnection()){
            c.setAutoCommit(false);
            try{
                new BookDAO().changeAvailability(c,bookId,-1);
                String sql="INSERT INTO borrow_records(book_id,member_id,borrow_date,due_date,status) VALUES(?,?,?,?, 'BORROWED')";
                try(PreparedStatement p=c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
                    p.setInt(1,bookId);p.setInt(2,memberId);p.setDate(3,java.sql.Date.valueOf(borrow));p.setDate(4,java.sql.Date.valueOf(due));p.executeUpdate();
                    c.commit();try(ResultSet r=p.getGeneratedKeys()){return r.next()?r.getInt(1):0;}
                }
            }catch(SQLException e){c.rollback();throw e;}finally{c.setAutoCommit(true);}
        }
    }
    public BorrowRecord findActiveById(int id)throws SQLException{
        String sql="SELECT br.*,b.title book_title,m.name member_name FROM borrow_records br JOIN books b ON b.id=br.book_id JOIN members m ON m.id=br.member_id WHERE br.id=? AND br.status='BORROWED'";
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){
            p.setInt(1,id);try(ResultSet r=p.executeQuery()){return r.next()?map(r):null;}
        }
    }
    public void returnBook(int borrowId,int bookId,LocalDate date)throws SQLException{
        try(Connection c=DBConnection.getConnection()){
            c.setAutoCommit(false);
            try{
                new BookDAO().changeAvailability(c,bookId,1);
                try(PreparedStatement p=c.prepareStatement("UPDATE borrow_records SET return_date=?,status='RETURNED' WHERE id=? AND status='BORROWED'")){
                    p.setDate(1,java.sql.Date.valueOf(date));p.setInt(2,borrowId);if(p.executeUpdate()==0)throw new SQLException("Record already returned.");
                }
                c.commit();
            }catch(SQLException e){c.rollback();throw e;}finally{c.setAutoCommit(true);}
        }
    }
    public List<BorrowRecord> findAll()throws SQLException{
        return query("SELECT br.*,b.title book_title,m.name member_name FROM borrow_records br JOIN books b ON b.id=br.book_id JOIN members m ON m.id=br.member_id ORDER BY br.id DESC");
    }
    public List<BorrowRecord> findByMember(int id)throws SQLException{
        String sql="SELECT br.*,b.title book_title,m.name member_name FROM borrow_records br JOIN books b ON b.id=br.book_id JOIN members m ON m.id=br.member_id WHERE br.member_id=? ORDER BY br.id DESC";
        List<BorrowRecord> list=new ArrayList<>();
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){p.setInt(1,id);try(ResultSet r=p.executeQuery()){while(r.next())list.add(map(r));}}
        return list;
    }
    private List<BorrowRecord> query(String sql)throws SQLException{
        List<BorrowRecord> list=new ArrayList<>();
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql);ResultSet r=p.executeQuery()){while(r.next())list.add(map(r));}
        return list;
    }
    private BorrowRecord map(ResultSet r)throws SQLException{
        BorrowRecord b=new BorrowRecord();b.setId(r.getInt("id"));b.setBookId(r.getInt("book_id"));b.setMemberId(r.getInt("member_id"));
        b.setBookTitle(r.getString("book_title"));b.setMemberName(r.getString("member_name"));b.setBorrowDate(r.getDate("borrow_date").toLocalDate());
        b.setDueDate(r.getDate("due_date").toLocalDate());java.sql.Date d=r.getDate("return_date");b.setReturnDate(d==null?null:d.toLocalDate());b.setStatus(r.getString("status"));return b;
    }
}
