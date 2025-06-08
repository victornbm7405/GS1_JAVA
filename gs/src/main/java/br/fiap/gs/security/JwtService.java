package br.fiap.gs.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;

public class JwtService {
    private static final String SECRET_KEY = "9f6c314587d4cfa03f99e1d271b8e83be01ad2dfb733f39c45db7b882ea734c6b12794eefa81accc11461b9843c9d181";

    public static boolean validateTokenStatic(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.out.println("❌ Erro ao validar token: " + e.getMessage());
            return false;
        }
    }
}
