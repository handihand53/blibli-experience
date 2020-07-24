package com.blibli.experience.repository;

import com.blibli.experience.entity.document.Cart;
import com.blibli.experience.enums.CartTag;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface CartRepository extends ReactiveMongoRepository<Cart, UUID> {

  Mono<Cart> findFirstByCartId(UUID cartId);

  Mono<Cart> findFirstByUserId(UUID id);

  @Query(value = "{'_id': ?0, 'cartForms.shopId': ?1}", exists = true)
  Mono<Boolean> existsByCartForms_ShopId(UUID id, UUID shopId);

  @Query(value = "{'_id: ?0, 'cartForms.shopProducts.productId': ?1}", exists = true)
  Mono<Boolean> existsByCartForms_ShopProducts_ProductId(UUID id, UUID productId);

}
