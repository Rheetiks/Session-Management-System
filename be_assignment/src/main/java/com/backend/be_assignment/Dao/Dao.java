package com.backend.be_assignment.Dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.backend.be_assignment.Config.JwtUtil;
import com.backend.be_assignment.Model.AvailableSlot;
import com.backend.be_assignment.Model.JwtRequest;
import com.backend.be_assignment.Model.Session;
import com.backend.be_assignment.Model.User;
import com.backend.be_assignment.RowMapper.AvaiableSlotRowMapper;
import com.backend.be_assignment.RowMapper.SessionRowMapper;
import com.backend.be_assignment.RowMapper.UserRowMapper;

@Repository
public class Dao {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private JwtUtil helper;

    public List<AvailableSlot> getAvailableSlot(int id){
        String sql="SELECT DATE_ADD(CURDATE(), INTERVAL n DAY) AS DATE, TIME('10:00:00') AS TIME " +
        "FROM (" +
        "    SELECT ROW_NUMBER() OVER () - 1 AS n " +
        "    FROM information_schema.columns " +
        "    LIMIT 31" +
        ") numbers " +
        "WHERE DATE_ADD(CURDATE(), INTERVAL n DAY) <= LAST_DAY(CURDATE()) " +
        "    AND MONTH(DATE_ADD(CURDATE(), INTERVAL n DAY)) = MONTH(CURDATE()) " +
        "    AND DAYOFWEEK(DATE_ADD(CURDATE(), INTERVAL n DAY)) IN (5, 6) " +
        "    AND NOT EXISTS (" +
        "        SELECT 1 " +
        "        FROM Session " +
        "        WHERE deanId = ? AND slotDate = DATE_ADD(CURDATE(), INTERVAL n DAY)" +
        "    )";

        return this.jdbc.query(sql,new AvaiableSlotRowMapper(),id);

    }

    public List<User> getDeans(){
        String sql="select * from user where userRole='Dean'";
        return this.jdbc.query(sql,new UserRowMapper());

    }

    public String bookSlot(String jwt,int deanId,AvailableSlot availableSlot){
        String sql="select * from Session where studentId=(select userId from user where userUID=?) and deanId=? and slotDate=? and slotTime=?";
        Session session=null;
        
        try{
            session=this.jdbc.queryForObject(sql,new SessionRowMapper(),helper.getUsernameFromToken(jwt),deanId,availableSlot.getSlotDate(),availableSlot.getSlotTime());
        }catch(Exception e){}

        if(session!=null){
            return "You have Already Booked the Session";
        }
        sql="insert into Session(studentId,deanid,slotDate,slotTime) values((select userId from user where userUID=?),?,?,?)";
        this.jdbc.update(sql,helper.getUsernameFromToken(jwt),deanId,availableSlot.getSlotDate(),availableSlot.getSlotTime());
        return "Successfully Booked Slot with Dean";
    }

    public List<Session> viewSessions(int deanId){
        String sql="select * from Session where deanId=?";
        return this.jdbc.query(sql, new SessionRowMapper(),deanId);
    }

    public User loadUserByUserUID(JwtRequest request){
        User user =null;
        String sql="select * from user where userUID=? and userRole=?";
        user=this.jdbc.queryForObject(sql, new UserRowMapper(),request.getUserUID(),request.getUserRole());
        return user;

    }

    public JwtRequest getUserByUID(String UID){
        String sql="select * from user where userUID=?";
        User user=this.jdbc.queryForObject(sql, new UserRowMapper(),UID);
        JwtRequest request= new JwtRequest();
        request.setUserUID(UID);
        request.setUserRole(user.getUserRole());
        request.setUserPassword(user.getUserPassword());
        return request;
    }
    
}
