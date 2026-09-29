package com.library.model;

public class Book {
    private int id;
    private String title, author, isbn, category;
    private int totalCopies, availableCopies;

    public Book() {}
    public Book(String title,String author,String isbn,String category,int totalCopies) {
        this.title=title; this.author=author; this.isbn=isbn; this.category=category;
        this.totalCopies=totalCopies; this.availableCopies=totalCopies;
    }
    public Book(int id,String title,String author,String isbn,String category,int totalCopies,int availableCopies) {
        this(title,author,isbn,category,totalCopies); this.id=id; this.availableCopies=availableCopies;
    }

    public int getId(){return id;} public void setId(int v){id=v;}
    public String getTitle(){return title;} public void setTitle(String v){title=v;}
    public String getAuthor(){return author;} public void setAuthor(String v){author=v;}
    public String getIsbn(){return isbn;} public void setIsbn(String v){isbn=v;}
    public String getCategory(){return category;} public void setCategory(String v){category=v;}
    public int getTotalCopies(){return totalCopies;} public void setTotalCopies(int v){totalCopies=v;}
    public int getAvailableCopies(){return availableCopies;} public void setAvailableCopies(int v){availableCopies=v;}

    public String toString(){
        return String.format("ID=%d | %s | %s | ISBN=%s | %s | Available=%d/%d",
            id,title,author,isbn,category,availableCopies,totalCopies);
    }
}
