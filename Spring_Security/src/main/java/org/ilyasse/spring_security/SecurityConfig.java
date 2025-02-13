package org.ilyasse.spring_security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;
@Configuration // tell spring that this class provides config.
@EnableMethodSecurity
@EnableWebSecurity //to enable security
public class SecurityConfig{

    @Autowired
    DataSource dataSource;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers ( "/h2-console/**" ).permitAll () //allowed all the req comming to h2 cconsole to baypass security.
                .anyRequest().authenticated());
        http.sessionManagement ( session // to disable cookies
                -> session.sessionCreationPolicy ( SessionCreationPolicy.STATELESS ));
       // http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        http.headers (headers ->
                headers.frameOptions ( HeadersConfigurer.FrameOptionsConfig::sameOrigin)); // allowed frames
        http.csrf (csrf -> csrf.disable ());
        return http.build();
    }
    @Bean // in memory Auth :
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User.withUsername ("user1")
                .password (passwordEncoder ().encode("password1")) // {noop} prefix which tell spring this password should saved as text and no  encodint don..
                .roles("USER")
                .build ();
        UserDetails admin = User.withUsername ( "admin" )
                .password ( passwordEncoder ().encode ("123123") )
                .roles ( "ADMIN" )
                .build ();

        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.createUser (user1);
        userDetailsManager.createUser (admin);
        return userDetailsManager; // to save user u need a schema:

        // return new InMemoryUserDetailsManager (user1,admin);
    }
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder ();
    }



}
