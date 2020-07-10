package com.blibli.experience.repository;

import com.blibli.experience.entity.document.ProductStock;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface ProductStockRepository extends ReactiveMongoRepository<ProductStock, UUID> {

    Mono<ProductStock> findFirstByProductShopForm_ShopId(UUID id);

    Flux<ProductStock> findAllByShopForm_ShopId(UUID id);

}
