package com.blibli.experience.repository;

import com.blibli.experience.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {

  Mono<User> getFirstById(UUID id);

  Mono<User> getFirstByEmail(String email);

}
