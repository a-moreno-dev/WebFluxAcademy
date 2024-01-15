package com.academy.enroll.configurations.Mappings;

import com.academy.enroll.DTOs.EnrollmentDTO;
import com.academy.enroll.Persistence.Documents.EnrollmentDocument;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnrollmentMapper {

    private final ModelMapper enrollmentMapper;

    public EnrollmentDTO toDTO(EnrollmentDocument enrollmentDocument) {
        return enrollmentMapper.map(enrollmentDocument, EnrollmentDTO.class);
    }

    public EnrollmentDocument toDocument(EnrollmentDTO enrollmentDTO) {
        return enrollmentMapper.map(enrollmentDTO, EnrollmentDocument.class);
    }
}
