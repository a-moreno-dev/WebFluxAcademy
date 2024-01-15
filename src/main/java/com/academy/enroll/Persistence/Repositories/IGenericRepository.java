package com.academy.enroll.Persistence.Repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IGenericRepository<Document, IDDataType> extends ReactiveMongoRepository<Document, IDDataType> {
}
