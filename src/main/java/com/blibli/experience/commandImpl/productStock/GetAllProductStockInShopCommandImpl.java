package com.blibli.experience.commandImpl.productStock;

import com.blibli.experience.command.productStock.GetAllProductStockInShopCommand;
import com.blibli.experience.entity.document.ProductStock;
import com.blibli.experience.model.response.productStock.GetAllProductStockInShopResponse;
import com.blibli.experience.repository.ProductStockRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class GetAllProductStockInShopCommandImpl implements GetAllProductStockInShopCommand {

    private ProductStockRepository productStockRepository;

    @Autowired
    public GetAllProductStockInShopCommandImpl(ProductStockRepository productStockRepository) {
        this.productStockRepository = productStockRepository;
    }

    @Override
    public Mono<List<GetAllProductStockInShopResponse>> execute(UUID shopId) {
        return productStockRepository.findAllByShopForm_ShopId(shopId)
                .switchIfEmpty(Mono.error(new NotFoundException("Shop not found.")))
                .map(this::toResponse)
                .collectList();
    }

    private GetAllProductStockInShopResponse toResponse(ProductStock productStock) {
        GetAllProductStockInShopResponse response = new GetAllProductStockInShopResponse();
        BeanUtils.copyProperties(productStock, response);
        return response;
    }

}
