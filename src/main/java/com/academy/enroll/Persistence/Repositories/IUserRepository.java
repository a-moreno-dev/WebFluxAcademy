package com.academy.enroll.Persistence.Repositories;

import com.academy.enroll.Persistence.Documents.UserDocument;
import reactor.core.publisher.Mono;

public interface IUserRepository extends IGenericRepository<UserDocument, String>{

    Mono<UserDocument> findOneByUsername(String username);

}
