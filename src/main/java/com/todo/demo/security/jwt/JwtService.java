package com.todo.demo.security.jwt;

import com.todo.demo.dto.JWTAuthenticationDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtService {

    private static final Logger log = LogManager.getLogger(JwtService.class);

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;


    @Value("${jwt.refresh.expiration}")
    private long refreshExpiration;

    public JWTAuthenticationDTO generateAuthToken(String email) {
        JWTAuthenticationDTO jwtDTO = new JWTAuthenticationDTO();
        jwtDTO.setToken(generateJwtToken(email));
        jwtDTO.setRefreshToken(generateRefreshToken(email));
        return jwtDTO;
    }

    public JWTAuthenticationDTO refreshBaseToken(String email, String refreshToken) {
        JWTAuthenticationDTO jwtDTO = new JWTAuthenticationDTO();
        jwtDTO.setToken(generateJwtToken(email));
        jwtDTO.setRefreshToken(refreshToken);
        return jwtDTO;
    }

    public String getEmailFromJwtToken(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return true;
        }catch (ExpiredJwtException e){
            log.info("token expired", e);
        } catch (UnsupportedJwtException e) {
            log.info("token unsupported", e);
        }catch (MalformedJwtException e) {
            log.info("token malformed", e);
        }catch (SecurityException e) {
            log.info("token security exception", e);
        }catch (Exception e) {
            log.info("token exception", e);
        }

        return false;
    }

    private String generateJwtToken(String email) {
        return Jwts.builder()
                .subject(email)
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey())
                .compact();

    }

    private String generateRefreshToken(String email) {
        return Jwts.builder()
                .subject(email)
                .expiration(new Date(System.currentTimeMillis()+ refreshExpiration*20))
                .signWith(getSignInKey())
                .compact();

    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
