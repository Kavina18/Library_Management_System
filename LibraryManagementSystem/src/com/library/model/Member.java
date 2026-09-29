package com.library.model;

import java.time.LocalDate;

public class Member {
    private int id;
    private String name,email,phone;
    private LocalDate joinedDate;

    public Member(){}
    public Member(String name,String email,String phone,LocalDate joinedDate){
        this.name=name;this.email=email;this.phone=phone;this.joinedDate=joinedDate;
    }
    public Member(int id,String name,String email,String phone,LocalDate joinedDate){
        this(name,email,phone,joinedDate);this.id=id;
    }
    public int getId(){return id;} public void setId(int v){id=v;}
    public String getName(){return name;} public void setName(String v){name=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
    public LocalDate getJoinedDate(){return joinedDate;} public void setJoinedDate(LocalDate v){joinedDate=v;}
    public String toString(){return String.format("ID=%d | %s | %s | Phone=%s | Joined=%s",id,name,email,phone,joinedDate);}
}
