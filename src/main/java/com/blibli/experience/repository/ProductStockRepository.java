package com.blibli.experience.repository;

import com.blibli.experience.entity.document.ProductStock;
import com.blibli.experience.enums.ProductCategory;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface ProductStockRepository extends ReactiveMongoRepository<ProductStock, UUID> {

    Mono<ProductStock> findFirstByShopDto_ShopIdAndProductDto_ProductId(UUID shopId, UUID productId);

    Mono<ProductStock> findByShopDto_ShopIdAndProductDto_ProductId(UUID shopId, UUID productId);

    Mono<ProductStock> findByShopDto_ShopIdAndProductDto_ProductBarcode(UUID shopId, String barcode);

    Mono<ProductStock> findFirstByProductDto_ProductId(UUID productId);

    Mono<ProductStock> findFirstByStockId(UUID id);

    Flux<ProductStock> findAllByShopDto_ShopId(UUID id);

    Flux<ProductStock> findAllByProductDto_ProductId(UUID productId);

    Mono<Long> countAllByProductDto_ProductCategory(ProductCategory productCategory);

}
