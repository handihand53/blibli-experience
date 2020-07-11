package com.blibli.experience.commandImpl.productStock;

import com.blibli.experience.command.productStock.PostProductStockCommand;
import com.blibli.experience.entity.document.ProductMaster;
import com.blibli.experience.entity.document.ProductStock;
import com.blibli.experience.entity.document.Shop;
import com.blibli.experience.entity.form.ProductForm;
import com.blibli.experience.entity.form.ShopForm;
import com.blibli.experience.model.request.productStock.PostProductStockRequest;
import com.blibli.experience.model.response.productStock.PostProductStockResponse;
import com.blibli.experience.repository.ProductMasterRepository;
import com.blibli.experience.repository.ProductStockRepository;
import com.blibli.experience.repository.ShopRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class PostProductStockCommandImpl implements PostProductStockCommand {

    private ShopRepository shopRepository;
    private ProductMasterRepository productMasterRepository;
    private ProductStockRepository productStockRepository;

    @Autowired
    public PostProductStockCommandImpl(ShopRepository shopRepository,
                                       ProductMasterRepository productMasterRepository,
                                       ProductStockRepository productStockRepository) {
        this.shopRepository = shopRepository;
        this.productMasterRepository = productMasterRepository;
        this.productStockRepository = productStockRepository;
    }

    @Override
    public Mono<PostProductStockResponse> execute(PostProductStockRequest request) {
        return Mono.fromCallable(() -> toProductStock(request))
                .flatMap(productStock -> productStockRepository.save(productStock))
                .map(this::toResponse);
    }

    private ProductStock toProductStock(PostProductStockRequest request) {
        ProductStock productStock = ProductStock.builder()
                .stockId(UUID.randomUUID())
                .stockCreatedAt(LocalDateTime.now())
                .build();
        BeanUtils.copyProperties(request, productStock);
        productStock.setShopForm(getShopForm(request));
        productStock.setProductForm(getProductForm(request));
        return productStock;
    }

    private ShopForm getShopForm(PostProductStockRequest request) {
        ShopForm shopForm = new ShopForm();
        Shop shop = shopRepository.findFirstByShopId(request.getShopId()).block();
        if (shop != null) {
            BeanUtils.copyProperties(shop, shopForm);
            return shopForm;
        }
        else {
            throw new RuntimeException("Shop not found.");
        }
    }

    private ProductForm getProductForm(PostProductStockRequest request) {
        ProductForm productForm = new ProductForm();
        ProductMaster productMaster = productMasterRepository.findFirstByProductId(request.getProductId()).block();
        if (productMaster != null) {
            BeanUtils.copyProperties(productMaster, productForm);
            return productForm;
        }
        else {
            throw new RuntimeException("Product master not found.");
        }
    }

    private PostProductStockResponse toResponse(ProductStock productStock) {
        PostProductStockResponse response = new PostProductStockResponse();
        BeanUtils.copyProperties(productStock, response);
        return response;
    }

}
