package org.example.config.auth;

import org.example.Model.Admin;
import org.example.Repository.AdminRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.AuthenticationProvider;


import java.util.Collections;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final AdminRepository adminRepository;

    public CustomAuthenticationProvider(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getPrincipal().toString();

        // Sprawdź, czy e-mail znajduje się w repozytorium AdminRepository

        Admin admin = adminRepository.findAll()
                .stream()
                .filter(r -> r.getEmail().equals(email))
                .findFirst()
                .orElse(null);;

        if (admin==null) {
            throw new BadCredentialsException("Access denied.");
        }

        return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
