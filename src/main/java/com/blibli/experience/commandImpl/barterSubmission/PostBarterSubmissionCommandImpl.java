package com.blibli.experience.commandImpl.barterSubmission;

import com.blibli.experience.command.barterSubmission.PostBarterSubmissionCommand;
import com.blibli.experience.entity.document.BarterSubmission;
import com.blibli.experience.entity.document.ProductBarter;
import com.blibli.experience.entity.document.User;
import com.blibli.experience.entity.dto.ProductBarterDto;
import com.blibli.experience.entity.dto.UserDto;
import com.blibli.experience.enums.UploadEnum;
import com.blibli.experience.model.request.barterSubmission.PostBarterSubmissionRequest;
import com.blibli.experience.model.response.barterSubmission.PostBarterSubmissionResponse;
import com.blibli.experience.repository.BarterSubmissionRepository;
import com.blibli.experience.repository.ProductBarterRepository;
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
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PostBarterSubmissionCommandImpl implements PostBarterSubmissionCommand {

    private BarterSubmissionRepository barterSubmissionRepository;
    private ProductBarterRepository productBarterRepository;
    private UserRepository userRepository;
    private FileUploadUtil fileUploadUtil;

    @Autowired
    public PostBarterSubmissionCommandImpl(BarterSubmissionRepository barterSubmissionRepository, ProductBarterRepository productBarterRepository, UserRepository userRepository, FileUploadUtil fileUploadUtil) {
        this.barterSubmissionRepository = barterSubmissionRepository;
        this.productBarterRepository = productBarterRepository;
        this.userRepository = userRepository;
        this.fileUploadUtil = fileUploadUtil;
    }

    @Override
    public Mono<PostBarterSubmissionResponse> execute(PostBarterSubmissionRequest request) {
        UserDto userDto = getUserDataForm(request);
        return productBarterRepository.findByProductBarterId(request.getProductBarterId())
                .switchIfEmpty(Mono.error(new NotFoundException("Target barter not found.")))
                .map(productBarter -> toBarterSubmission(productBarter, userDto, request))
                .flatMap(barterSubmission -> {
                    try {
                        barterSubmission.setBarterSubmissionImagePaths(getImagePaths(request, barterSubmission));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return barterSubmissionRepository.save(barterSubmission);
                })
                .map(this::toResponse);
    }

    private UserDto getUserDataForm(PostBarterSubmissionRequest request) {
        UserDto userDto = new UserDto();
        User user = userRepository.findFirstByUserId(request.getUserId()).block();
        if (user != null) {
            BeanUtils.copyProperties(user, userDto);
            return userDto;
        } else {
            throw new RuntimeException("User not found.");
        }
    }

    private BarterSubmission toBarterSubmission(ProductBarter productBarter, UserDto dataForm,
                                                PostBarterSubmissionRequest request) {
        ProductBarterDto productBarterDto = getProductBarterDataForm(productBarter);
        BarterSubmission barterSubmission = BarterSubmission.builder()
                .barterSubmissionId(UUID.randomUUID())
                .userData(dataForm)
                .barterSubmissionTargetBarter(productBarterDto)
                .barterSubmissionCreatedAt(LocalDateTime.now())
                .build();
        BeanUtils.copyProperties(request, barterSubmission);
        return barterSubmission;
    }

    private ProductBarterDto getProductBarterDataForm(ProductBarter productBarter) {
        ProductBarterDto dataForm = new ProductBarterDto();
        BeanUtils.copyProperties(productBarter, dataForm);
        return dataForm;
    }

    private List<String> getImagePaths(PostBarterSubmissionRequest request, BarterSubmission barterSubmission) throws IOException {
        return fileUploadUtil.uploadAllPhoto(request.getBarterSubmissionImages(), barterSubmission.getBarterSubmissionId(), UploadEnum.barterSubmissionPhoto);
    }

    private PostBarterSubmissionResponse toResponse(BarterSubmission barterSubmission) {
        PostBarterSubmissionResponse response = new PostBarterSubmissionResponse();
        BeanUtils.copyProperties(barterSubmission, response);
        return response;
    }


}
