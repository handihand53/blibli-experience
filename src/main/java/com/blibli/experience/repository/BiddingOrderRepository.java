package com.blibli.experience.repository;

import com.blibli.experience.entity.document.BiddingOrder;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BiddingOrderRepository extends ReactiveMongoRepository<BiddingOrder, UUID> {

    Mono<BiddingOrder> findFirstByBiddingOrderId(UUID biddingOrderId);

    Flux<BiddingOrder> findAllByBiddingOwner_UserId(UUID ownerUserId);

    Flux<BiddingOrder> findAllByBiddingWinner_UserId(UUID winnerUserId);

}
