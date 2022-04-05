package br.com.microservices.productapi.modules.auth.service;

import br.com.microservices.productapi.modules.exception.AuthenticationException;
import br.com.microservices.productapi.modules.auth.dto.AuthResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

@Service
public class AuthService {

    @Value("${app-config.secrets.jwt_secret}")
    private String jwtSecret;

    public String validateToken(String token) {
        if (Strings.isEmpty(token)) {
            throw new AuthenticationException("empty token access");
        }
        token = token.replace("Bearer ", Strings.EMPTY);
        return token;
    }

    public void isAuthorized(String token) {
        try {
            String accessToken = this.validateToken(token);
            Claims claims = Jwts
                    .parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(this.jwtSecret.getBytes()))
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
            AuthResponse user = AuthResponse.getUser(claims);
            if(ObjectUtils.isEmpty(user) || user.getId().isEmpty()) {
                throw new AuthenticationException("user not valid");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new AuthenticationException("Error while trying to process the access token");
        }
    }
}
