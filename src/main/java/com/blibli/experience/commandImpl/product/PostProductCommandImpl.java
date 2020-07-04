//package com.blibli.experience.commandImpl.product;
//
//import com.blibli.experience.command.product.PostProductCommand;
//import com.blibli.experience.entity.document.ProductMaster;
//import com.blibli.experience.entity.document.Shop;
//import com.blibli.experience.entity.form.ShopForm;
//import com.blibli.experience.model.request.product.PostProductRequest;
//import com.blibli.experience.repository.ProductMasterRepository;
//import com.blibli.experience.repository.ShopRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.BeanUtils;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Slf4j
//@Service
//public class PostProductCommandImpl implements PostProductCommand {
//
//  private ProductMasterRepository productMasterRepository;
//
//  public PostProductCommandImpl(ProductMasterRepository productMasterRepository) {
//    this.productMasterRepository = productMasterRepository;
//  }
//
//  @Override
//  public Mono<String> execute(PostProductRequest request) {
//    return shopRepository.findFirstByShopId(request.getShopId())
//        .switchIfEmpty(Mono.error(new Exception("Shop not found!")))
//        .map(this::toShopForm)
//        .filter(shopForm -> isProductExists(shopForm, request))
//        .switchIfEmpty(Mono.error(new Exception("Product is already registered before.")))
//        .map(shopForm -> toProduct(request, shopForm))
//        .flatMap(product -> productRepository.save(product)
//            .thenReturn("Success!"));
//  }
//
//  private ShopForm toShopForm(Shop shop) {
//    log.info("#PostProductCommand - Convert ShopData to ShopForm...");
//    ShopForm shopForm = new ShopForm();
//    BeanUtils.copyProperties(shop, shopForm);
//    return shopForm;
//  }
//
//  private Boolean isProductExists(ShopForm shopForm, PostProductRequest request) {
//    return productRepository.existsByProductShopFormAndProductBarcode(shopForm, request.getProductBarcode()).block();
//  }
//
//  private Product toProduct(PostProductRequest request, ShopForm form) {
//    log.info("#PostProductCommand - Convert request and form to Product object...");
//    Product product = Product.builder()
//        .productId(UUID.randomUUID())
//        .productShopForm(form)
//        .productCreatedAt(LocalDateTime.now())
//        .build();
//    BeanUtils.copyProperties(request, product);
//    return product;
//  }
//
//}
