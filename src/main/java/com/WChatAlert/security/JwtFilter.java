package com.WChatAlert.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 👇 IMPORTANT imports (ye missing the)
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

@Component
public class JwtFilter extends OncePerRequestFilter {

    // JwtUtil ka object (Spring automatically inject karega)
    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 🔹 Request ke headers me se "Authorization" header nikaal rahe hain
        String authHeader = request.getHeader("Authorization");

        // 🔹 Check kar rahe hain:
        // 1. Header present hai?
        // 2. Kya "Bearer " se start ho raha hai?
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            // 🔹 "Bearer " ke baad jo actual token hai wo nikaal rahe hain
            String token = authHeader.substring(7);

            // 🔹 Token valid hai ya nahi check kar rahe hain
            if (jwtUtil.validateToken(token)) {

                // 🔹 Token ke andar se email (subject) nikaal rahe hain
                String email = jwtUtil.extractSubject(token);

                // 🔹 Spring Security ko batane ke liye authentication object bana rahe hain
                // principal = email
                // credentials = null (password ki zarurat nahi ab)
                // authorities = empty list (roles abhi use nahi kar rahe)
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                Collections.emptyList()
                        );

                // 🔹 Extra request details attach kar rahe hain (IP, session etc.)
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 🔹 Spring Security ke context me set kar rahe hain
                // Iska matlab: "User authenticated hai"
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 🔹 Important: Request ko next filter ya controller tak bhejna zaroori hai
        filterChain.doFilter(request, response);
    }
}
