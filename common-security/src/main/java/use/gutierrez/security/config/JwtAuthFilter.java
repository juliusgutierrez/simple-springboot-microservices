package use.gutierrez.security.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import use.gutierrez.security.config.utils.JwtUtil;

import java.io.IOException;

@Configuration
public class JwtAuthFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = authHeader.substring(7);

    try {
      var claims = Jwts.parser()
          .verifyWith(Keys.hmacShaKeyFor(JwtUtil.getSecret().getBytes()))
          .build()
          .parseSignedClaims(token)
          .getPayload();

      String username = claims.getSubject();
      String role = claims.get("role", String.class);

      var auth = new UsernamePasswordAuthenticationToken(
          username,
          null,
          java.util.List.of(new SimpleGrantedAuthority("ROLE_" + role))
      );

      SecurityContextHolder.getContext().setAuthentication(auth);

    } catch (Exception e) {
      SecurityContextHolder.clearContext();
    }

    filterChain.doFilter(request, response);
  }
}
