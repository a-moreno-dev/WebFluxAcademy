package com.academy.enroll.configurations.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SecurityContextRepository implements ServerSecurityContextRepository {

    private final AuthenticationManager authenticationManager;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return null;
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        try {
            final String authorizationHeader = exchange
                    .getRequest()
                    .getHeaders()
                    .getFirst(HttpHeaders.AUTHORIZATION);

            if (authorizationHeader == null)
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getReasonPhrase());

            if (authorizationHeader.toLowerCase().startsWith("bearer ")) {
                final int TOKEN_POSITION = 1;
                final String token = authorizationHeader.split(" ")[TOKEN_POSITION];
                final Authentication auth = new UsernamePasswordAuthenticationToken(token, token);
                return this.authenticationManager.authenticate(auth).map(SecurityContextImpl::new);
            }

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getReasonPhrase());
        } catch (ResponseStatusException e) {
            return Mono.error(e);
        }
    }
}
