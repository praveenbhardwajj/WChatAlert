package com.WChatAlert.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;
    
    
    // 🔐 Secret ko byte form me convert karke HMAC key bana rahe hain
    // Ye SIGNATURE banane ke liye use hogi
    private Key getsigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // ============================
    // 🔹 TOKEN GENERATION PART
    // ============================
    public String genrateToken(String subject) {
        return Jwts.builder()
                
                // -------- PAYLOAD START --------
                // Payload ka part hai:
                // { "sub": subject }
                .setSubject(subject)

                // Payload me "iat" (issued at)
                .setIssuedAt(new Date())

                // Payload me "exp" (expiration)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                // -------- PAYLOAD END --------

                // -------- HEADER + SIGNATURE --------
                // Yahan tum algorithm decide kar rahe ho
                // Isse HEADER me automatically:
                // { "alg": "HS256", "typ": "JWT" }
                // set ho jata hai
                
                // Ye line SIGNATURE generate karegi:
                // signature = HMACSHA256(header + payload, secret)
                .signWith(getsigningKey(), SignatureAlgorithm.HS256)
                // -------- SIGNATURE END --------

                // Final JWT string banata hai:
                // base64(header).base64(payload).base64(signature)
                .compact();
    }

    
    // ============================
    // 🔹 SUBJECT EXTRACTION
    // ============================
    public String extractSubject(String token) {

        return Jwts.parserBuilder()
                
                // Yahan signature verify hoga using same secret
                .setSigningKey(getsigningKey())

                .build()

                // Yahan:
                // 1. Signature verify hoga
                // 2. Expiry check hoga
                // 3. Agar sab sahi hai to payload milega
                .parseClaimsJws(token)

                // Payload access
                .getBody()

                // Payload se "sub" return
                .getSubject();
    }

    
    // ============================
    // 🔹 TOKEN VALIDATION
    // ============================
    public Boolean validateToken(String token) {
        try {

            Jwts.parserBuilder()
                    .setSigningKey(getsigningKey())
                    .build()
                    // Ye line internally:
                    // ✔ Signature verify karegi
                    // ✔ Expiration check karegi
                    // ✔ Token structure check karegi
                    .parseClaimsJws(token);

            return true;

        } catch (JwtException | IllegalArgumentException e) {
            // Agar:
            // ❌ Expired
            // ❌ Tampered
            // ❌ Wrong secret
            // ❌ Malformed
            return false;
        }
    }
}
