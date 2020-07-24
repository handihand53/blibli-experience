package com.blibli.experience.commandImpl.order;

import com.blibli.experience.command.order.UpdateOrderDeliveryReceiptCommand;
import com.blibli.experience.entity.document.Order;
import com.blibli.experience.entity.form.ReceiptForm;
import com.blibli.experience.enums.OrderStatus;
import com.blibli.experience.model.request.order.UpdateOrderDeliveryReceiptRequest;
import com.blibli.experience.model.response.order.UpdateOrderDeliveryReceiptResponse;
import com.blibli.experience.repository.OrderRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Service
public class UpdateOrderDeliveryReceiptCommandImpl implements UpdateOrderDeliveryReceiptCommand {

    private OrderRepository orderRepository;

    @Autowired
    public UpdateOrderDeliveryReceiptCommandImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Mono<UpdateOrderDeliveryReceiptResponse> execute(UpdateOrderDeliveryReceiptRequest request) {
        return orderRepository.findFirstByOrderId(request.getOrderId())
                .switchIfEmpty(Mono.error(new NotFoundException("Order not found.")))
                .map(order -> updateOrder(order, request))
                .flatMap(order -> orderRepository.save(order))
                .map(this::toResponse);
    }

    private Order updateOrder(Order order, UpdateOrderDeliveryReceiptRequest request) {
        ReceiptForm receiptForm = ReceiptForm.builder()
                .receipt(request.getDeliveryReceipt())
                .createdAt(LocalDateTime.now())
                .build();
        order.setDeliveryReceipt(receiptForm);
        order.setOrderStatus(OrderStatus.DELIVERED_TO_CONSUMER);
        return order;
    }

    private UpdateOrderDeliveryReceiptResponse toResponse(Order order) {
        UpdateOrderDeliveryReceiptResponse response = new UpdateOrderDeliveryReceiptResponse();
        BeanUtils.copyProperties(order, response);
        return response;
    }
}
