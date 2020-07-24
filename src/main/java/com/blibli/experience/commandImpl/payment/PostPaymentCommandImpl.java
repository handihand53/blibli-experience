package com.blibli.experience.commandImpl.payment;

import com.blibli.experience.command.payment.PostPaymentCommand;
import com.blibli.experience.entity.document.Order;
import com.blibli.experience.entity.document.Payment;
import com.blibli.experience.entity.form.OrderDataForm;
import com.blibli.experience.enums.OrderStatus;
import com.blibli.experience.model.request.payment.PostPaymentRequest;
import com.blibli.experience.model.response.payment.PostPaymentResponse;
import com.blibli.experience.repository.OrderRepository;
import com.blibli.experience.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Service
public class PostPaymentCommandImpl implements PostPaymentCommand {

    private PaymentRepository paymentRepository;
    private OrderRepository orderRepository;

    @Autowired
    public PostPaymentCommandImpl(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Mono<PostPaymentResponse> execute(PostPaymentRequest request) {
        return orderRepository.findFirstByOrderId(request.getOrderId())
                .doOnNext(this::updateOrder)
                .map(order -> toPayment(order, request))
                .flatMap(payment -> paymentRepository.save(payment))
                .map(this::toResponse);
    }

    private void updateOrder(Order order) {
        order.setOrderStatus(OrderStatus.PAID);
        orderRepository.save(order).subscribe();
    }

    private Payment toPayment(Order order, PostPaymentRequest request) {
        OrderDataForm orderDataForm = new OrderDataForm();
        BeanUtils.copyProperties(order, orderDataForm);
        return Payment.builder()
                .paymentId(order.getPaymentId())
                .orderTransactionId(order.getOrderTransactionId())
                .orderDataForm(orderDataForm)
                .paymentType(request.getPaymentType())
                .paymentCreatedAt(LocalDateTime.now())
                .build();
    }


    private PostPaymentResponse toResponse(Payment payment) {
        PostPaymentResponse response = new PostPaymentResponse();
        BeanUtils.copyProperties(payment, response);
        return response;
    }
}
