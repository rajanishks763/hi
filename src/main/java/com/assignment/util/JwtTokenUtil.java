package com.assignment.util;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.assignment.antity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {
    private final String secret = "1234";
    
    public String generateToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getLoginId());
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 3600000); // 1 hour
        
        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(expiration).signWith(SignatureAlgorithm.HS256, secret).compact();
    }
    
    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }
    
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

