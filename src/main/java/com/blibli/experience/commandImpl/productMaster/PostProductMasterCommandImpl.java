package com.blibli.experience.commandImpl.productMaster;

import com.blibli.experience.command.productMaster.PostProductMasterCommand;
import com.blibli.experience.entity.document.ProductMaster;
import com.blibli.experience.enums.ProductAvailableStatus;
import com.blibli.experience.model.request.productMaster.PostProductMasterRequest;
import com.blibli.experience.model.response.productMaster.PostProductMasterResponse;
import com.blibli.experience.repository.ProductMasterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class PostProductMasterCommandImpl implements PostProductMasterCommand {

    private ProductMasterRepository productMasterRepository;

    @Autowired
    public PostProductMasterCommandImpl(ProductMasterRepository productMasterRepository) {
        this.productMasterRepository = productMasterRepository;
    }

    @Override
    public Mono<PostProductMasterResponse> execute(PostProductMasterRequest request) {
        return Mono.fromCallable(() -> toProductMaster(request))
                .flatMap(productMaster -> productMasterRepository.save(productMaster))
                .map(this::toResponse);
    }

    private ProductMaster toProductMaster(PostProductMasterRequest request) {
        ProductMaster productMaster = ProductMaster.builder()
                .productId(UUID.randomUUID())
                .productCreatedAt(LocalDateTime.now())
                .availableStatus(ProductAvailableStatus.NOT_AVAILABLE)
                .build();
        BeanUtils.copyProperties(request, productMaster);
        return productMaster;
    }

    private PostProductMasterResponse toResponse(ProductMaster productMaster) {
        PostProductMasterResponse response = new PostProductMasterResponse();
        BeanUtils.copyProperties(productMaster, response);
        return response;
    }

}
