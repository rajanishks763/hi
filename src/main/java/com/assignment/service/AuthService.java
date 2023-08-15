package com.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestBody;

import com.assignment.antity.User;
import com.assignment.repository.UserRepository;
import com.assignment.util.JwtTokenUtil;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    public String authenticate(String loginId, String password) {
        User user = userRepository.findByLoginId(loginId);
        
        if (user != null && user.getPassword().equals(password)) {
            return jwtTokenUtil.generateToken(user);
        }
        
        return null;
    }
    
    
    public String saveUserDetails( User user) {
        
        
        if (user != null && user.getPassword().isEmpty()) {
           return "invalid details";
        }
        
      userRepository.save(user);
      return "saved user details successfully";
    }


}

