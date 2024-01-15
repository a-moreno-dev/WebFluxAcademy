package com.academy.enroll.configurations.Mappings;

import com.academy.enroll.DTOs.CourseDTO;
import com.academy.enroll.Persistence.Documents.CourseDocument;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseMapper {

    private final ModelMapper courseMapper;

    public CourseDTO toDTO(CourseDocument document) {
        return courseMapper.map(document, CourseDTO.class);
    }

    public CourseDocument toDocument(CourseDTO dto) {
        return courseMapper.map(dto, CourseDocument.class);
    }
}

