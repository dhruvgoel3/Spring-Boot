package com.example.spring_security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JWTUtils {

    private String jwtSecret = "YS1zdHJpbmctc2VjcmV0LWF0LWxlYXN0LTI1Ni1iaXRzLWxvbmc=";
    private int jwtExpirations = 172800000; // This is in milliseconds

    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer "))
            return bearerToken.substring(7);
        return null;
    }

    public String generateTokenFromUserName(UserDetails userDetails) {
        String userName = userDetails.getUsername();
        return Jwts.builder()
                .subject(userName)
                .claim("roles", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtExpirations))
                .signWith(Key())
                .compact();
    }

    public boolean validJwtToken(String jwtToken) {
        try {
            Jwts.parser().verifyWith((SecretKey) Key()).build().parseSignedClaims(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;

    }

    private Key Key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsernameFromToken(String jwt) {
        return Jwts.parser().verifyWith((SecretKey) Key())
                .build().parseSignedClaims(jwt).getPayload().getSubject();
    }

    public Claims getAllClaims(String jwt) {
        return Jwts.parser().verifyWith((SecretKey) Key()).build().parseSignedClaims(jwt).getPayload();
    }
}
