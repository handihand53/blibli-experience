package com.blibli.experience.commandImpl.product;

import com.blibli.experience.command.product.PostProductCommand;
import com.blibli.experience.entity.document.Shop;
import com.blibli.experience.entity.form.ShopForm;
import com.blibli.experience.entity.document.Product;
import com.blibli.experience.entity.document.User;
import com.blibli.experience.model.request.product.PostProductRequest;
import com.blibli.experience.repository.ProductRepository;
import com.blibli.experience.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PostProductCommandImpl implements PostProductCommand {

  private ProductRepository productRepository;
  private UserRepository userRepository;

  @Autowired
  public PostProductCommandImpl(ProductRepository productRepository, UserRepository userRepository) {
    this.productRepository = productRepository;
    this.userRepository = userRepository;
  }

  @Override
  public Mono<String> execute(PostProductRequest request) {
    return userRepository.findFirstById(request.getMerchantId())
        .switchIfEmpty(Mono.error(new NotFoundException("User not found!")))
        .map(this::toShopForm)
        .map(merchant -> toProduct(request, merchant))
        .flatMap(product -> productRepository.save(product)
          .thenReturn("Post product successful!"));
  }

  private Product toProduct(PostProductRequest request, ShopForm form) {
    Product product = Product.builder()
        .id(UUID.randomUUID())
        .shop(form)
        .createdAt(LocalDateTime.now())
        .build();
    BeanUtils.copyProperties(request, product);
    return product;
  }

  private ShopForm toShopForm(Shop shop) {
    ShopForm shopForm = new ShopForm();
    BeanUtils.copyProperties(shop, shopForm);
    return shopForm;
  }

}
