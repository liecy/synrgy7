package com.rahmi.binfood.security;

import com.rahmi.binfood.model.ERole;
import com.rahmi.binfood.model.Role;
import com.rahmi.binfood.model.User;
import com.rahmi.binfood.repository.RoleRepository;
import com.rahmi.binfood.repository.UserRepository;
import com.rahmi.binfood.service.UserDetailsServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;

@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        final String email;
        final String fullName;

        if (oAuth2User instanceof OidcUser) {
            email = ((OidcUser) oAuth2User).getEmail();
            fullName = ((OidcUser) oAuth2User).getFullName();
        } else if (oAuth2User instanceof DefaultOAuth2User) {
            email = oAuth2User.getAttribute("email");
            fullName = oAuth2User.getAttribute("name");
        } else {
            throw new IllegalStateException("Unexpected user type: " + oAuth2User.getClass());
        }

        if (email == null || email.isEmpty()) {
            throw new IllegalStateException("Email not found from OAuth2 provider");
        }

        if (fullName == null || fullName.isEmpty()) {
            throw new IllegalStateException("Full name not found from OAuth2 provider");
        }

        User user = userRepository.findByEmailAddress(email)
                .orElseGet(() -> {
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseGet(() -> roleRepository.save(new Role(ERole.ROLE_USER)));

                    User newUser = User.builder()
                            .username(fullName)
                            .emailAddress(email)
                            .password("") // OAuth2 users might not have a password
                            .roles(Collections.singleton(userRole))
                            .build();
                    return userRepository.save(newUser);
                });

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        response.sendRedirect("/api/auth/oauth2/success?token=" + token);
    }
}
