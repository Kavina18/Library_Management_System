package com.library.dao;

import com.library.config.DBConnection;
import com.library.model.Member;
import java.sql.*;
import java.util.*;

public class MemberDAO {
    public int add(Member m)throws SQLException{
        String sql="INSERT INTO members(name,email,phone,joined_date) VALUES(?,?,?,?)";
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            p.setString(1,m.getName());p.setString(2,m.getEmail());p.setString(3,m.getPhone());p.setDate(4,java.sql.Date.valueOf(m.getJoinedDate()));p.executeUpdate();
            try(ResultSet r=p.getGeneratedKeys()){return r.next()?r.getInt(1):0;}
        }
    }
    public List<Member> findAll()throws SQLException{
        List<Member> list=new ArrayList<>();
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("SELECT * FROM members ORDER BY id");ResultSet r=p.executeQuery()){
            while(r.next())list.add(map(r));
        }return list;
    }
    public Member findById(int id)throws SQLException{
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("SELECT * FROM members WHERE id=?")){
            p.setInt(1,id);try(ResultSet r=p.executeQuery()){return r.next()?map(r):null;}
        }
    }
    public void update(Member m)throws SQLException{
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("UPDATE members SET name=?,email=?,phone=? WHERE id=?")){
            p.setString(1,m.getName());p.setString(2,m.getEmail());p.setString(3,m.getPhone());p.setInt(4,m.getId());p.executeUpdate();
        }
    }
    public void delete(int id)throws SQLException{
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("DELETE FROM members WHERE id=?")){p.setInt(1,id);p.executeUpdate();}
    }
    private Member map(ResultSet r)throws SQLException{
        return new Member(r.getInt("id"),r.getString("name"),r.getString("email"),r.getString("phone"),r.getDate("joined_date").toLocalDate());
    }
}
