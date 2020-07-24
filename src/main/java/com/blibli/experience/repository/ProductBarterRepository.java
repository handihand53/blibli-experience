package com.blibli.experience.repository;

import com.blibli.experience.entity.document.ProductBarter;
import com.blibli.experience.enums.ProductAvailableStatus;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProductBarterRepository extends ReactiveMongoRepository<ProductBarter, UUID> {

    Mono<ProductBarter> findByProductBarterId(UUID productBarterId);

    Flux<ProductBarter> findAllByUserData_UserId(UUID userId);

    Flux<ProductBarter> findAllByAvailableStatus(ProductAvailableStatus availableStatus);

}
