package com.blibli.experience.commandImpl.order;

import com.blibli.experience.command.order.GetAllOrderByShopIdCommand;
import com.blibli.experience.entity.document.Order;
import com.blibli.experience.model.response.order.GetAllOrderByShopIdResponse;
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
public class GetAllOrderByShopIdCommandImpl implements GetAllOrderByShopIdCommand {

    private OrderRepository orderRepository;

    @Autowired
    public GetAllOrderByShopIdCommandImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Mono<List<GetAllOrderByShopIdResponse>> execute(UUID request) {
        return orderRepository.findAllByShopForm_ShopId(request)
                .switchIfEmpty(Mono.error(new NotFoundException("Order not found.")))
                .map(this::toResponse)
                .collectList();
    }

    private GetAllOrderByShopIdResponse toResponse(Order order) {
        GetAllOrderByShopIdResponse response = new GetAllOrderByShopIdResponse();
        BeanUtils.copyProperties(order, response);
        return response;
    }
}
