package com.blibli.experience.repository;

import com.blibli.experience.entity.document.ProductBidding;
import com.blibli.experience.enums.ProductAvailableStatus;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public interface ProductBiddingRepository extends ReactiveMongoRepository<ProductBidding, UUID> {

    Mono<ProductBidding> findFirstByProductBiddingId(UUID productBiddingId);

    Flux<ProductBidding> findAllByUserData_UserIdAndAvailableStatus(UUID userId, ProductAvailableStatus productAvailableStatus);

    Flux<ProductBidding> findAllByAvailableStatus(ProductAvailableStatus productAvailableStatus);

    Flux<ProductBidding> findAllByCloseBidDateBefore(LocalDateTime localDateTime);

    Mono<Long> countAllByAvailableStatus(ProductAvailableStatus productAvailableStatus);

}
