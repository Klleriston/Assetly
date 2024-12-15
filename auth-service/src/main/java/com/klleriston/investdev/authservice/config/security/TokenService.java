package com.klleriston.investdev.authservice.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.klleriston.investdev.authservice.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${jwt.secret}")
    private String secret;


    public String generateToken(User user) {
        try {
            System.out.println("Gerando token para o usuário: " + user.getUsername());
            Algorithm algorithm = Algorithm.HMAC256(secret);
            var token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getUsername())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
            System.out.println("Token gerado: " + token);
            return token;
        } catch (JWTCreationException e) {
            System.out.println("Erro ao gerar token: " + e.getMessage());
            throw new RuntimeException("Err ao gerar token ->", e);
        }
    }

    public String validateToken(String token) {
        try {
            System.out.println("Validando token: " + token);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            var subject = JWT.require(algorithm).withIssuer("auth-api").build().verify(token).getSubject();
            System.out.println("Token válido. Sujeito: " + subject);
            return subject;
        } catch (JWTVerificationException e) {
            System.out.println("Erro ao verificar token: " + e.getMessage());
            throw new RuntimeException("Err ao verificar token ->", e);
        }
    }


    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }
}
