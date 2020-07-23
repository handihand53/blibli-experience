package com.blibli.experience.repository;

import com.blibli.experience.entity.document.BarterSubmission;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BarterSubmissionRepository extends ReactiveMongoRepository<BarterSubmission, UUID> {

    Mono<BarterSubmission> findByBarterSubmissionId(UUID id);

    Flux<BarterSubmission> findAllByUserData_UserId(UUID userId);

    Flux<BarterSubmission> findAllByBarterSubmissionTargetBarter_ProductBarterId(UUID productBarterId);

}
