package com.academy.enroll.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentRequestDTO {

    private String id;

    @NotEmpty(message = "El campo nombre es requrido")
    @Size(min = 2, max = 45, message = "El campo nombre debe tener entre 2 y 45 caracteres")
    private String name;

    @NotEmpty(message = "El campo apellido es requrido")
    @Size(min = 2, max = 45, message = "El campo apellido debe tener entre 2 y 45 caracteres")
    private String lastName;

    @NotEmpty(message = "El campo DNI es requrido")
    @Size(min = 5, max = 36, message = "El campo DNI debe tener entre 5 y 36 caracteres")
    private String dni;

    @NotNull(message = "Debe proveer una fecha de nacimento")
    @Past(message = "La fecha de nacimiento es incorrecta")
    private LocalDate bornDate;
}
