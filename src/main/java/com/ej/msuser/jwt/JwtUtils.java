package com.ej.msuser.jwt;


import com.ej.msuser.entity.Usuario;
import com.ej.msuser.exceptions.TokenInvalidException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

public class JwtUtils {

    private static String SECRET_KEY = "23890138921-09-4802489032-432423";
    private static Integer EXPIRE_MINUTES = 60;

    private static SecretKey generatedKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public static JwtToken createToken(Usuario usuario){
        Date issueAt = new Date();
        Date limit = toExpireDate(issueAt);
        String token = Jwts.builder().header().add("typ", "JWT").and()
                .subject(usuario.getUsername()).issuedAt(issueAt).expiration(limit)
                .signWith(generatedKey()).claim("role", usuario.getRole()).compact();
        return new JwtToken(token);
    }

    private static Date toExpireDate(Date issueAt) {
        return Date.from(issueAt.toInstant().plus(Duration.ofMinutes(EXPIRE_MINUTES)));
    }

    public static String isValidToken(String token){
        try{
            return Jwts.parser().verifyWith(generatedKey()).build()
                    .parseSignedClaims(refactorToken(token)).getPayload().getSubject();
        } catch (JwtException e){
            throw new TokenInvalidException("Token invalido");
        }
    }

    private static CharSequence refactorToken(String token) {
        if(token.contains("Bearer ")){
            return token.substring("Bearer ".length());
        }
        return token;
    }

}
