package com.blibli.experience.commandImpl.productStock;

import com.blibli.experience.command.productStock.GetAllProductStockInShopCommand;
import com.blibli.experience.entity.document.ProductStock;
import com.blibli.experience.model.request.productStock.GetAllProductStockInShopRequest;
import com.blibli.experience.model.response.productStock.GetAllProductStockInShopResponse;
import com.blibli.experience.repository.ProductStockRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class GetAllProductStockInShopCommandImpl implements GetAllProductStockInShopCommand {

    private ProductStockRepository productStockRepository;

    @Autowired
    public GetAllProductStockInShopCommandImpl(ProductStockRepository productStockRepository) {
        this.productStockRepository = productStockRepository;
    }

    @Override
    public Mono<Flux<GetAllProductStockInShopResponse>> execute(GetAllProductStockInShopRequest request) {
        return Mono.fromCallable(() -> productStockRepository.findAllByShopForm_ShopId(request.getShopId()))
                .switchIfEmpty(Mono.error(new NotFoundException("Stock or Shop not found")))
                .map(this::toResponse);
    }

    private Flux<GetAllProductStockInShopResponse> toResponse(Flux<ProductStock> productStockFlux) {
        return productStockFlux.map(this::toStock);
    }
    
    private GetAllProductStockInShopResponse toStock(ProductStock productStock) {
        GetAllProductStockInShopResponse response = new GetAllProductStockInShopResponse();
        BeanUtils.copyProperties(productStock, response);
        return response;
    }

}
