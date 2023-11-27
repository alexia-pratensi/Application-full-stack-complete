package com.openclassrooms.mddapi.payload.response;

import lombok.Getter;
import lombok.Setter;

/**
 * This class represents the response that is sent after a successful
 * authentication.
 * It includes the JWT token, the type of the token, the ID of the authenticated
 * user, their username, and their name.
 */
@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String name;

    public JwtResponse(String accessToken, Long id, String username, String name) {
        this.token = accessToken;
        this.id = id;
        this.name = name;
    }
}
