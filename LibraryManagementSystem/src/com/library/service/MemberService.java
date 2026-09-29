package com.library.service;

import com.library.dao.MemberDAO;
import com.library.exception.MemberNotFoundException;
import com.library.model.Member;
import java.sql.SQLException;
import java.util.List;

public class MemberService {
    private final MemberDAO dao=new MemberDAO();
    public int add(Member m)throws SQLException{if(m.getName()==null||m.getName().isBlank())throw new IllegalArgumentException("Name required");if(m.getEmail()==null||!m.getEmail().contains("@"))throw new IllegalArgumentException("Valid email required");return dao.add(m);}
    public List<Member> getAll()throws SQLException{return dao.findAll();}
    public Member getById(int id)throws SQLException,MemberNotFoundException{Member m=dao.findById(id);if(m==null)throw new MemberNotFoundException("Member not found: "+id);return m;}
    public void update(Member m)throws SQLException,MemberNotFoundException{getById(m.getId());dao.update(m);}
    public void delete(int id)throws SQLException,MemberNotFoundException{getById(id);dao.delete(id);}
}
