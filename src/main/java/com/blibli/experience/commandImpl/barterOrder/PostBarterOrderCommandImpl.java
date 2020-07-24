package com.blibli.experience.commandImpl.barterOrder;

import com.blibli.experience.command.barterOrder.PostBarterOrderCommand;
import com.blibli.experience.entity.document.BarterOrder;
import com.blibli.experience.entity.document.BarterSubmission;
import com.blibli.experience.entity.document.ProductBarter;
import com.blibli.experience.entity.form.BarterSubmissionDataForm;
import com.blibli.experience.entity.form.ProductBarterDataForm;
import com.blibli.experience.enums.BarterItemStatus;
import com.blibli.experience.enums.BarterOrderStatus;
import com.blibli.experience.enums.ProductAvailableStatus;
import com.blibli.experience.model.request.barterOrder.PostBarterOrderRequest;
import com.blibli.experience.model.response.barterOrder.PostBarterOrderResponse;
import com.blibli.experience.repository.BarterOrderRepository;
import com.blibli.experience.repository.BarterSubmissionRepository;
import com.blibli.experience.repository.ProductBarterRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class PostBarterOrderCommandImpl implements PostBarterOrderCommand {

    private ProductBarterRepository productBarterRepository;
    private BarterSubmissionRepository barterSubmissionRepository;
    private BarterOrderRepository barterOrderRepository;

    @Autowired
    public PostBarterOrderCommandImpl(ProductBarterRepository productBarterRepository, BarterSubmissionRepository barterSubmissionRepository, BarterOrderRepository barterOrderRepository) {
        this.productBarterRepository = productBarterRepository;
        this.barterSubmissionRepository = barterSubmissionRepository;
        this.barterOrderRepository = barterOrderRepository;
    }

    @Override
    public Mono<PostBarterOrderResponse> execute(PostBarterOrderRequest request) {
        BarterSubmission barterSubmission = getAndUpdateBarterSubmission(request);
        return productBarterRepository.findByProductBarterId(barterSubmission.getBarterSubmissionTargetBarter().getProductBarterId())
                .flatMap(this::checkProductBarterAvailability)
                .map(productBarter -> toBarterOrder(productBarter, barterSubmission))
                .flatMap(barterOrder -> barterOrderRepository.save(barterOrder))
                .map(this::toResponse);
    }

    private BarterSubmission getAndUpdateBarterSubmission(PostBarterOrderRequest request) {
        BarterSubmission barterSubmission = barterSubmissionRepository.findByBarterSubmissionId(request.getBarterSubmissionId()).block();
        if (barterSubmission != null) {
            barterSubmission.getBarterSubmissionTargetBarter().setAvailableStatus(ProductAvailableStatus.NOT_AVAILABLE);
            barterSubmissionRepository.save(barterSubmission).subscribe();
            return barterSubmission;
        } else {
            throw new RuntimeException("Barter Submission not found");
        }
    }

    private Mono<ProductBarter> checkProductBarterAvailability(ProductBarter productBarter) {
        if (productBarter != null && productBarter.getAvailableStatus().equals(ProductAvailableStatus.AVAILABLE)) {
            productBarter.setAvailableStatus(ProductAvailableStatus.NOT_AVAILABLE);
            return productBarterRepository.save(productBarter);
        }
        else {
            return Mono.error(new NotFoundException("Product Barter not found or Product is not available"));
        }
    }

    private BarterOrder toBarterOrder(ProductBarter productBarter, BarterSubmission barterSubmission) {
        BarterSubmissionDataForm barterSubmissionDataForm = getBarterSubmissionDataForm(barterSubmission);
        ProductBarterDataForm productBarterDataForm = getProductBarterDataForm(productBarter);
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('0', '9').build();
        return BarterOrder.builder()
                .barterOrderId(UUID.randomUUID())
                .orderTransactionId("barter-" + generator.generate(8))
                .sellingProduct(productBarterDataForm)
                .buyingProduct(barterSubmissionDataForm)
                .sellerData(productBarter.getUserData())
                .buyerData(barterSubmission.getUserData())
                .orderStatus(BarterOrderStatus.WAITING_IN_WAREHOUSE)
                .sellerItemStatus(BarterItemStatus.IN_OWNER)
                .buyerItemStatus(BarterItemStatus.IN_OWNER)
                .barterOrderCreatedAt(LocalDateTime.now())
                .build();
    }

    private BarterSubmissionDataForm getBarterSubmissionDataForm(BarterSubmission barterSubmission) {
        BarterSubmissionDataForm dataForm = new BarterSubmissionDataForm();
        BeanUtils.copyProperties(barterSubmission, dataForm);
        return dataForm;
    }

    private ProductBarterDataForm getProductBarterDataForm(ProductBarter productBarter) {
        ProductBarterDataForm dataForm = new ProductBarterDataForm();
        BeanUtils.copyProperties(productBarter, dataForm);
        return dataForm;
    }

    private PostBarterOrderResponse toResponse(BarterOrder barterOrder) {
        PostBarterOrderResponse response = new PostBarterOrderResponse();
        BeanUtils.copyProperties(barterOrder, response);
        return response;
    }

}
