package com.academy.enroll.configurations.Mappings;

import com.academy.enroll.DTOs.StudentRequestDTO;
import com.academy.enroll.DTOs.StudentResponseDTO;
import com.academy.enroll.Persistence.Documents.StudentDocument;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    private final ModelMapper modelMapper;

    public StudentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureMappings();
    }

    private void configureMappings() {
        modelMapper.createTypeMap(StudentDocument.class, StudentResponseDTO.class)
                .addMapping(StudentDocument::getBornDate, StudentResponseDTO::setAge);
    }

    public StudentResponseDTO toDTO(StudentDocument studentDocument) {
        return modelMapper.map(studentDocument, StudentResponseDTO.class);
    }

    public StudentDocument toDocument(StudentRequestDTO studentResponseDTO) {
        return modelMapper.map(studentResponseDTO, StudentDocument.class);
    }
}

