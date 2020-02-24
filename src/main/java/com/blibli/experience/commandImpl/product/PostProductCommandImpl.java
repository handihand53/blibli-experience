package com.blibli.experience.commandImpl.product;

import com.blibli.experience.command.product.PostProductCommand;
import com.blibli.experience.entity.document.Product;
import com.blibli.experience.entity.document.Shop;
import com.blibli.experience.entity.form.ShopForm;
import com.blibli.experience.model.request.product.PostProductRequest;
import com.blibli.experience.repository.ProductRepository;
import com.blibli.experience.repository.ShopRepository;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PostProductCommandImpl implements PostProductCommand {

  private ProductRepository productRepository;
  private ShopRepository shopRepository;

  public PostProductCommandImpl(ProductRepository productRepository,
      ShopRepository shopRepository) {
    this.productRepository = productRepository;
    this.shopRepository = shopRepository;
  }

  @Override
  public Mono<String> execute(PostProductRequest request) {
    return shopRepository.findFirstByShopId(request.getShopId())
        .switchIfEmpty(Mono.error(new Exception("Shop not found!")))
        .map(this::toShopForm)
        .map(shopForm -> toProduct(request, shopForm))
        .flatMap(product -> productRepository.save(product)
            .thenReturn("Success!"));
  }

  private ShopForm toShopForm(Shop shop) {
    ShopForm shopForm = new ShopForm();
    BeanUtils.copyProperties(shop, shopForm);
    return shopForm;
  }

  private Product toProduct(PostProductRequest request, ShopForm form) {
    Product product = Product.builder()
        .productId(UUID.randomUUID())
        .productShopForm(form)
        .productCreatedAt(LocalDateTime.now())
        .build();
    BeanUtils.copyProperties(request, product);
    return product;
  }


}
