package org.ilyasse.spring_security.JWT;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils {

    // Logger for logging errors and debug information
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    // Secret key for signing the JWT token (configured in application.properties)
    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    // Token expiration time in milliseconds (configured in application.properties)
    @Value("${spring.app.jwtExpirationMs}")
    private long jwtExpirationMs;

    /**
     * Extracts the JWT token from the "Authorization" header of the HTTP request.
     *
     * @param request The HTTP request.
     * @return The JWT token if found, otherwise null.
     */
    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        logger.debug("Authorization Header: {}", bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Remove "Bearer " prefix
        }
        return null;
    }

    /**
     * Generates a JWT token for the given user details.
     *
     * @param userDetails The user details (username, roles, etc.).
     * @return The generated JWT token.
     */
    public String generateTokenFromUsername(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername()) // Set the subject (username)
                .issuedAt(new Date()) // Set the issue date
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs)) // Set the expiration date
                .signWith(key()) // Sign the token with the secret key
                .compact(); // Build the token
    }

    /**
     * Extracts the username from the JWT token.
     *
     * @param token The JWT token.
     * @return The username extracted from the token.
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith(key()) // Verify the token with the secret key
                .build()
                .parseSignedClaims(token) // Parse the token
                .getPayload() // Get the payload (claims)
                .getSubject(); // Extract the subject (username)
    }

    /**
     * Generates the secret key for signing the JWT token.
     *
     * @return The secret key.
     */
    private SecretKey key() {
        // Decode the base64-encoded secret key and create a SecretKey
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret));
    }

    /**
     * Extracts a specific claim from the JWT token.
     *
     * @param token          The JWT token.
     * @param claimsResolver A function to resolve the desired claim.
     * @return The extracted claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token The JWT token.
     * @return All claims in the token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key()) // Verify the token with the secret key
                .build()
                .parseSignedClaims(token) // Parse the token
                .getPayload(); // Get the payload (claims)
    }

    /**
     * Validates the JWT token.
     *
     * @param authToken The JWT token to validate.
     * @return True if the token is valid, false otherwise.
     */
    public boolean validateJwtToken(String authToken) {
        try {
            // Parse and verify the token
            Jwts.parser()
                    .verifyWith(key()) // Verify the token with the secret key
                    .build()
                    .parseSignedClaims(authToken); // Parse the token
            return true; // Token is valid
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error validating JWT token: {}", e.getMessage());
        }
        return false; // Token is invalid
    }
}