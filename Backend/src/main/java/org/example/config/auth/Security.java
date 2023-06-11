package org.example.config.auth;

import lombok.RequiredArgsConstructor;
import org.example.Model.Admin;
import org.example.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.jdbc.core.JdbcTemplate;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class Security {

    @Value("${security.oauth2.resource.jwk.key-set-uri}")
    private String uri;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors().and()
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST,"/seanses")
                .authenticated()


                .anyRequest()
                .permitAll()

                .and()
                .oauth2ResourceServer().jwt()
                .decoder(jwtDecoder());
        http
                .oauth2Login()
                .userInfoEndpoint()
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







