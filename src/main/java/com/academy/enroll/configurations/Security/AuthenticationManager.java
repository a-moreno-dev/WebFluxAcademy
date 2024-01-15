package com.academy.enroll.configurations.Security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtUtil jwtUtil;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        final String token = authentication.getCredentials().toString();
        final String user = jwtUtil.getUsernameFromToken(token);
        try {
            if (user != null && jwtUtil.validateToken(token)) {
                final Claims claims = jwtUtil.getAllClaimsFromToken(token);
                final List<String> roles = claims.get("roles", List.class);

                return Mono.just(new UsernamePasswordAuthenticationToken(user, null,
                        roles.stream().map(SimpleGrantedAuthority::new).toList()));
            }
            throw new InterruptedException("Invalid or expired token");
        } catch (InterruptedException e) {
            return Mono.error(e);
        }
    }
}
