package com.blibli.experience.repository;

import com.blibli.experience.entity.document.BarterOrder;
import com.blibli.experience.enums.BarterItemStatus;
import com.blibli.experience.enums.BarterOrderStatus;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BarterOrderRepository extends ReactiveMongoRepository<BarterOrder, UUID> {

    Mono<BarterOrder> findByBarterOrderId(UUID barterOrderId);

    Flux<BarterOrder> findAllByBuyerItemStatusOrSellerItemStatus(BarterItemStatus buyerItemStatus, BarterItemStatus sellerItemStatus);

    Flux<BarterOrder> findAllBySellerData_UserId(UUID userId);

    Flux<BarterOrder> findAllByBuyerData_UserId(UUID userId);

    Flux<BarterOrder> findAllByOrderStatus(BarterOrderStatus barterOrderStatus);

}
