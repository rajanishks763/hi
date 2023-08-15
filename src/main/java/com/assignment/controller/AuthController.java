package com.assignment.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.antity.User;
import com.assignment.dto.TokenConfig;
import com.assignment.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<TokenConfig> login(@RequestBody Map<String, String> credentials) {
        String loginId = credentials.get("loginId");
        String password = credentials.get("password");
        
        String token = authService.authenticate(loginId, password);
        
        TokenConfig newtoken = new TokenConfig();
        
        
        
        if (token != null) {
        	newtoken.setTokenUrl(token);
            return ResponseEntity.ok(newtoken);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    @PostMapping("/save/user")
    public String saveUser(@RequestBody User user) {
    	return authService.saveUserDetails(user);
    }
}

