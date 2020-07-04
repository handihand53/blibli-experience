package com.blibli.experience.commandImpl.product;

import com.blibli.experience.command.product.GetProductDetailWithBarcodeAndShopCommand;
import com.blibli.experience.entity.document.ProductMaster;
import com.blibli.experience.entity.document.ProductStock;
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
                .map(this::toResponse);
    }

    private GetProductDetailWithBarcodeAndShopResponse toResponse(ProductMaster product) {
        GetProductDetailWithBarcodeAndShopResponse response = new GetProductDetailWithBarcodeAndShopResponse();
        ProductStock stock = productStockRepository.findFirstByProductShopForm_ShopId(product.getProductId())
                .switchIfEmpty(Mono.error(new NotFoundException("This shop didn't have this product"))).block();
        BeanUtils.copyProperties(product, response);
        BeanUtils.copyProperties(stock, response);
        return response;
    }

}
