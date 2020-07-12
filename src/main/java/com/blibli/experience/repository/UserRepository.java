package com.blibli.experience.repository;

import com.blibli.experience.entity.document.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Mono<User> findFirstByUserId(UUID id);

    Mono<User> findFirstByUserEmail(String email);

    @Query(value = "{ 'addressForms': { $elemMatch: { 'province' : ?0 } } }")
    Flux<User> findByAddressFormsProvince(String province);

}