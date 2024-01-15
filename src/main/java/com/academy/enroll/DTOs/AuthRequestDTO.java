package com.academy.enroll.DTOs;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthRequestDTO {

    @NotEmpty(message = "El campo usuario es requrido")
    @Size(min = 2, max = 45, message = "El campo nombre debe tener entre 8 y 45 caracteres")
    private String username;

    @NotEmpty(message = "El campo password es requrido")
    @Size(min = 8, max = 45, message = "El campo password debe tener entre 8 y 45 caracteres")
    private String password;
}
