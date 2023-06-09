package org.example.auth.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;


import java.io.IOException;
import java.security.interfaces.RSAPublicKey;
import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class Security {

    @Value("${security.oauth2.resource.jwk.key-set-uri}")
    private String uri;
    private final AdminService adminService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/seanses")
                .authenticated()

                .anyRequest()
                .permitAll()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .oauth2ResourceServer().jwt()
                .decoder(jwtDecoder());
        http
                .oauth2Login() //http://localhost:8000/api/oauth2/authorization/google
                .userInfoEndpoint()
               // .userService(new CustomOAuth2UserService(adminService));
                .userService(new DefaultOAuth2UserService());


        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        OAuth2TokenValidator<Jwt> jwtValidator = JwtValidators.createDefaultWithIssuer("https://accounts.google.com");
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(uri).build();
        jwtDecoder.setJwtValidator(jwtValidator);
        return jwtDecoder;
    }
}

