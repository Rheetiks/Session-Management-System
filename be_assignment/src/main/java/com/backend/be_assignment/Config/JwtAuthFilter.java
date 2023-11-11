package com.backend.be_assignment.Config;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.backend.be_assignment.Model.JwtRequest;

import org.slf4j.Logger;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Autowired
    private JwtUtil jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    JwtRequest userRequest;

   



    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                
                String requestHeader = request.getHeader("Authorization");
                logger.info(" Header :  {}", requestHeader);
                String UID = null;
                String token = null;
                if (requestHeader != null && requestHeader.startsWith("Bearer")) {
                    token = requestHeader.substring(7);
                    try {
        
                        UID = this.jwtHelper.getUsernameFromToken(token);
        
                    } catch (IllegalArgumentException e) {
                        logger.info("Illegal Argument while fetching the username !!");
                        e.printStackTrace();
                    } catch (ExpiredJwtException e) {
                        logger.info("Given jwt token is expired !!");
                        e.printStackTrace();
                    } catch (MalformedJwtException e) {
                        logger.info("Some changed has done in token !! Invalid Token");
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
        
                    }
        
        
                } else {
                    logger.info("Invalid Header Value !! ");
                }
        
        
                //
                if (UID != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // userRequest=this.userDetailsService.getUserByUID(UID);
        
                    //fetch user detail from username
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(UID);
                    Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
                    if (validateToken) {
        
                        //set the authentication
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        
                    } else {
                        logger.info("Validation fails !!");
                    }
        
        
                }
        
                filterChain.doFilter(request, response);


    }
}







