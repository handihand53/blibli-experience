package com.blibli.experience.commandImpl.product;

import com.blibli.experience.command.product.GetProductDetailWithBarcodeAndShopCommand;
import com.blibli.experience.entity.document.ProductStock;
import com.blibli.experience.model.request.product.GetProductDetailWithBarcodeAndShopRequest;
import com.blibli.experience.model.response.product.GetProductDetailWithBarcodeAndShopResponse;
import com.blibli.experience.repository.ProductStockRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class GetProductDetailWithBarcodeAndShopCommandImpl implements GetProductDetailWithBarcodeAndShopCommand {

    private ProductStockRepository productStockRepository;

    @Autowired
    public GetProductDetailWithBarcodeAndShopCommandImpl(ProductStockRepository productStockRepository) {
        this.productStockRepository = productStockRepository;
    }

    @Override
    public Mono<GetProductDetailWithBarcodeAndShopResponse> execute(
            GetProductDetailWithBarcodeAndShopRequest request) {
        return productStockRepository.findByShopForm_ShopIdAndProductDataForm_ProductBarcode(request.getShopId(), request.getProductBarcode())
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found!")))
                .map(this::toResponse);
    }

    private GetProductDetailWithBarcodeAndShopResponse toResponse(ProductStock productStock) {
        GetProductDetailWithBarcodeAndShopResponse response = new GetProductDetailWithBarcodeAndShopResponse();
        BeanUtils.copyProperties(productStock, response);
        return response;
    }


}
