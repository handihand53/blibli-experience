package com.blibli.experience.repository;

import com.blibli.experience.entity.document.ProductBidding;
import com.blibli.experience.enums.ProductAvailableStatus;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProductBiddingRepository extends ReactiveMongoRepository<ProductBidding, UUID> {

    Mono<ProductBidding> findFirstByProductBiddingId(UUID productBiddingId);

    Flux<ProductBidding> findAllByAvailableStatus(ProductAvailableStatus productAvailableStatus);

    Mono<Long> countAllByAvailableStatus(ProductAvailableStatus productAvailableStatus);

}
