package com.backend.be_assignment.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.backend.be_assignment.Dao.Dao;
import com.backend.be_assignment.Model.AvailableSlot;
import com.backend.be_assignment.Model.JwtRequest;
import com.backend.be_assignment.Model.Session;
import com.backend.be_assignment.Model.User;

@org.springframework.stereotype.Service
public class Service implements UserDetailsService{

    @Autowired
    private Dao dao;

    public List<AvailableSlot> getAvailableSlot(int id){
        return this.dao.getAvailableSlot(id);
    }

    public List<User> getDeans(){
        return this.dao.getDeans();
    }

    public String bookSlot(String jwt,int deanId,AvailableSlot availableSlot){
        return this.dao.bookSlot(jwt,deanId,availableSlot);
    }

    public List<Session> viewSessions(int deanId){
        return this.dao.viewSessions(deanId);
    }



    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public JwtRequest getUserByUID(String UID){
        return this.dao.getUserByUID(UID);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        JwtRequest request=getUserByUID(username);
        return org.springframework.security.core.userdetails.User
                    .withUsername(request.getUserUID())
                    .password(passwordEncoder().encode(request.getUserPassword())) 
                    .roles(request.getUserRole())
                    .build();
    }
    
}
