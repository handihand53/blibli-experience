package com.blibli.experience.repository;

import com.blibli.experience.entity.document.Order;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface OrderRepository extends ReactiveMongoRepository<Order, UUID> {

    Mono<Order> findFirstByOrderId(UUID orderId);

    Flux<Order> findAllByUserDto_UserId(UUID userId);

    Flux<Order> findAllByShopDto_ShopId(UUID shopId);

}
