package com.academy.enroll.Services.Implementations;

import com.academy.enroll.Persistence.Documents.CourseDocument;
import com.academy.enroll.Persistence.Repositories.ICourseRepository;
import com.academy.enroll.Services.Abstractions.AbstractGenericService;
import com.academy.enroll.Services.Contracts.ICourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CourseService extends AbstractGenericService<CourseDocument, String> implements ICourseService {

    private final ICourseRepository repository;

    @Override
    protected ICourseRepository getRepository() {
        return repository;
    }

    @Override
    public Mono<CourseDocument> createOne(CourseDocument document) {
        document.setId(null);
        return getRepository().save(document);
    }

    @Override
    public Mono<CourseDocument> updateOneById(String id, CourseDocument document) {
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
