package com.blibli.experience.repository;

import com.blibli.experience.entity.document.BiddingPayment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.UUID;

public interface BiddingPaymentRepository extends ReactiveMongoRepository<BiddingPayment, UUID> {
}
