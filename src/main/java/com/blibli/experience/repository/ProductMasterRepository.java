package com.blibli.experience.repository;

import com.blibli.experience.entity.document.ProductMaster;
import com.blibli.experience.enums.ProductAvailableStatus;
import com.blibli.experience.enums.ProductCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface ProductMasterRepository extends ReactiveMongoRepository<ProductMaster, UUID> {

  Mono<ProductMaster> findFirstByProductId(UUID id);

  Mono<ProductMaster> findFirstByProductBarcode(String barcode);

  Flux<ProductMaster> findAllByProductNameContaining(String searchKey);

  Flux<ProductMaster> findAllByProductNameContainingAndAvailableStatus(String searchKey, ProductAvailableStatus productAvailableStatus);

  Flux<ProductMaster> findAllByProductCategory(ProductCategory productCategory);

  Flux<ProductMaster> findAllByAvailableStatus(ProductAvailableStatus productAvailableStatus);

  Mono<Long> countAllByProductNameContainingAndAvailableStatus(String searchKey, ProductAvailableStatus productAvailableStatus);

}
