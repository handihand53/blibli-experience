package com.blibli.experience.repository;

import com.blibli.experience.entity.document.Shop;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface ShopRepository extends ReactiveMongoRepository<Shop, UUID> {

    Mono<Shop> findFirstByShopId(UUID id);

    Mono<Shop> findFirstByUserId(UUID userId);

}
