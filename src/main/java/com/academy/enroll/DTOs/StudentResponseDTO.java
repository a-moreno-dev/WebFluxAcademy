package com.academy.enroll.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentResponseDTO {
    private String id;
    private String dni;
    private String name;
    private String lastName;
    private Integer age;
    private Boolean status;
    @JsonIgnore
    private LocalDate bornDate;

    public Integer getAge() {
        return this.getBornDate() != null ? Period.between(bornDate, LocalDate.now()).getYears() : null;
    }
}
