package com.blibli.experience.commandImpl.product;

import com.blibli.experience.command.product.GetProductDetailWithBarcodeAndShopCommand;
import com.blibli.experience.entity.document.ProductMaster;
import com.blibli.experience.entity.document.ProductStock;
import com.blibli.experience.entity.form.ProductForm;
import com.blibli.experience.model.request.product.GetProductDetailWithBarcodeAndShopRequest;
import com.blibli.experience.model.response.product.GetProductDetailWithBarcodeAndShopResponse;
import com.blibli.experience.repository.ProductMasterRepository;
import com.blibli.experience.repository.ProductStockRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
public class GetProductDetailWithBarcodeAndShopCommandImpl
        implements GetProductDetailWithBarcodeAndShopCommand {

    private ProductMasterRepository productMasterRepository;
    private ProductStockRepository productStockRepository;

    @Autowired
    public GetProductDetailWithBarcodeAndShopCommandImpl(ProductMasterRepository productMasterRepository,
                                                         ProductStockRepository productStockRepository) {
        this.productMasterRepository = productMasterRepository;
        this.productStockRepository = productStockRepository;
    }

    @Override
    public Mono<GetProductDetailWithBarcodeAndShopResponse> execute(
            GetProductDetailWithBarcodeAndShopRequest request) {
        return productMasterRepository.findFirstByProductBarcode(request.getProductBarcode())
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found!")))
                .map(productMaster -> toResponse(request, productMaster));
    }

    private GetProductDetailWithBarcodeAndShopResponse toResponse(GetProductDetailWithBarcodeAndShopRequest request,
                                                                  ProductMaster productMaster) {
        GetProductDetailWithBarcodeAndShopResponse response = new GetProductDetailWithBarcodeAndShopResponse();
        ProductForm productForm = new ProductForm();
        ProductStock stock = getProductStock(request.getShopId(), productMaster.getProductId());
        BeanUtils.copyProperties(productMaster, productForm);
        stock.setProductForm(productForm);
        BeanUtils.copyProperties(stock, response);
        return response;
    }

    private ProductStock getProductStock(UUID shopId, UUID productId) {
        ProductStock productStock = productStockRepository.findFirstByShopForm_ShopIdAndProductForm_ProductId(shopId, productId)
                .block();
        if (productStock != null) {
            return productStock;
        }
        else {
            throw new RuntimeException("Stock not found.");
        }
    }

}
