package com.blibli.experience.commandImpl.biddingOrder;

import com.blibli.experience.command.biddingOrder.PostBiddingOrderCommand;
import com.blibli.experience.entity.document.BiddingOrder;
import com.blibli.experience.entity.document.ProductBidding;
import com.blibli.experience.entity.form.BiddingForm;
import com.blibli.experience.entity.form.ProductBiddingForm;
import com.blibli.experience.entity.form.UserDataForm;
import com.blibli.experience.enums.BiddingOrderStatus;
import com.blibli.experience.enums.ProductBiddingAvailableStatus;
import com.blibli.experience.model.request.biddingOrder.PostBiddingOrderRequest;
import com.blibli.experience.model.response.biddingOrder.PostBiddingOrderResponse;
import com.blibli.experience.repository.BiddingOrderRepository;
import com.blibli.experience.repository.ProductBiddingRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class PostBiddingOrderCommandImpl implements PostBiddingOrderCommand {

    private BiddingOrderRepository biddingOrderRepository;
    private ProductBiddingRepository productBiddingRepository;

    @Autowired
    public PostBiddingOrderCommandImpl(BiddingOrderRepository biddingOrderRepository, ProductBiddingRepository productBiddingRepository) {
        this.biddingOrderRepository = biddingOrderRepository;
        this.productBiddingRepository = productBiddingRepository;
    }

    @Override
    public Mono<PostBiddingOrderResponse> execute(PostBiddingOrderRequest request) {
        return productBiddingRepository.findFirstByProductBiddingId(request.getProductBiddingId())
                .switchIfEmpty(Mono.error(new NotFoundException("Product Bidding not found.")))
                .map(this::toBiddingOrder)
                .flatMap(biddingOrder -> biddingOrderRepository.save(biddingOrder))
                .map(this::toResponse);
    }

    private BiddingOrder toBiddingOrder(ProductBidding productBidding) {
        if (checkProductBiddingStatus(productBidding)) {
            ProductBiddingForm productBiddingForm = new ProductBiddingForm();
            BeanUtils.copyProperties(productBidding, productBiddingForm);
            RandomStringGenerator generator = new RandomStringGenerator.Builder()
                    .withinRange('0', '9').build();
            updateProductBiddingToOrder(productBidding);
            return BiddingOrder.builder()
                    .biddingOrderId(UUID.randomUUID())
                    .orderTransactionId("bidding-" + generator.generate(8))
                    .biddingOwner(productBidding.getUserData())
                    .biddingWinner(getBiddingWinner(productBidding))
                    .productBiddingForm(productBiddingForm)
                    .paymentId(UUID.randomUUID())
                    .biddingOrderStatus(BiddingOrderStatus.WAITING_FOR_PAYMENT_FROM_BIDDING_WINNER)
                    .biddingOrderCreatedAt(LocalDateTime.now())
                    .build();
        } else {
            throw new RuntimeException("Bidding is not done yet.");
        }
    }

    private Boolean checkProductBiddingStatus(ProductBidding productBidding) {
        return productBidding.getAvailableStatus().equals(ProductBiddingAvailableStatus.FINISHED);
    }

    private void updateProductBiddingToOrder(ProductBidding productBidding) {
        productBidding.setAvailableStatus(ProductBiddingAvailableStatus.ORDER_GENERATED);
        productBiddingRepository.save(productBidding).subscribe();
    }

    private UserDataForm getBiddingWinner(ProductBidding productBidding) {
        UserDataForm userDataForm = new UserDataForm();
        for (BiddingForm biddingForm : productBidding.getBiddingForms()) {
            if (biddingForm.getBiddingPrice().equals(productBidding.getCurrentPrice())) {
                userDataForm = biddingForm.getUserDataForm();
                break;
            }
        }
        return userDataForm;
    }

    private PostBiddingOrderResponse toResponse(BiddingOrder biddingOrder) {
        PostBiddingOrderResponse response = new PostBiddingOrderResponse();
        BeanUtils.copyProperties(biddingOrder, response);
        return response;
    }
}
