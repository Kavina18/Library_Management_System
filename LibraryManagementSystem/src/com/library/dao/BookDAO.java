package com.library.dao;

import com.library.config.DBConnection;
import com.library.model.Book;
import java.sql.*;
import java.util.*;

public class BookDAO {
    public int add(Book b) throws SQLException {
        String sql="INSERT INTO books(title,author,isbn,category,total_copies,available_copies) VALUES(?,?,?,?,?,?)";
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            p.setString(1,b.getTitle());p.setString(2,b.getAuthor());p.setString(3,b.getIsbn());p.setString(4,b.getCategory());
            p.setInt(5,b.getTotalCopies());p.setInt(6,b.getAvailableCopies());p.executeUpdate();
            try(ResultSet r=p.getGeneratedKeys()){return r.next()?r.getInt(1):0;}
        }
    }
    public List<Book> findAll() throws SQLException {
        List<Book> list=new ArrayList<>();
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("SELECT * FROM books ORDER BY id");ResultSet r=p.executeQuery()){
            while(r.next())list.add(map(r));
        } return list;
    }
    public Book findById(int id) throws SQLException {
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("SELECT * FROM books WHERE id=?")){
            p.setInt(1,id);try(ResultSet r=p.executeQuery()){return r.next()?map(r):null;}
        }
    }
    public List<Book> search(String key) throws SQLException {
        List<Book> list=new ArrayList<>(); String sql="SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR category LIKE ? ORDER BY title";
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){
            String k="%"+key+"%";p.setString(1,k);p.setString(2,k);p.setString(3,k);
            try(ResultSet r=p.executeQuery()){while(r.next())list.add(map(r));}
        } return list;
    }
    public void update(Book b) throws SQLException {
        String sql="UPDATE books SET title=?,author=?,isbn=?,category=?,total_copies=?,available_copies=? WHERE id=?";
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,b.getTitle());p.setString(2,b.getAuthor());p.setString(3,b.getIsbn());p.setString(4,b.getCategory());
            p.setInt(5,b.getTotalCopies());p.setInt(6,b.getAvailableCopies());p.setInt(7,b.getId());p.executeUpdate();
        }
    }
    public void delete(int id)throws SQLException{
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("DELETE FROM books WHERE id=?")){
            p.setInt(1,id);p.executeUpdate();
        }
    }
    public void changeAvailability(Connection c,int id,int delta)throws SQLException{
        String sql="UPDATE books SET available_copies=available_copies+? WHERE id=? AND available_copies+? BETWEEN 0 AND total_copies";
        try(PreparedStatement p=c.prepareStatement(sql)){p.setInt(1,delta);p.setInt(2,id);p.setInt(3,delta);if(p.executeUpdate()==0)throw new SQLException("Unable to update availability.");}
    }
    private Book map(ResultSet r)throws SQLException{
        return new Book(r.getInt("id"),r.getString("title"),r.getString("author"),r.getString("isbn"),r.getString("category"),r.getInt("total_copies"),r.getInt("available_copies"));
    }
}
