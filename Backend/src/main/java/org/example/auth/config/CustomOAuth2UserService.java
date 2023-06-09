package org.example.auth.config;

import org.example.Model.Admin;
import org.example.Services.AdminService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final AdminService adminService;

    public CustomOAuth2UserService(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // Sprawdzenie, czy użytkownik istnieje w bazie danych
        String email = oAuth2User.getAttribute("email");
        Admin admin = adminService.loadUserByUsername(email);

        // Utworzenie obiektu OAuth2User na podstawie UserDetails
        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
        attributes.put("sub", admin.getEmail()); // Ustawienie "sub" na adres email jako unikalny identyfikator użytkownika

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("Admin")); // Dodaj odpowiednią rolę dla użytkownika

        return new DefaultOAuth2User(authorities, attributes, "sub");
    }







}


