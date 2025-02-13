package org.ilyasse.spring_security.JWT;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * AuthEntryPointJwt is a custom authentication entry point for handling unauthorized requests.
 * It implements the AuthenticationEntryPoint interface and sends a JSON response with an error message
 * when an unauthenticated user tries to access a secured resource.
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    // Logger for logging errors
    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    /**
     * This method is triggered when an unauthenticated user tries to access a secured resource.
     * It sends a JSON response with an error message and a 401 Unauthorized status code.
     *
     * @param request       The HTTP request.
     * @param response      The HTTP response.
     * @param authException The exception that triggered this method (e.g., missing or invalid token).
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        // Log the unauthorized error
        logger.error("Unauthorized error: {}", authException.getMessage());

        // Set the response content type to JSON
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // Set the HTTP status code to 401 Unauthorized
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Create a response body with error details
        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", authException.getMessage());
        body.put("path", request.getServletPath());

        // Convert the response body to JSON and write it to the response output stream
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }

    @Override
    public void commence ( jakarta.servlet.http.HttpServletRequest request , jakarta.servlet.http.HttpServletResponse response , AuthenticationException authException ) throws IOException, ServletException {

    }
}