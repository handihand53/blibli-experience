package com.blibli.experience.commandImpl.barterOrder;

import com.blibli.experience.command.barterOrder.UpdateBarterOrderReceiptToWarehouseCommand;
import com.blibli.experience.entity.document.BarterOrder;
import com.blibli.experience.entity.form.ReceiptForm;
import com.blibli.experience.enums.BarterItemStatus;
import com.blibli.experience.enums.BarterOrderStatus;
import com.blibli.experience.enums.BarterRoleEnum;
import com.blibli.experience.model.request.barterOrder.UpdateBarterOrderReceiptToWarehouseRequest;
import com.blibli.experience.model.response.barterOrder.UpdateBarterOrderReceiptToWarehouseResponse;
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
public class UpdateBarterOrderReceiptToWarehouseCommandImpl implements UpdateBarterOrderReceiptToWarehouseCommand {

    private BarterOrderRepository barterOrderRepository;

    @Autowired
    public UpdateBarterOrderReceiptToWarehouseCommandImpl(BarterOrderRepository barterOrderRepository) {
        this.barterOrderRepository = barterOrderRepository;
    }

    @Override
    public Mono<UpdateBarterOrderReceiptToWarehouseResponse> execute(UpdateBarterOrderReceiptToWarehouseRequest request) {
        return barterOrderRepository.findByBarterOrderId(request.getBarterOrderId())
                .switchIfEmpty(Mono.error(new NotFoundException("Barter Order not found.")))
                .map(barterOrder -> updateBarterOrder(barterOrder, request))
                .flatMap(barterOrder -> barterOrderRepository.save(barterOrder))
                .map(this::toResponse);
    }

    private BarterOrder updateBarterOrder(BarterOrder barterOrder, UpdateBarterOrderReceiptToWarehouseRequest request) {
        ReceiptForm receiptForm = ReceiptForm.builder()
                .receipt(request.getReceipt())
                .createdAt(LocalDateTime.now())
                .build();
        if(request.getBarterRoleEnum().equals(BarterRoleEnum.SELLER)) {
            barterOrder.setSellerToWarehouseReceipt(receiptForm);
            barterOrder.setSellerItemStatus(BarterItemStatus.SENT_TO_WAREHOUSE);
        } else if(request.getBarterRoleEnum().equals(BarterRoleEnum.BUYER)) {
            barterOrder.setBuyerToWarehouseReceipt(receiptForm);
            barterOrder.setBuyerItemStatus(BarterItemStatus.SENT_TO_WAREHOUSE);
        }
        if(barterOrder.getSellerItemStatus().equals(BarterItemStatus.SENT_TO_WAREHOUSE) &&
                barterOrder.getBuyerItemStatus().equals(BarterItemStatus.SENT_TO_WAREHOUSE) ) {
            barterOrder.setOrderStatus(BarterOrderStatus.SENT_TO_WAREHOUSE);
        }
        return barterOrder;
    }

    private UpdateBarterOrderReceiptToWarehouseResponse toResponse(BarterOrder barterOrder) {
        UpdateBarterOrderReceiptToWarehouseResponse response = new UpdateBarterOrderReceiptToWarehouseResponse();
        BeanUtils.copyProperties(barterOrder, response);
        return response;
    }
}
