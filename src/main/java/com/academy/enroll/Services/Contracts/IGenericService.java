package com.academy.enroll.Services.Contracts;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IGenericService<Document, IDDataType> {

    Flux<Document> findAll();

    Mono<Document> findOneById(IDDataType documentId);

    Mono<Document> createOne(Document document);

    Mono<Document> updateOneById(IDDataType documentId, Document document);

    Mono<Boolean> deleteOneById(IDDataType documentId);
}
