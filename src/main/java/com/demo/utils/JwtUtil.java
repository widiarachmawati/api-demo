package com.demo.utils;

import com.demo.controller.model.UserRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

  private final String signKey = StringUtil.signKeyGenerator();

  public String generateToken(UserRequest request) {
    return Jwts.builder()
        .setSubject("JWT Token")
        .claim("username", request.getUsername())
        .claim("password", request.getPassword())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Token valid for 10 hours
        .signWith(SignatureAlgorithm.HS512, signKey)
        .compact();
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(signKey).parseClaimsJws(token).getBody();
  }

  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public boolean validateToken(String authToken) {
    try {
      if (!isTokenExpired(authToken)) {
        Jwts.parser().setSigningKey(signKey).parseClaimsJws(authToken);
        return true;
      }
    } catch (Exception ignored) {}
    return false;
  }

}
