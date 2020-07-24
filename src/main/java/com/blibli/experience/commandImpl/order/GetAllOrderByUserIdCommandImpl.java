package com.blibli.experience.commandImpl.order;

import com.blibli.experience.command.order.GetAllOrderByUserIdCommand;
import com.blibli.experience.entity.document.Order;
import com.blibli.experience.model.response.order.GetAllOrderByUserIdResponse;
import com.blibli.experience.repository.OrderRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class GetAllOrderByUserIdCommandImpl implements GetAllOrderByUserIdCommand {

    private OrderRepository orderRepository;

    @Autowired
    public GetAllOrderByUserIdCommandImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Mono<List<GetAllOrderByUserIdResponse>> execute(UUID request) {
        return orderRepository.findAllByUserDataForm_UserId(request)
                .switchIfEmpty(Mono.error(new NotFoundException("Order not found.")))
                .map(this::toResponse)
                .collectList();
    }

    private GetAllOrderByUserIdResponse toResponse(Order order) {
        GetAllOrderByUserIdResponse response = new GetAllOrderByUserIdResponse();
        BeanUtils.copyProperties(order, response);
        return response;
    }
}
