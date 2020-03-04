package com.blibli.experience.repository;

import com.blibli.experience.entity.document.Product;
import com.blibli.experience.entity.form.ShopForm;
import com.blibli.experience.enums.ProductTag;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, UUID> {

  Mono<Product> findFirstByProductId(UUID id);

  Mono<Product> findFirstByProductShopForm_ShopIdAndProductBarcode(UUID shopId, String barcode);

  Flux<Product> findAllByProductTagsContaining(ProductTag tag);

  Flux<Product> findAllByProductStockGreaterThanEqual(Integer minimumStock);

  Mono<Boolean> existsByProductShopFormAndProductBarcode(ShopForm shopForm, String barcode);

}
