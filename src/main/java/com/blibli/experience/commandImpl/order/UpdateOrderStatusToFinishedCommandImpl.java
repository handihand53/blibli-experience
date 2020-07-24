package com.blibli.experience.commandImpl.order;

import com.blibli.experience.command.order.UpdateOrderStatusToFinishedCommand;
import com.blibli.experience.entity.document.Order;
import com.blibli.experience.enums.OrderStatus;
import com.blibli.experience.model.response.order.UpdateOrderStatusToFinishedResponse;
import com.blibli.experience.repository.OrderRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
public class UpdateOrderStatusToFinishedCommandImpl implements UpdateOrderStatusToFinishedCommand {

    private OrderRepository orderRepository;

    @Autowired
    public UpdateOrderStatusToFinishedCommandImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Mono<UpdateOrderStatusToFinishedResponse> execute(UUID request) {
        return orderRepository.findFirstByOrderId(request)
                .switchIfEmpty(Mono.error(new NotFoundException("Order not found.")))
                .map(this::updateOrder)
                .flatMap(order -> orderRepository.save(order))
                .map(this::toResponse);
    }

    private Order updateOrder(Order order) {
        order.setOrderStatus(OrderStatus.FINISHED);
        return order;
    }

    private UpdateOrderStatusToFinishedResponse toResponse(Order order) {
        UpdateOrderStatusToFinishedResponse response = new UpdateOrderStatusToFinishedResponse();
        BeanUtils.copyProperties(order, response);
        return response;
    }
}
