package com.blibli.experience.commandImpl.barterOrder;

import com.blibli.experience.command.barterOrder.GetBarterOrderDetailCommand;
import com.blibli.experience.entity.document.BarterOrder;
import com.blibli.experience.model.response.barterOrder.GetBarterOrderDetailResponse;
import com.blibli.experience.repository.BarterOrderRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
public class GetBarterOrderDetailCommandImpl implements GetBarterOrderDetailCommand {

    private BarterOrderRepository barterOrderRepository;

    @Autowired
    public GetBarterOrderDetailCommandImpl(BarterOrderRepository barterOrderRepository) {
        this.barterOrderRepository = barterOrderRepository;
    }

    @Override
    public Mono<GetBarterOrderDetailResponse> execute(UUID request) {
        return barterOrderRepository.findByBarterOrderId(request)
                .switchIfEmpty(Mono.error(new NotFoundException("Barter Order not found.")))
                .map(this::toResponse);
    }

    private GetBarterOrderDetailResponse toResponse(BarterOrder barterOrder) {
        GetBarterOrderDetailResponse response = new GetBarterOrderDetailResponse();
        BeanUtils.copyProperties(barterOrder, response);
        return response;
    }
}
