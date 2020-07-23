package com.blibli.experience.commandImpl.barterOrder;

import com.blibli.experience.command.barterOrder.UpdateBarterOrderReceiptToVerifiedCommand;
import com.blibli.experience.entity.document.BarterOrder;
import com.blibli.experience.enums.BarterItemStatus;
import com.blibli.experience.enums.BarterOrderStatus;
import com.blibli.experience.enums.BarterRoleEnum;
import com.blibli.experience.model.request.barterOrder.UpdateBarterOrderReceiptToVerifiedRequest;
import com.blibli.experience.model.response.barterOrder.UpdateBarterOrderReceiptToVerifiedResponse;
import com.blibli.experience.repository.BarterOrderRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class UpdateBarterOrderReceiptToVerifiedCommandImpl implements UpdateBarterOrderReceiptToVerifiedCommand {

    private BarterOrderRepository barterOrderRepository;

    @Autowired
    public UpdateBarterOrderReceiptToVerifiedCommandImpl(BarterOrderRepository barterOrderRepository) {
        this.barterOrderRepository = barterOrderRepository;
    }

    @Override
    public Mono<UpdateBarterOrderReceiptToVerifiedResponse> execute(UpdateBarterOrderReceiptToVerifiedRequest request) {
        return barterOrderRepository.findByBarterOrderId(request.getBarterOrderId())
                .switchIfEmpty(Mono.error(new NotFoundException("Barter Order not found.")))
                .map(barterOrder -> updateBarterOrder(barterOrder, request))
                .flatMap(barterOrder -> barterOrderRepository.save(barterOrder))
                .map(this::toResponse);
    }

    public BarterOrder updateBarterOrder(BarterOrder barterOrder, UpdateBarterOrderReceiptToVerifiedRequest request) {
        if (request.getBarterRoleEnum().equals(BarterRoleEnum.SELLER)) {
            barterOrder.setSellerItemStatus(BarterItemStatus.VERIFIED_IN_WAREHOUSE);
        } else if (request.getBarterRoleEnum().equals(BarterRoleEnum.BUYER)) {
            barterOrder.setBuyerItemStatus(BarterItemStatus.VERIFIED_IN_WAREHOUSE);
        }
        if (barterOrder.getSellerItemStatus().equals(BarterItemStatus.VERIFIED_IN_WAREHOUSE) &&
                barterOrder.getBuyerItemStatus().equals(BarterItemStatus.VERIFIED_IN_WAREHOUSE)) {
            barterOrder.setOrderStatus(BarterOrderStatus.VERIFIED_IN_WAREHOUSE);
        }
        return barterOrder;
    }

    public UpdateBarterOrderReceiptToVerifiedResponse toResponse(BarterOrder barterOrder) {
        UpdateBarterOrderReceiptToVerifiedResponse response = new UpdateBarterOrderReceiptToVerifiedResponse();
        BeanUtils.copyProperties(barterOrder, response);
        return response;
    }
}
