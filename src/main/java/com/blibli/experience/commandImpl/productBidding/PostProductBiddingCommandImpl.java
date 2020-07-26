package com.blibli.experience.commandImpl.productBidding;

import com.blibli.experience.command.productBidding.PostProductBiddingCommand;
import com.blibli.experience.entity.document.ProductBidding;
import com.blibli.experience.entity.document.User;
import com.blibli.experience.entity.form.BiddingForm;
import com.blibli.experience.entity.form.UserDataForm;
import com.blibli.experience.enums.ProductBiddingAvailableStatus;
import com.blibli.experience.enums.UploadEnum;
import com.blibli.experience.model.request.productBidding.PostProductBiddingRequest;
import com.blibli.experience.model.response.productBidding.PostProductBiddingResponse;
import com.blibli.experience.repository.ProductBiddingRepository;
import com.blibli.experience.repository.UserRepository;
import com.blibli.experience.util.FileUploadUtil;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PostProductBiddingCommandImpl implements PostProductBiddingCommand {

    private ProductBiddingRepository productBiddingRepository;
    private UserRepository userRepository;
    private FileUploadUtil fileUploadUtil;

    @Autowired
    public PostProductBiddingCommandImpl(ProductBiddingRepository productBiddingRepository, UserRepository userRepository, FileUploadUtil fileUploadUtil) {
        this.productBiddingRepository = productBiddingRepository;
        this.userRepository = userRepository;
        this.fileUploadUtil = fileUploadUtil;
    }

    @Override
    public Mono<PostProductBiddingResponse> execute(PostProductBiddingRequest request) {
        return userRepository.findFirstByUserId(request.getUserId())
                .switchIfEmpty(Mono.error(new NotFoundException("User not found.")))
                .map(user -> toProductBidding(user, request))
                .flatMap(productBidding -> {
                    try {
                        productBidding.setProductBiddingImagePaths(getImagePaths(request, productBidding));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return productBiddingRepository.save(productBidding);
                })
                .map(this::toResponse);
    }

    private ProductBidding toProductBidding(User user, PostProductBiddingRequest request) {
        if (verifyStartBid(request)) {
            List<BiddingForm> biddingForms = new ArrayList<>();
            UserDataForm userDataForm = new UserDataForm();
            BeanUtils.copyProperties(user, userDataForm);
            ProductBidding productBidding = ProductBidding.builder()
                    .productBiddingId(UUID.randomUUID())
                    .userData(userDataForm)
                    .biddingForms(biddingForms)
                    .availableStatus(ProductBiddingAvailableStatus.AVAILABLE)
                    .productBiddingCreatedAt(LocalDateTime.now())
                    .build();
            BeanUtils.copyProperties(request, productBidding);
            productBidding.setCurrentPrice(productBidding.getStartPrice());
            return productBidding;
        } else {
            throw new RuntimeException("Next bid price is too high.");
        }
    }

    private Boolean verifyStartBid(PostProductBiddingRequest request) {
        return request.getStartPrice() > request.getNextBid();
    }

    private List<String> getImagePaths(PostProductBiddingRequest request, ProductBidding productBidding) throws IOException {
        return fileUploadUtil.uploadAllPhoto(request.getProductImages(), productBidding.getProductBiddingId(), UploadEnum.biddingProductPhoto);
    }

    private PostProductBiddingResponse toResponse(ProductBidding productBidding) {
        PostProductBiddingResponse response = new PostProductBiddingResponse();
        BeanUtils.copyProperties(productBidding, response);
        return response;
    }
}
