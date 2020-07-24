package com.blibli.experience.commandImpl.order;

import com.blibli.experience.command.order.GetOrderDetailCommand;
import com.blibli.experience.entity.document.Order;
import com.blibli.experience.model.response.order.GetOrderDetailResponse;
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
public class GetOrderDetailCommandImpl implements GetOrderDetailCommand {

    private OrderRepository orderRepository;

    @Autowired
    public GetOrderDetailCommandImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Mono<GetOrderDetailResponse> execute(UUID request) {
        return orderRepository.findFirstByOrderId(request)
                .switchIfEmpty(Mono.error(new NotFoundException("Order not found.")))
                .map(this::toResponse);
    }

    private GetOrderDetailResponse toResponse(Order order) {
        GetOrderDetailResponse response = new GetOrderDetailResponse();
        BeanUtils.copyProperties(order, response);
        return response;
    }
}
