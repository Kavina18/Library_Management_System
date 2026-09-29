package com.library.service;

import com.library.dao.BookDAO;
import com.library.exception.BookNotFoundException;
import com.library.model.Book;
import java.sql.SQLException;
import java.util.List;

public class BookService {
    private final BookDAO dao=new BookDAO();
    public int add(Book b)throws SQLException{validate(b);return dao.add(b);}
    public List<Book> getAll()throws SQLException{return dao.findAll();}
    public Book getById(int id)throws SQLException,BookNotFoundException{Book b=dao.findById(id);if(b==null)throw new BookNotFoundException("Book not found: "+id);return b;}
    public List<Book> search(String s)throws SQLException{return dao.search(s);}
    public void update(Book b)throws SQLException,BookNotFoundException{getById(b.getId());validate(b);dao.update(b);}
    public void delete(int id)throws SQLException,BookNotFoundException{getById(id);dao.delete(id);}
    private void validate(Book b){if(b.getTitle()==null||b.getTitle().isBlank())throw new IllegalArgumentException("Title required");if(b.getAuthor()==null||b.getAuthor().isBlank())throw new IllegalArgumentException("Author required");if(b.getIsbn()==null||b.getIsbn().isBlank())throw new IllegalArgumentException("ISBN required");if(b.getTotalCopies()<=0)throw new IllegalArgumentException("Copies must be > 0");}
}
