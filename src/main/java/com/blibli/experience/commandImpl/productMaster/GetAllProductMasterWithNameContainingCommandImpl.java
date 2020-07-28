package com.blibli.experience.commandImpl.productMaster;

import com.blibli.experience.command.productMaster.GetAllProductMasterWithNameContainingCommand;
import com.blibli.experience.entity.document.ProductMaster;
import com.blibli.experience.model.response.productMaster.GetAllProductMasterWithNameContainingResponse;
import com.blibli.experience.repository.ProductMasterRepository;
import com.blibli.experience.util.SearchKeyUtil;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class GetAllProductMasterWithNameContainingCommandImpl implements GetAllProductMasterWithNameContainingCommand {

    private ProductMasterRepository productMasterRepository;

    @Autowired
    public GetAllProductMasterWithNameContainingCommandImpl(ProductMasterRepository productMasterRepository) {
        this.productMasterRepository = productMasterRepository;
    }

    @Override
    public Mono<List<GetAllProductMasterWithNameContainingResponse>> execute(String searchKey) {
        return productMasterRepository.findAllByProductNameContaining(SearchKeyUtil.capitalizeSearchKey(searchKey))
                .switchIfEmpty(productMasterRepository.findAllByProductNameContaining(SearchKeyUtil.lowerSearchKey(searchKey)))
                        .switchIfEmpty(Mono.error(new NotFoundException("Product not found.")))
                        .map(this::toResponse)
                        .collectList();
    }

    private GetAllProductMasterWithNameContainingResponse toResponse(ProductMaster productMaster) {
        GetAllProductMasterWithNameContainingResponse response = new GetAllProductMasterWithNameContainingResponse();
        BeanUtils.copyProperties(productMaster, response);
        return response;
    }
}
