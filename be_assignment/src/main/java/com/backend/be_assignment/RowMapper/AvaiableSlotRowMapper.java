package com.backend.be_assignment.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.backend.be_assignment.Model.AvailableSlot;

public class AvaiableSlotRowMapper implements RowMapper<AvailableSlot>{

    @Override
    public AvailableSlot mapRow(ResultSet rs, int arg1) throws SQLException {

        AvailableSlot availableSlot= new AvailableSlot();

        availableSlot.setSlotDate(rs.getDate(1));
        availableSlot.setSlotTime(rs.getTime(2));

        return availableSlot;
    }

    
    
}
