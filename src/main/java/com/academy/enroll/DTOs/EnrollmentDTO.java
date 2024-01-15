package com.academy.enroll.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnrollmentDTO {

    private String id;

    private Boolean status;

    @NotNull(message = "El campo estudiante  es requrido")
    private StudentRequestDTO student;

    @NotNull(message = "Los cursos son requeridos")
    private List<CourseDTO> courses;

    private LocalDateTime registerDate;
}
