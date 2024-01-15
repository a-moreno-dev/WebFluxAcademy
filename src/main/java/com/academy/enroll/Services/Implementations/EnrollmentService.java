package com.academy.enroll.Services.Implementations;

import com.academy.enroll.Persistence.Documents.EnrollmentDocument;
import com.academy.enroll.Persistence.Repositories.IEnrollmentRepository;
import com.academy.enroll.Services.Abstractions.AbstractGenericService;
import com.academy.enroll.Services.Contracts.IEnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EnrollmentService extends AbstractGenericService<EnrollmentDocument, String> implements IEnrollmentService {

    protected final IEnrollmentRepository repository;

    @Override
    protected IEnrollmentRepository getRepository() {
        return repository;
    }

    @Override
    public Mono<EnrollmentDocument> createOne(EnrollmentDocument document) {
        document.setId(null);
        document.setStatus(false);
        return getRepository().save(document);
    }

    @Override
    public Mono<EnrollmentDocument> updateOneById(String id, EnrollmentDocument document) {
        if (validateDocumentId(id) || !id.equals(document.getId()))
            return Mono.empty();
        document.setId(id);
        document.setStatus(false);
        document.setRegisterDate(LocalDateTime.now());
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
