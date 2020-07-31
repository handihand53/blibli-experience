package com.blibli.experience.commandImpl.productMaster;

import com.blibli.experience.command.productMaster.PostProductMasterCommand;
import com.blibli.experience.entity.document.ProductMaster;
import com.blibli.experience.enums.ProductAvailableStatus;
import com.blibli.experience.enums.UploadEnum;
import com.blibli.experience.model.request.productMaster.PostProductMasterRequest;
import com.blibli.experience.model.response.productMaster.PostProductMasterResponse;
import com.blibli.experience.repository.ProductMasterRepository;
import com.blibli.experience.util.FileUploadUtil;
import com.blibli.experience.util.QrGeneratorUtil;
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
public class PostProductMasterCommandImpl implements PostProductMasterCommand {

    private ProductMasterRepository productMasterRepository;
    private FileUploadUtil fileUploadUtil;
    private QrGeneratorUtil qrGeneratorUtil;

    @Autowired
    public PostProductMasterCommandImpl(ProductMasterRepository productMasterRepository, FileUploadUtil fileUploadUtil, QrGeneratorUtil qrGeneratorUtil) {
        this.productMasterRepository = productMasterRepository;
        this.fileUploadUtil = fileUploadUtil;
        this.qrGeneratorUtil = qrGeneratorUtil;
    }

    @Override
    public Mono<PostProductMasterResponse> execute(PostProductMasterRequest request) {
        return Mono.fromCallable(() -> toProductMaster(request))
                .flatMap(productMaster -> {
                    try {
                        productMaster.setProductImagePaths(getImagePaths(request, productMaster));
                        productMaster.setQrImagePath(qrGeneratorUtil.generateQR(productMaster.getProductId().toString()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return productMasterRepository.save(productMaster);
                })
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

    private List<String> getImagePaths(PostProductMasterRequest request, ProductMaster productMaster) throws IOException {
        return fileUploadUtil.uploadAllPhoto(request.getProductImage(), productMaster.getProductId(), UploadEnum.productPhoto);
    }

    private PostProductMasterResponse toResponse(ProductMaster productMaster) {
        PostProductMasterResponse response = new PostProductMasterResponse();
        BeanUtils.copyProperties(productMaster, response);
        return response;
    }

}
