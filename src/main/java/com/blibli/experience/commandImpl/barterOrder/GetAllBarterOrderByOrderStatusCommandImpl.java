package com.blibli.experience.commandImpl.barterOrder;

import com.blibli.experience.command.barterOrder.GetAllBarterOrderByOrderStatusCommand;
import com.blibli.experience.entity.document.BarterOrder;
import com.blibli.experience.enums.BarterOrderStatus;
import com.blibli.experience.model.response.barterOrder.GetAllBarterOrderByOrderStatusResponse;
import com.blibli.experience.repository.BarterOrderRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class GetAllBarterOrderByOrderStatusCommandImpl implements GetAllBarterOrderByOrderStatusCommand {

    private BarterOrderRepository barterOrderRepository;

    @Autowired
    public GetAllBarterOrderByOrderStatusCommandImpl(BarterOrderRepository barterOrderRepository) {
        this.barterOrderRepository = barterOrderRepository;
    }

    @Override
    public Mono<List<GetAllBarterOrderByOrderStatusResponse>> execute(BarterOrderStatus request) {
        return barterOrderRepository.findAllByOrderStatus(request)
                .switchIfEmpty(Mono.error(new NotFoundException("Barter order not found.")))
                .map(this::toResponse)
                .collectList();
    }

    private GetAllBarterOrderByOrderStatusResponse toResponse(BarterOrder barterOrder) {
        GetAllBarterOrderByOrderStatusResponse response = new GetAllBarterOrderByOrderStatusResponse();
        BeanUtils.copyProperties(barterOrder, response);
        return response;
    }
}
