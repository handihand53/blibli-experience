package com.blibli.experience.commandImpl.biddingPayment;

import com.blibli.experience.command.biddingPayment.PostBiddingPaymentCommand;
import com.blibli.experience.entity.document.BiddingOrder;
import com.blibli.experience.entity.document.BiddingPayment;
import com.blibli.experience.entity.form.BiddingOrderDataForm;
import com.blibli.experience.enums.BiddingOrderStatus;
import com.blibli.experience.model.request.biddingPayment.PostBiddingPaymentRequest;
import com.blibli.experience.model.response.biddingPayment.PostBiddingPaymentResponse;
import com.blibli.experience.repository.BiddingOrderRepository;
import com.blibli.experience.repository.BiddingPaymentRepository;
import com.blibli.experience.repository.PaymentRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class PostBiddingPaymentCommandImpl implements PostBiddingPaymentCommand {

    private BiddingPaymentRepository biddingPaymentRepository;
    private BiddingOrderRepository biddingOrderRepository;

    @Autowired
    public PostBiddingPaymentCommandImpl(BiddingPaymentRepository biddingPaymentRepository, BiddingOrderRepository biddingOrderRepository) {
        this.biddingPaymentRepository = biddingPaymentRepository;
        this.biddingOrderRepository = biddingOrderRepository;
    }

    @Override
    public Mono<PostBiddingPaymentResponse> execute(PostBiddingPaymentRequest request) {
        return biddingOrderRepository.findFirstByBiddingOrderId(request.getBiddingOrderId())
                .switchIfEmpty(Mono.error(new NotFoundException("Bidding Order not found.")))
                .doOnNext(this::updateBiddingOrder)
                .map(biddingOrder -> toBiddingPayment(biddingOrder, request))
                .flatMap(biddingPayment -> biddingPaymentRepository.save(biddingPayment))
                .map(this::toResponse);
    }

    private void updateBiddingOrder(BiddingOrder biddingOrder) {
        biddingOrder.setBiddingOrderStatus(BiddingOrderStatus.PAID);
        biddingOrderRepository.save(biddingOrder).subscribe();
    }

    private BiddingPayment toBiddingPayment(BiddingOrder biddingOrder, PostBiddingPaymentRequest request) {
        BiddingOrderDataForm biddingOrderDataForm = new BiddingOrderDataForm();
        BeanUtils.copyProperties(biddingOrder, biddingOrderDataForm);
        return BiddingPayment.builder()
                .biddingOrderPaymentId(UUID.randomUUID())
                .orderTransactionId(biddingOrder.getOrderTransactionId())
                .biddingOrderDataForm(biddingOrderDataForm)
                .biddingOrderPaymentType(request.getPaymentType())
                .biddingOrderPaymentCreatedAt(LocalDateTime.now())
                .build();
    }

    private PostBiddingPaymentResponse toResponse(BiddingPayment biddingPayment) {
        PostBiddingPaymentResponse response = new PostBiddingPaymentResponse();
        BeanUtils.copyProperties(biddingPayment, response);
        return response;
    }
}
