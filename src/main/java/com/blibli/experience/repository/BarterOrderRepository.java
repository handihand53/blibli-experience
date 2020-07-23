package com.blibli.experience.repository;

import com.blibli.experience.entity.document.BarterOrder;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BarterOrderRepository extends ReactiveMongoRepository<BarterOrder, UUID> {

    Mono<BarterOrder> findByBarterOrderId(UUID barterOrderId);
}
