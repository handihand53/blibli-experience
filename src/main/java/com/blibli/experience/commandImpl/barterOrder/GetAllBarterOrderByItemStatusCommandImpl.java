package com.blibli.experience.commandImpl.barterOrder;

import com.blibli.experience.command.barterOrder.GetAllBarterOrderByItemStatusCommand;
import com.blibli.experience.entity.document.BarterOrder;
import com.blibli.experience.model.request.barterOrder.GetAllBarterOrderByItemStatusRequest;
import com.blibli.experience.model.response.barterOrder.GetAllBarterOrderByItemStatusResponse;
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
public class GetAllBarterOrderByItemStatusCommandImpl implements GetAllBarterOrderByItemStatusCommand {

    private BarterOrderRepository barterOrderRepository;

    @Autowired
    public GetAllBarterOrderByItemStatusCommandImpl(BarterOrderRepository barterOrderRepository) {
        this.barterOrderRepository = barterOrderRepository;
    }

    @Override
    public Mono<List<GetAllBarterOrderByItemStatusResponse>> execute(GetAllBarterOrderByItemStatusRequest request) {
        return barterOrderRepository.findAllByBuyerItemStatusOrSellerItemStatus(request.getBarterItemStatus(), request.getBarterItemStatus())
                .switchIfEmpty(Mono.error(new NotFoundException("Barter order not found")))
                .map(this::toResponse)
                .collectList();
    }

    private GetAllBarterOrderByItemStatusResponse toResponse(BarterOrder barterOrder) {
        GetAllBarterOrderByItemStatusResponse response = new GetAllBarterOrderByItemStatusResponse();
        BeanUtils.copyProperties(barterOrder, response);
        return response;
    }
}
