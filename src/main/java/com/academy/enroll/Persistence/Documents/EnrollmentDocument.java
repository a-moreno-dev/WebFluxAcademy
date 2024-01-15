package com.academy.enroll.Persistence.Documents;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "enrollments")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class EnrollmentDocument {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    @Field
    private Boolean status;

    @Field
    private StudentDocument student;

    @Field
    private List<CourseDocument> courses;

    @Field
    private LocalDateTime registerDate;
}
