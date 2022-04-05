package br.com.microservices.productapi.modules.auth.dto;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String id;
    private String name;
    private String email;

    public static AuthResponse getUser(Claims jwtClaims) {
        try {
            return AuthResponse
                    .builder()
                    .id((String) jwtClaims.get("id"))
                    .name((String) jwtClaims.get("name"))
                    .email((String) jwtClaims.get("email"))
                    .build();
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
