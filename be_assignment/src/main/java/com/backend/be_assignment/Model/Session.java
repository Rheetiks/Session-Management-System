package com.backend.be_assignment.Model;

import java.sql.Date;
import java.sql.Time;

public class Session {

    private int sessionId;
    private int studentId;
    private int deanId;
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
    public int getSessionId() {
        return sessionId;
    }
    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }
   
    public int getStudentId() {
        return studentId;
    }
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    public int getDeanId() {
        return deanId;
    }
    public void setDeanId(int deanId) {
        this.deanId = deanId;
    }





    
}
