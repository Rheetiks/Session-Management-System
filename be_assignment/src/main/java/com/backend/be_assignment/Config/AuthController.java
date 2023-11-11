package com.backend.be_assignment.Config;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.be_assignment.Model.JwtRequest;
import com.backend.be_assignment.Model.JwtResponse;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtUtil helper;

    @Autowired
    private UserDetailsService userDetailsService;
   

    

    

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request, HttpServletResponse servelet) {
        this.doAuthenticate(request.getUserUID(), request.getUserPassword());
        

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUserUID());
        String token = this.helper.generateToken(userDetails);

        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .UID(userDetails.getUsername()).build();
            
        
        Cookie cookie = new Cookie("Cookie", token);
        cookie.setHttpOnly(false);
        cookie.setPath("/");
        cookie.setMaxAge(5 * 60 * 60);
        servelet.addCookie(cookie);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    
    

    private void doAuthenticate(String UID, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(UID, password);
        try {
            manager.authenticate(authentication);

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
}

