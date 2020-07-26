package com.blibli.experience.commandImpl.biddingOrder;

import com.blibli.experience.command.biddingOrder.UpdateBiddingOrderDeliveryReceiptCommand;
import com.blibli.experience.entity.document.BiddingOrder;
import com.blibli.experience.entity.form.ReceiptForm;
import com.blibli.experience.enums.BiddingOrderStatus;
import com.blibli.experience.model.request.biddingOrder.UpdateBiddingOrderDeliveryReceiptRequest;
import com.blibli.experience.model.response.biddingOrder.UpdateBiddingOrderDeliveryReceiptResponse;
import com.blibli.experience.repository.BiddingOrderRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Service
public class UpdateBiddingOrderDeliveryReceiptCommandImpl implements UpdateBiddingOrderDeliveryReceiptCommand {

    private BiddingOrderRepository biddingOrderRepository;

    @Autowired
    public UpdateBiddingOrderDeliveryReceiptCommandImpl(BiddingOrderRepository biddingOrderRepository) {
        this.biddingOrderRepository = biddingOrderRepository;
    }

    @Override
    public Mono<UpdateBiddingOrderDeliveryReceiptResponse> execute(UpdateBiddingOrderDeliveryReceiptRequest request) {
        return biddingOrderRepository.findFirstByBiddingOrderId(request.getBiddingOrderId())
                .switchIfEmpty(Mono.error(new NotFoundException("Bidding Order not found.")))
                .map(biddingOrder -> updateBiddingOrder(biddingOrder, request))
                .flatMap(biddingOrder -> biddingOrderRepository.save(biddingOrder))
                .map(this::toResponse);
    }

    private BiddingOrder updateBiddingOrder(BiddingOrder biddingOrder, UpdateBiddingOrderDeliveryReceiptRequest request) {
        if(checkBiddingOrderStatus(biddingOrder)) {
            ReceiptForm receiptForm = ReceiptForm.builder()
                    .receipt(request.getDeliveryReceipt())
                    .createdAt(LocalDateTime.now())
                    .build();
            biddingOrder.setDeliveryReceipt(receiptForm);
            biddingOrder.setBiddingOrderStatus(BiddingOrderStatus.DELIVERED_TO_BIDDING_OWNER);
            return biddingOrder;
        } else {
            throw new RuntimeException("This order is not paid yet.");
        }
    }

    private Boolean checkBiddingOrderStatus(BiddingOrder biddingOrder) {
        return biddingOrder.getBiddingOrderStatus().equals(BiddingOrderStatus.PAID);
    }

    private UpdateBiddingOrderDeliveryReceiptResponse toResponse(BiddingOrder biddingOrder) {
        UpdateBiddingOrderDeliveryReceiptResponse response = new UpdateBiddingOrderDeliveryReceiptResponse();
        BeanUtils.copyProperties(biddingOrder, response);
        return response;
    }
}
