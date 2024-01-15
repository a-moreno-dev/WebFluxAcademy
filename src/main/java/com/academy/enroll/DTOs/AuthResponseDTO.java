package com.academy.enroll.DTOs;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    private Date expiration;
}
