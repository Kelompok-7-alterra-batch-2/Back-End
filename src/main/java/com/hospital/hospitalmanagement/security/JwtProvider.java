package com.hospital.hospitalmanagement.security;

import com.hospital.hospitalmanagement.entities.UserEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Component
public class JwtProvider {
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private Long expirationAccessToken = 10L * 60 * 60;
    private Long expirationRefreshToken = 1000L * 60 * 60;

    public Map<String, String> generateToken(Authentication authentication){
        final UserEntity user = (UserEntity) authentication.getPrincipal();

        Date now = new Date(System.currentTimeMillis());
        Date expiryDateAccessToken = new Date(now.getTime() + expirationAccessToken);
        Date expiryDateRefreshToken = new Date(now.getTime() + expirationRefreshToken);


        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

        String access_token = Jwts.builder()
                .setId(user.getId().toString()) // with claims, this will be replaced
                .setSubject(user.getUsername()) // with claims, this will be replaced
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDateAccessToken)
                .signWith(key)
                .compact();

        String refresh_token = Jwts.builder()
                .setId(user.getId().toString()) // with claims, this will be replaced
                .setSubject(user.getUsername()) // with claims, this will be replaced
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDateRefreshToken)
                .signWith(key)
                .compact();

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);

        return tokens;
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (SignatureException ex){
            log.error("Invalid Jwt Signature: {}", ex.getMessage());
        }catch (MalformedJwtException ex){
            log.error("Invalid Jwt token: {}", ex.getMessage());
        }catch (ExpiredJwtException ex){
            log.error("Expired Jwt Token: {}", ex.getMessage());
        }catch (UnsupportedJwtException ex){
            log.error("Unsupported Jwt Token: {}", ex.getMessage());
        }catch (IllegalArgumentException ex){
            log.error("Jwt claim string is empty: {}", ex.getMessage());
        }
        return false;
    }

    public String getUsername(String token){
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.get("username").toString();
    }
}
