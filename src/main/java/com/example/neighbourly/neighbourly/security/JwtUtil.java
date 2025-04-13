package com.example.neighbourly.neighbourly.security;

import com.example.neighbourly.neighbourly.models.User;
import com.example.neighbourly.neighbourly.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtUtil {

    private static  final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS384);
    private static final int jwtExpiration = 1000 * 60 * 60 * 24;

    @Autowired
    private UserRepository userRepository;

    public String GenerateToken(String email, String role){
        return Jwts.builder()
                .setSubject(email)
                .claim("roles", (Object)role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+jwtExpiration))
                .signWith(SECRET_KEY)
                .compact();
    }

    public String extractEmail(String token){
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody().getSubject();
    }

    public String getRole(String token){
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("roles", String.class);
    }

    public boolean isTokenValid(String token){
        try{
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
        }
        catch (JwtException | IllegalArgumentException e){
            return false;
        }
        return true;
    }

}
