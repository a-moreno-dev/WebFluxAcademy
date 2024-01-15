package com.academy.enroll.Services.Contracts;

import com.academy.enroll.Persistence.Documents.UserDocument;
import reactor.core.publisher.Mono;

public interface IUserService extends IGenericService<UserDocument, String> {

    Mono<UserDocument> saveDocumentHash(UserDocument user);

    Mono<UserDocument> findOneByUsername(String username);
}
