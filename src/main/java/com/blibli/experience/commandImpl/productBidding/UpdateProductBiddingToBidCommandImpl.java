package com.blibli.experience.commandImpl.productBidding;

import com.blibli.experience.command.productBidding.UpdateProductBiddingToBidCommand;
import com.blibli.experience.entity.document.ProductBidding;
import com.blibli.experience.entity.document.User;
import com.blibli.experience.entity.form.BiddingForm;
import com.blibli.experience.entity.form.UserDataForm;
import com.blibli.experience.enums.ProductAvailableStatus;
import com.blibli.experience.enums.ProductBiddingAvailableStatus;
import com.blibli.experience.model.request.productBidding.UpdateProductBiddingToBidRequest;
import com.blibli.experience.model.response.productBidding.UpdateProductBiddingToBidResponse;
import com.blibli.experience.repository.ProductBiddingRepository;
import com.blibli.experience.repository.UserRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class UpdateProductBiddingToBidCommandImpl implements UpdateProductBiddingToBidCommand {

    private ProductBiddingRepository productBiddingRepository;
    private UserRepository userRepository;

    @Autowired
    public UpdateProductBiddingToBidCommandImpl(ProductBiddingRepository productBiddingRepository, UserRepository userRepository) {
        this.productBiddingRepository = productBiddingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Mono<UpdateProductBiddingToBidResponse> execute(UpdateProductBiddingToBidRequest request) {
        UserDataForm userDataForm = getUserDataForm(request);
        return productBiddingRepository.findFirstByProductBiddingId(request.getProductBiddingId())
                .switchIfEmpty(Mono.error(new NotFoundException("Product Bidding not found.")))
                .map(productBidding -> updateProductBidding(productBidding, userDataForm, request))
                .flatMap(productBidding -> productBiddingRepository.save(productBidding))
                .map(this::toResponse);
    }

    private UserDataForm getUserDataForm(UpdateProductBiddingToBidRequest request) {
        User user = userRepository.findFirstByUserId(request.getUserId()).block();
        if(user != null) {
            UserDataForm userDataForm = new UserDataForm();
            BeanUtils.copyProperties(user, userDataForm);
            return userDataForm;
        } else {
            throw new RuntimeException("User not found.");
        }
    }

    private ProductBidding updateProductBidding(ProductBidding productBidding, UserDataForm userDataForm, UpdateProductBiddingToBidRequest request) {
        if(checkBidLessThanCurrentPrice(productBidding, request) && checkEndDate(productBidding)
                && checkBidModulus(productBidding, request) && checkStatus(productBidding)) {
            BiddingForm biddingForm = BiddingForm.builder()
                    .userDataForm(userDataForm)
                    .biddingPrice(request.getBid())
                    .createdAt(LocalDateTime.now())
                    .build();
            productBidding.setCurrentPrice(request.getBid());
            List<BiddingForm> biddingForms = productBidding.getBiddingForms();
            biddingForms.add(biddingForm);
            productBidding.setBiddingForms(biddingForms);
            productBidding.setLastBidDate(LocalDateTime.now());
            return productBidding;
        } else {
            throw new RuntimeException("Bid rejected. Your bid is less than, not modulus with bid request, or already ended.")  ;
        }
    }

    private Boolean checkBidLessThanCurrentPrice(ProductBidding productBidding, UpdateProductBiddingToBidRequest request) {
        return request.getBid() < productBidding.getCurrentPrice();
    }

    private Boolean checkEndDate(ProductBidding productBidding) {
        return LocalDateTime.now().isBefore(productBidding.getCloseBidDate());
    }

    private Boolean checkBidModulus(ProductBidding productBidding, UpdateProductBiddingToBidRequest request) {
        return request.getBid() % productBidding.getNextBid() == 0;
    }

    private Boolean checkStatus(ProductBidding productBidding) {
        return productBidding.getAvailableStatus().equals(ProductBiddingAvailableStatus.AVAILABLE);
    }

    private UpdateProductBiddingToBidResponse toResponse(ProductBidding productBidding) {
        UpdateProductBiddingToBidResponse response = new UpdateProductBiddingToBidResponse();
        BeanUtils.copyProperties(productBidding, response);
        return response;
    }
}
