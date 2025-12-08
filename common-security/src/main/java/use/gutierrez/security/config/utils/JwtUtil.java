package use.gutierrez.security.config.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.time.Duration;
import java.util.Date;

public class JwtUtil {

  private static final String SECRET = "supersecretkeysupersecretkey12345";
  private static final Duration EXPIRATION = Duration.ofHours(1);

  public static String generateToken(String username, String role) {
    return Jwts.builder()
        .claim("username", username)
        .claim("role", role)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + EXPIRATION.toMillis()))
        .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), Jwts.SIG.HS256)
        .compact();
  }

  public static String getSecret() {
    return SECRET;
  }
}
