package com.bindu.smartexpensesystem.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {

    private static final SecretKey SECRET_KEY =
            Keys.hmacShaKeyFor(
                    "mysecretkeymysecretkeymysecretkey".getBytes()
            );

    public static String generateToken(String username) {

        return Jwts.builder()

                .setSubject(username)

                .setIssuedAt(new Date())

                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000 * 60 * 60
                        )
                )

                .signWith(SECRET_KEY)

                .compact();
    }
    
    public static String extractEmail(String token) {

        return Jwts.parserBuilder()

                .setSigningKey(SECRET_KEY)

                .build()

                .parseClaimsJws(token)

                .getBody()

                .getSubject();
    }
}