package com.academy.enroll.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDTO {

    private String id;

    @NotEmpty(message = "El campo nombre es requrido")
    @Size(min = 2, max = 80, message = "El campo nombre debe tener entre 2 y 80 caracteres")
    private String name;

    @NotEmpty(message = "El campo siglas es requrido")
    @Size(min = 2, max = 45, message = "El campo siglas debe tener entre 2 y 45 caracteres")
    private String acronym;

    private Boolean status;
}

