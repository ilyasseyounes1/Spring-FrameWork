package org.ilyasse.spring_security.JWT;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AuthTokenFilter is a custom filter that intercepts incoming HTTP requests,
 * extracts the JWT token from the "Authorization" header, validates the token,
 * and sets the authentication in the Spring Security context.
 * This class extends OncePerRequestFilter to ensure the filter runs once per request.
 */
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    // Logger for logging debug information and errors
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Autowired
    private JwtUtils jwtUtils; // Utility class for JWT operations

    @Autowired
    private UserDetailsService userDetailsService; // Service to load user details

    /**
     * Filters incoming requests to validate the JWT token and set the authentication in the SecurityContext.
     *
     * @param request     The HTTP request.
     * @param response    The HTTP response.
     * @param filterChain The filter chain to continue the request processing.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Extract the JWT token from the request header
            String jwt = parseJwt(request);

            // Validate the token and extract the username
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                // Load user details from the database
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Create an authentication object
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // Set additional details (e.g., IP address, session ID) in the authentication object
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Log the roles extracted from the JWT token
                logger.debug("Roles from JWT: {}", userDetails.getAuthorities());
            }
        } catch (Exception e) {
            // Log any errors that occur during authentication
            logger.error("Cannot set user authentication: {}", e.getMessage());
        }

        // Continue the filter chain
        filterChain.doFilter( (ServletRequest) request , response);
    }

    /**
     * Extracts the JWT token from the "Authorization" header of the HTTP request.
     * The token is expected to start with "Bearer ".
     *
     * @param request The HTTP request.
     * @return The JWT token if found, otherwise null.
     */
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7); // Remove "Bearer " prefix
        }
        return null;
    }

    @Override
    protected void doFilterInternal ( HttpServletRequest request , jakarta.servlet.http.HttpServletResponse response , jakarta.servlet.FilterChain filterChain ) throws jakarta.servlet.ServletException, IOException {

    }
}