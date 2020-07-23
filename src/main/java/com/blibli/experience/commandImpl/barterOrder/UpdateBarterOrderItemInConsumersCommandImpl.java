package com.blibli.experience.commandImpl.barterOrder;

import com.blibli.experience.command.barterOrder.UpdateBarterOrderItemInConsumersCommand;
import com.blibli.experience.entity.document.BarterOrder;
import com.blibli.experience.enums.BarterItemStatus;
import com.blibli.experience.enums.BarterOrderStatus;
import com.blibli.experience.enums.BarterRoleEnum;
import com.blibli.experience.model.request.barterOrder.UpdateBarterOrderItemInConsumersRequest;
import com.blibli.experience.model.response.barterOrder.UpdateBarterOrderItemInConsumersResponse;
import com.blibli.experience.repository.BarterOrderRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class UpdateBarterOrderItemInConsumersCommandImpl implements UpdateBarterOrderItemInConsumersCommand {

    private BarterOrderRepository barterOrderRepository;

    @Autowired
    public UpdateBarterOrderItemInConsumersCommandImpl(BarterOrderRepository barterOrderRepository) {
        this.barterOrderRepository = barterOrderRepository;
    }

    @Override
    public Mono<UpdateBarterOrderItemInConsumersResponse> execute(UpdateBarterOrderItemInConsumersRequest request) {
        return barterOrderRepository.findByBarterOrderId(request.getBarterOrderId())
                .switchIfEmpty(Mono.error(new NotFoundException("Barter Order not found.")))
                .map(barterOrder -> updateBarterOrder(barterOrder, request))
                .flatMap(barterOrder -> barterOrderRepository.save(barterOrder))
                .map(this::toResponse);
    }

    private BarterOrder updateBarterOrder(BarterOrder barterOrder, UpdateBarterOrderItemInConsumersRequest request) {
        if (request.getBarterRoleEnum().equals(BarterRoleEnum.SELLER)) {
            barterOrder.setSellerItemStatus(BarterItemStatus.IN_CONSUMERS);
        } else if (request.getBarterRoleEnum().equals(BarterRoleEnum.BUYER)) {
            barterOrder.setBuyerItemStatus(BarterItemStatus.IN_CONSUMERS);
        }
        if (barterOrder.getSellerItemStatus().equals(BarterItemStatus.IN_CONSUMERS) &&
                barterOrder.getBuyerItemStatus().equals(BarterItemStatus.IN_CONSUMERS)) {
            barterOrder.setOrderStatus(BarterOrderStatus.FINISHED);
        }
        return barterOrder;
    }

    private UpdateBarterOrderItemInConsumersResponse toResponse(BarterOrder barterOrder) {
        UpdateBarterOrderItemInConsumersResponse response = new UpdateBarterOrderItemInConsumersResponse();
        BeanUtils.copyProperties(barterOrder, response);
        return response;
    }
}
