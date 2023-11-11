package com.backend.be_assignment.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.backend.be_assignment.Model.AvailableSlot;
import com.backend.be_assignment.Model.Session;
import com.backend.be_assignment.Model.User;
import com.backend.be_assignment.Service.Service;

@RestController
public class Controller {

    @Autowired
    private Service service;

    @GetMapping("/student/get/AvailableSlot/{id}")
    public List<AvailableSlot> getAvailableSlot(@PathVariable int id){
        return this.service.getAvailableSlot(id);
    }

    @GetMapping("/student/get/deans")
    public List<User> getDeans(){
        return this.service.getDeans();

    }

    @PostMapping("/student/get/AvailableSlot/{deanId}/bookSlot")
    public String bookSlot(@RequestHeader("Authorization") String authorization,@PathVariable int deanId, @RequestBody AvailableSlot availableSlot){
        String jwt=authorization.replace("Bearer ", "");
        return this.service.bookSlot(jwt,deanId,availableSlot);
    }

    @GetMapping("/dean/{deanId}/viewSessions")
    public List<Session> viewSessions(@PathVariable int deanId){
        
        return this.service.viewSessions(deanId);
    }
    
}
