package com.blibli.experience.commandImpl.productBarter;

import com.blibli.experience.command.productBarter.PostProductBarterCommand;
import com.blibli.experience.entity.document.ProductBarter;
import com.blibli.experience.entity.document.User;
import com.blibli.experience.entity.form.UserDataForm;
import com.blibli.experience.enums.ProductAvailableStatus;
import com.blibli.experience.model.request.productBarter.PostProductBarterRequest;
import com.blibli.experience.model.response.productBarter.PostProductBarterResponse;
import com.blibli.experience.repository.ProductBarterRepository;
import com.blibli.experience.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class PostProductBarterCommandImpl implements PostProductBarterCommand {

    private ProductBarterRepository productBarterRepository;
    private UserRepository userRepository;

    @Autowired
    public PostProductBarterCommandImpl(ProductBarterRepository productBarterRepository, UserRepository userRepository) {
        this.productBarterRepository = productBarterRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Mono<PostProductBarterResponse> execute(PostProductBarterRequest request) {
        return userRepository.findFirstByUserId(request.getUserId())
                .map(this::toResponse)
                .map(response -> saveProductBarter(request, response));
    }

    private PostProductBarterResponse toResponse(User user) {
        PostProductBarterResponse response = new PostProductBarterResponse();
        UserDataForm userDataForm = new UserDataForm();
        BeanUtils.copyProperties(user, userDataForm);
        response.setUserData(userDataForm);
        return response;
    }

    private PostProductBarterResponse saveProductBarter(PostProductBarterRequest request, PostProductBarterResponse response) {
        ProductBarter productBarter = ProductBarter.builder()
                .productBarterId(UUID.randomUUID())
                .userData(response.getUserData())
                .availableStatus(ProductAvailableStatus.AVAILABLE)
                .productBarterCreatedAt(LocalDateTime.now())
                .build();
        BeanUtils.copyProperties(request, productBarter);
        productBarterRepository.save(productBarter).subscribe();
        BeanUtils.copyProperties(productBarter, response);
        return response;
    }

}
