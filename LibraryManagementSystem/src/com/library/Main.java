package com.library;

import com.library.model.*;
import com.library.service.*;
import com.library.util.InputUtil;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final Scanner SC=new Scanner(System.in);
    private static final BookService BOOKS=new BookService();
    private static final MemberService MEMBERS=new MemberService();
    private static final LibraryService LIBRARY=new LibraryService();

    public static void main(String[] args){
        System.out.println("========================================");
        System.out.println("       LIBRARY MANAGEMENT SYSTEM");
        System.out.println("========================================");
        while(true){
            try{
                System.out.println("\n1. Book Management\n2. Member Management\n3. Borrow / Return\n4. Reports\n0. Exit");
                int c=InputUtil.readInt(SC,"Choose: ");
                switch(c){case 1->bookMenu();case 2->memberMenu();case 3->borrowMenu();case 4->reports();case 0->{System.out.println("Goodbye!");return;}default->System.out.println("Invalid option.");}
            }catch(Exception e){System.out.println("Error: "+e.getMessage());}
        }
    }

    private static void bookMenu()throws Exception{
        System.out.println("\n--- Books ---\n1. Add\n2. List\n3. Search\n4. Update\n5. Delete");
        switch(InputUtil.readInt(SC,"Choose: ")){
            case 1->addBook();case 2->BOOKS.getAll().forEach(System.out::println);
            case 3->BOOKS.search(InputUtil.readString(SC,"Keyword: ")).forEach(System.out::println);
            case 4->updateBook();
            case 5->{BOOKS.delete(InputUtil.readInt(SC,"Book ID: "));System.out.println("Deleted.");}
        }
    }
    private static void addBook()throws SQLException{
        String t=InputUtil.readString(SC,"Title: "),a=InputUtil.readString(SC,"Author: "),i=InputUtil.readString(SC,"ISBN: "),c=InputUtil.readString(SC,"Category: ");
        int n=InputUtil.readInt(SC,"Copies: ");System.out.println("Added ID="+BOOKS.add(new Book(t,a,i,c,n)));
    }
    private static void updateBook()throws Exception{
        int id=InputUtil.readInt(SC,"Book ID: ");Book b=BOOKS.getById(id);int borrowed=b.getTotalCopies()-b.getAvailableCopies();
        b.setTitle(InputUtil.readString(SC,"Title: "));b.setAuthor(InputUtil.readString(SC,"Author: "));b.setIsbn(InputUtil.readString(SC,"ISBN: "));
        b.setCategory(InputUtil.readString(SC,"Category: "));int total=InputUtil.readInt(SC,"Total copies: ");
        if(total<borrowed)throw new IllegalArgumentException("Total cannot be below borrowed copies: "+borrowed);
        b.setTotalCopies(total);b.setAvailableCopies(total-borrowed);BOOKS.update(b);System.out.println("Updated.");
    }

    private static void memberMenu()throws Exception{
        System.out.println("\n--- Members ---\n1. Add\n2. List\n3. Update\n4. Delete");
        switch(InputUtil.readInt(SC,"Choose: ")){
            case 1->addMember();case 2->MEMBERS.getAll().forEach(System.out::println);case 3->updateMember();
            case 4->{MEMBERS.delete(InputUtil.readInt(SC,"Member ID: "));System.out.println("Deleted.");}
        }
    }
    private static void addMember()throws SQLException{
        String n=InputUtil.readString(SC,"Name: "),e=InputUtil.readString(SC,"Email: "),p=InputUtil.readString(SC,"Phone: ");
        System.out.println("Added ID="+MEMBERS.add(new Member(n,e,p,LocalDate.now())));
    }
    private static void updateMember()throws Exception{
        Member m=MEMBERS.getById(InputUtil.readInt(SC,"Member ID: "));
        m.setName(InputUtil.readString(SC,"Name: "));m.setEmail(InputUtil.readString(SC,"Email: "));m.setPhone(InputUtil.readString(SC,"Phone: "));MEMBERS.update(m);System.out.println("Updated.");
    }

    private static void borrowMenu()throws Exception{
        System.out.println("\n--- Borrow / Return ---\n1. Borrow\n2. Return\n3. All records\n4. Member history");
        switch(InputUtil.readInt(SC,"Choose: ")){
            case 1->System.out.println("Borrow ID="+LIBRARY.borrowBook(InputUtil.readInt(SC,"Book ID: "),InputUtil.readInt(SC,"Member ID: ")));
            case 2->{long late=LIBRARY.returnBook(InputUtil.readInt(SC,"Borrow ID: "));System.out.println("Returned. Late days="+late+" | Fine=Rs."+late*5);}
            case 3->LIBRARY.getAllBorrowRecords().forEach(System.out::println);
            case 4->LIBRARY.getMemberHistory(InputUtil.readInt(SC,"Member ID: ")).forEach(System.out::println);
        }
    }

    private static void reports()throws SQLException{
        List<Book> b=BOOKS.getAll();List<BorrowRecord> r=LIBRARY.getAllBorrowRecords();
        System.out.println("\n--- Reports ---");
        System.out.println("Book titles: "+b.size());
        System.out.println("Physical copies: "+b.stream().mapToInt(Book::getTotalCopies).sum());
        System.out.println("Available copies: "+b.stream().mapToInt(Book::getAvailableCopies).sum());
        System.out.println("Currently borrowed: "+r.stream().filter(x->"BORROWED".equals(x.getStatus())).count());
        System.out.println("Categories:");
        b.stream().collect(Collectors.groupingBy(Book::getCategory,Collectors.counting())).forEach((k,v)->System.out.println(k+" -> "+v));
        System.out.println("Books by title:");
        b.stream().sorted(Comparator.comparing(Book::getTitle)).forEach(System.out::println);
    }
}
