package com.backend.taskplanner.infrastructure.jwt;

import com.backend.taskplanner.infrastructure.service.CustomUserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static com.backend.taskplanner.infrastructure.jwt.Constants.*;

public class JWTValidate {
    public static boolean tokenExists(HttpServletRequest request, HttpServletResponse response) {
        String header = request.getHeader(HEADER_AUTHORIZATION);
        if (header == null || !header.startsWith(TOKEN_BEARER_PREFIX)) {
            return false;
        } else {
            return true;
        }
    }

    public static Claims JWTValid(HttpServletRequest request) {
        String jwtToken = request.getHeader(HEADER_AUTHORIZATION).replace(TOKEN_BEARER_PREFIX, "");

        return Jwts.parser()
                .setSigningKey(getSignedKey(SUPER_SECRET_KEY))
                .build()
                .parseClaimsJws(jwtToken).getBody();
    }

    public static void setAuthetication(Claims claims, CustomUserDetailService customUserDetailService) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(claims.getSubject());
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
