package com.blibli.experience.repository;

import com.blibli.experience.entity.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.awt.print.Pageable;
import java.util.UUID;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, UUID> {

  Flux<Product> findAllByStockGreaterThanEqual(Integer minimumStock);

}
