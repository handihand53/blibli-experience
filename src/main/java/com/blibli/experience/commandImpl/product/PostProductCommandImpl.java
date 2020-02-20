package com.blibli.experience.commandImpl.product;

import com.blibli.experience.command.product.PostProductCommand;
import com.blibli.experience.model.form.MerchantForm;
import com.blibli.experience.entity.Product;
import com.blibli.experience.entity.User;
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
        .map(this::toMerchantData)
        .map(merchant -> toProduct(request, merchant))
        .flatMap(product -> productRepository.save(product)
          .thenReturn("Post product successful!"));
  }

  private Product toProduct(PostProductRequest request, MerchantForm form) {
    Product product = Product.builder()
        .id(UUID.randomUUID())
        .merchant(form)
        .createdAt(LocalDateTime.now())
        .build();
    BeanUtils.copyProperties(request, product);
    return product;
  }

  private MerchantForm toMerchantData(User user) {
    MerchantForm merchantForm = new MerchantForm();
    BeanUtils.copyProperties(user, merchantForm);
    return merchantForm;
  }

}
