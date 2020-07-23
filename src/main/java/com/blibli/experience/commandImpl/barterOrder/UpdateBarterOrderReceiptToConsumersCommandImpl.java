package com.blibli.experience.commandImpl.barterOrder;

import com.blibli.experience.command.barterOrder.UpdateBarterOrderReceiptToConsumersCommand;
import com.blibli.experience.entity.document.BarterOrder;
import com.blibli.experience.entity.form.ReceiptForm;
import com.blibli.experience.enums.BarterItemStatus;
import com.blibli.experience.enums.BarterOrderStatus;
import com.blibli.experience.enums.BarterRoleEnum;
import com.blibli.experience.model.request.barterOrder.UpdateBarterOrderReceiptToConsumersRequest;
import com.blibli.experience.model.response.barterOrder.UpdateBarterOrderReceiptToConsumersResponse;
import com.blibli.experience.repository.BarterOrderRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Service
public class UpdateBarterOrderReceiptToConsumersCommandImpl implements UpdateBarterOrderReceiptToConsumersCommand {

    private BarterOrderRepository barterOrderRepository;

    @Autowired
    public UpdateBarterOrderReceiptToConsumersCommandImpl(BarterOrderRepository barterOrderRepository) {
        this.barterOrderRepository = barterOrderRepository;
    }

    @Override
    public Mono<UpdateBarterOrderReceiptToConsumersResponse> execute(UpdateBarterOrderReceiptToConsumersRequest request) {
        return barterOrderRepository.findByBarterOrderId(request.getBarterOrderId())
                .switchIfEmpty(Mono.error(new NotFoundException("Barter Order not found.")))
                .map(barterOrder -> updateBarterOrder(barterOrder, request))
                .flatMap(barterOrder -> barterOrderRepository.save(barterOrder))
                .map(this::toResponse);
    }

    private BarterOrder updateBarterOrder(BarterOrder barterOrder, UpdateBarterOrderReceiptToConsumersRequest request) {
        ReceiptForm receiptForm = ReceiptForm.builder()
                .receipt(request.getReceipt())
                .createdAt(LocalDateTime.now())
                .build();
        if (request.getBarterRoleEnum().equals(BarterRoleEnum.SELLER)) {
            barterOrder.setWarehouseToSellerReceipt(receiptForm);
            barterOrder.setSellerItemStatus(BarterItemStatus.SENT_TO_CONSUMERS);
        } else if (request.getBarterRoleEnum().equals(BarterRoleEnum.BUYER)) {
            barterOrder.setWarehouseToBuyerReceipt(receiptForm);
            barterOrder.setBuyerItemStatus(BarterItemStatus.SENT_TO_CONSUMERS);
        }
        if (barterOrder.getSellerItemStatus().equals(BarterItemStatus.SENT_TO_CONSUMERS) &&
                barterOrder.getBuyerItemStatus().equals(BarterItemStatus.SENT_TO_CONSUMERS)) {
            barterOrder.setOrderStatus(BarterOrderStatus.SENT_TO_CONSUMERS);
        }
        return barterOrder;
    }

    private UpdateBarterOrderReceiptToConsumersResponse toResponse(BarterOrder barterOrder) {
        UpdateBarterOrderReceiptToConsumersResponse response = new UpdateBarterOrderReceiptToConsumersResponse();
        BeanUtils.copyProperties(barterOrder, response);
        return response;
    }
}
