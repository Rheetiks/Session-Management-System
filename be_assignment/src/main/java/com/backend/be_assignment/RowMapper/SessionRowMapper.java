package com.backend.be_assignment.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.backend.be_assignment.Model.Session;

public class SessionRowMapper implements RowMapper<Session>{

    @Override
    public Session mapRow(ResultSet rs, int arg1) throws SQLException {
        Session session= new Session();
        session.setSessionId(rs.getInt(1));
        session.setStudentId(rs.getInt(2));
        session.setDeanId(rs.getInt(3));
        session.setSlotDate(rs.getDate(4));
        session.setSlotTime(rs.getTime(5));
        return session;

    }

}
