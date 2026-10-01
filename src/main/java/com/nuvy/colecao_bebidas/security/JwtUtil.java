package com.nuvy.colecao_bebidas.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private static final long VALIDADE_MS = 1000L * 60 * 60 * 12; // 12 horas

    private SecretKey getChave() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String gerarToken(String usuario) {
        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + VALIDADE_MS);

        return Jwts.builder()
                .subject(usuario)
                .issuedAt(agora)
                .expiration(expiracao)
                .signWith(getChave())
                .compact();
    }

    public String extrairUsuario(String token) {
        return Jwts.parser()
                .verifyWith(getChave())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean tokenValido(String token) {
        try {
            Jwts.parser().verifyWith(getChave()).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}