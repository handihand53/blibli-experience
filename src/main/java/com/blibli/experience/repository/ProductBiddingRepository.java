package com.blibli.experience.repository;

import com.blibli.experience.entity.document.ProductBidding;
import com.blibli.experience.enums.ProductAvailableStatus;
import com.blibli.experience.enums.ProductBiddingAvailableStatus;
import com.blibli.experience.enums.ProductCategory;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public interface ProductBiddingRepository extends ReactiveMongoRepository<ProductBidding, UUID> {

    Mono<ProductBidding> findFirstByProductBiddingId(UUID productBiddingId);

    Flux<ProductBidding> findAllByUserData_UserIdAndAvailableStatus(UUID userId, ProductBiddingAvailableStatus productBiddingAvailableStatus);

    Flux<ProductBidding> findAllByAvailableStatus(ProductBiddingAvailableStatus productBiddingAvailableStatus);

    Flux<ProductBidding> findAllByCloseBidDateBefore(LocalDateTime localDateTime);

    Flux<ProductBidding> findAllByAvailableStatusAndProductCategory(ProductBiddingAvailableStatus productBiddingAvailableStatus, ProductCategory productCategory);

    @Query(value = "{ 'biddingForms': { $elemMatch: { 'userDataForm_userId' : ?0 } } }")
    Flux<ProductBidding> findAllByBiddingFormsUserData(UUID userId);

    Mono<Long> countAllByAvailableStatusAndUserData_UserId(ProductBiddingAvailableStatus productBiddingAvailableStatus, UUID userId);

    Mono<Long> countAllByAvailableStatusAndProductCategory(ProductBiddingAvailableStatus productBiddingAvailableStatus, ProductCategory productCategory);

    Mono<Long> countAllByAvailableStatus(ProductBiddingAvailableStatus productBiddingAvailableStatus);

}
