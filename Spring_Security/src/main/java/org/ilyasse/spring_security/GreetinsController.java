package org.ilyasse.spring_security;

import org.ilyasse.spring_security.JWT.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.ilyasse.spring_security.JWT.LoginResponse;
import org.ilyasse.spring_security.JWT.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;

@RestController
public class GreetinsController{

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping("/spring")
    public String greet (){
        return "spring security";
    }
@PreAuthorize ( "hasRole('ADMIN')" )
@GetMapping("/admin")
    public String admin (){
        return "spring security admin";
}

        @PostMapping("/signin")
        public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest  LoginRequest) {
            try {
                // Authenticate the user
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                LoginRequest.getUsername(),
                                 LoginRequest.getPassword()
                        )
                );

                // Set the authentication object in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Generate a JWT token
                String jwt = jwtUtils.generateTokenFromUsername(authentication);

                // Return the JWT token in the response
                return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
            } catch (AuthenticationException e) {
                // Handle authentication failure
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            }
        }
    }
