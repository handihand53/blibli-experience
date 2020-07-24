package com.blibli.experience.repository;

import com.blibli.experience.entity.document.Payment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.UUID;

public interface PaymentRepository extends ReactiveMongoRepository<Payment, UUID> {
}
