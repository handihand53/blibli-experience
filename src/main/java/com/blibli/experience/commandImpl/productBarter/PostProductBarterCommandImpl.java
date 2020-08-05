package com.blibli.experience.commandImpl.productBarter;

import com.blibli.experience.command.productBarter.PostProductBarterCommand;
import com.blibli.experience.entity.document.ProductBarter;
import com.blibli.experience.entity.document.User;
import com.blibli.experience.entity.dto.UserDto;
import com.blibli.experience.enums.ProductAvailableStatus;
import com.blibli.experience.enums.UploadEnum;
import com.blibli.experience.model.request.productBarter.PostProductBarterRequest;
import com.blibli.experience.model.response.productBarter.PostProductBarterResponse;
import com.blibli.experience.repository.ProductBarterRepository;
import com.blibli.experience.repository.UserRepository;
import com.blibli.experience.util.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PostProductBarterCommandImpl implements PostProductBarterCommand {

    private ProductBarterRepository productBarterRepository;
    private UserRepository userRepository;
    private FileUploadUtil fileUploadUtil;

    @Autowired
    public PostProductBarterCommandImpl(ProductBarterRepository productBarterRepository, UserRepository userRepository, FileUploadUtil fileUploadUtil) {
        this.productBarterRepository = productBarterRepository;
        this.userRepository = userRepository;
        this.fileUploadUtil = fileUploadUtil;
    }

    @Override
    public Mono<PostProductBarterResponse> execute(PostProductBarterRequest request) {
        UserDto userDto = getUserDataForm(request);
        return Mono.fromCallable(() -> toProductBarter(request, userDto))
                .flatMap(productBarter -> {
                    try {
                        productBarter.setProductBarterImagePaths(getImagePaths(request, productBarter));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return productBarterRepository.save(productBarter);
                })
                .map(this::toResponse);
    }

    private UserDto getUserDataForm(PostProductBarterRequest request) {
        UserDto userDto = new UserDto();
        User user = userRepository.findFirstByUserId(request.getUserId()).block();
        if (user != null) {
            BeanUtils.copyProperties(user, userDto);
            return userDto;
        } else {
            throw new RuntimeException("User not found.");
        }
    }

    private ProductBarter toProductBarter(PostProductBarterRequest request, UserDto userDto) {
        ProductBarter productBarter = ProductBarter.builder()
                .productBarterId(UUID.randomUUID())
                .userData(userDto)
                .availableStatus(ProductAvailableStatus.AVAILABLE)
                .productBarterCreatedAt(LocalDateTime.now())
                .build();
        BeanUtils.copyProperties(request, productBarter);
        return productBarter;
    }

    private List<String> getImagePaths(PostProductBarterRequest request, ProductBarter productBarter) throws IOException {
        return fileUploadUtil.uploadAllPhoto(request.getProductBarterImages(), productBarter.getProductBarterId(), UploadEnum.barterProductPhoto);
    }


    private PostProductBarterResponse toResponse(ProductBarter productBarter) {
        PostProductBarterResponse response = new PostProductBarterResponse();
        BeanUtils.copyProperties(productBarter, response);
        return response;
    }



}
