package com.blibli.experience.commandImpl.productStock;

import com.blibli.experience.command.productStock.SynchronizeAllProductDataFormCommand;
import com.blibli.experience.entity.document.ProductMaster;
import com.blibli.experience.entity.document.ProductStock;
import com.blibli.experience.entity.form.ProductDataForm;
import com.blibli.experience.repository.ProductMasterRepository;
import com.blibli.experience.repository.ProductStockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class SynchronizeAllProductDataFormCommandImpl implements SynchronizeAllProductDataFormCommand {

    private ProductStockRepository productStockRepository;
    private ProductMasterRepository productMasterRepository;

    @Autowired
    public SynchronizeAllProductDataFormCommandImpl(ProductStockRepository productStockRepository, ProductMasterRepository productMasterRepository) {
        this.productStockRepository = productStockRepository;
        this.productMasterRepository = productMasterRepository;
    }

    @Override
    public Mono<String> execute(Integer request) {
        return productStockRepository.findAll()
                .flatMap(this::getProductMasterData)
                .flatMap(productStock -> productStockRepository.save(productStock))
                .last()
                .map(productStock -> toResponse());

    }

    private Mono<ProductStock> getProductMasterData(ProductStock productStock) {
        return productMasterRepository.findFirstByProductId(productStock.getProductDataForm().getProductId())
                .map(productMaster -> updateProductDataForm(productMaster, productStock));

    }

    private ProductStock updateProductDataForm(ProductMaster productMaster, ProductStock productStock) {
        ProductDataForm updatedProductDataForm = new ProductDataForm();
        BeanUtils.copyProperties(productMaster, updatedProductDataForm);
        productStock.setProductDataForm(updatedProductDataForm);
        return productStock;
    }

    private String toResponse() {
        return "Success";
    }
}
