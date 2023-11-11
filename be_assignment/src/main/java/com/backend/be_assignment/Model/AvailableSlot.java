package com.backend.be_assignment.Model;

import java.sql.Date;
import java.sql.Time;

public class AvailableSlot {

    private Date slotDate;
    private Time slotTime;

    
    public Date getSlotDate() {
        return slotDate;
    }
    public void setSlotDate(Date slotDate) {
        this.slotDate = slotDate;
    }
    public Time getSlotTime() {
        return slotTime;
    }
    public void setSlotTime(Time slotTime) {
        this.slotTime = slotTime;
    }


    
    
    
}
