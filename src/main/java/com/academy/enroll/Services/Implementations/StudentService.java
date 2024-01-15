package com.academy.enroll.Services.Implementations;

import com.academy.enroll.Persistence.Documents.StudentDocument;
import com.academy.enroll.Persistence.Repositories.IStudentRepository;
import com.academy.enroll.Services.Abstractions.AbstractGenericService;
import com.academy.enroll.Services.Contracts.IStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class StudentService extends AbstractGenericService<StudentDocument, String> implements IStudentService {

    protected final IStudentRepository repository;

    @Override
    protected IStudentRepository getRepository() {
        return repository;
    }

    @Override
    public Mono<StudentDocument> createOne(StudentDocument document) {
        document.setId(null);
        document.setStatus(false);
        return getRepository().save(document);
    }

    @Override
    public Mono<StudentDocument> updateOneById(String id, StudentDocument document) {
        if (validateDocumentId(id) || !id.equals(document.getId()))
            return Mono.empty();
        document.setId(id);
        return getRepository()
                .findById(id)
                .hasElement()
                .flatMap(hasElement -> Boolean.TRUE.equals(hasElement)
                        ? repository.save(document)
                        : Mono.empty()
                );
    }

    private boolean validateDocumentId(String id) {
        return id == null || id.isBlank();
    }
}
