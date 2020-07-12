package com.blibli.experience.repository;

import com.blibli.experience.entity.document.ProductMaster;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface ProductMasterRepository extends ReactiveMongoRepository<ProductMaster, UUID> {

  Mono<ProductMaster> findFirstByProductId(UUID id);

  Mono<ProductMaster> findFirstByProductBarcode(String barcode);

}
