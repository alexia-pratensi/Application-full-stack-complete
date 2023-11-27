package com.openclassrooms.mddapi.security.jwt;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.openclassrooms.mddapi.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${oc.app.jwtSecret}")
    private String jwtSecret;

    @Value("${oc.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    /**
     * This method is used to generate a JWT token for an authenticated user.
     * It retrieves the principal from the authentication object, builds a JWT with
     * the username as the subject,
     * the current date as the issued at date, and a future date as the expiration
     * date.
     * The JWT is then signed with the HS512 algorithm and the secret key.
     *
     * @param authentication This is the Authentication object from which the
     *                       principal user details are retrieved.
     * @return String This returns the generated JWT.
     */
    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * This method is used to extract the username from the JWT token.
     * It parses the JWT token using the secret key, retrieves the claims, and gets
     * the subject from the claims.
     *
     * @param token This is the JWT token from which the username is extracted.
     * @return String This returns the username extracted from the JWT token.
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * This method is used to validate the JWT token.
     * It parses the JWT token using the secret key and returns true if the parsing
     * is successful.
     * If any exception occurs during parsing, it logs the error message and returns
     * false.
     *
     * @param authToken This is the JWT token that is to be validated.
     * @return boolean This returns true if the JWT token is valid, or false
     *         otherwise.
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
