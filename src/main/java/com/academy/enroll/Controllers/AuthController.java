package com.academy.enroll.Controllers;


import com.academy.enroll.DTOs.AuthRequestDTO;
import com.academy.enroll.DTOs.AuthResponseDTO;
import com.academy.enroll.Services.Contracts.IUserService;
import com.academy.enroll.configurations.Security.ErrorLogin;
import com.academy.enroll.configurations.Security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Date;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;
    private final IUserService userService;

    @PostMapping("/login")
    public Mono<ResponseEntity<?>> login(@Valid @RequestBody AuthRequestDTO requestDTO) {
        return userService.findOneByUsername(requestDTO.getUsername())
                .map(userDetails -> {
                    final boolean passwordMatch = BCrypt.checkpw(
                            requestDTO.getPassword(),
                            userDetails.getPassword()
                    );
                    if (passwordMatch) {
                        final String token = jwtUtil.generateToken(userDetails);
                        final Date expiration = jwtUtil.getExpirationDateFromToken(token);

                        return ResponseEntity.ok(new AuthResponseDTO(token, expiration));
                    } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(new ErrorLogin("Bad Credentials", new Date()));
                    }
                })
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
