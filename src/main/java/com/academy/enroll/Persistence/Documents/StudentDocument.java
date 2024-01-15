package com.academy.enroll.Persistence.Documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.Period;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "students")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class StudentDocument {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    @Field
    private String dni;

    @Field
    private String name;

    @Field
    private String lastName;

    @Field
    private LocalDate bornDate;

    @Field
    private Boolean status;
}
