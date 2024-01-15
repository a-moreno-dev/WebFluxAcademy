package com.academy.enroll.Services.Abstractions;

import com.academy.enroll.Persistence.Repositories.IGenericRepository;
import com.academy.enroll.Services.Contracts.IGenericService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class AbstractGenericService<Document, IDDataType> implements IGenericService<Document, IDDataType> {

    protected abstract IGenericRepository<Document, IDDataType> getRepository();

    @Override
    public Flux<Document> findAll() {
        return getRepository().findAll();
    }

    @Override
    public Mono<Document> createOne(Document document) {
        return Mono.empty();
    }

    @Override
    public Mono<Document> updateOneById(IDDataType id, Document document) {
        return Mono.empty();
    }

    @Override
    public Mono<Document> findOneById(IDDataType id) {
        if (validateDocumentId(id))
            return Mono.empty();
        return getRepository().findById(id);
    }

    @Override
    public Mono<Boolean> deleteOneById(IDDataType id) {
        if (validateDocumentId(id)) return Mono.empty();
        return getRepository()
                .findById(id)
                .hasElement()
                .flatMap(hasElement -> Boolean.TRUE.equals(hasElement)
                        ? getRepository().deleteById(id).thenReturn(true)
                        : Mono.just(false));
    }

    private boolean validateDocumentId(IDDataType id) {
        return id == null || id.toString().isBlank();
    }
}
