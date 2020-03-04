package com.blibli.experience.commandImpl.cart;

import com.blibli.experience.command.cart.PostProductToCartCommand;
import com.blibli.experience.entity.document.Cart;
import com.blibli.experience.entity.document.Product;
import com.blibli.experience.entity.form.CartForm;
import com.blibli.experience.entity.form.CartProductForm;
import com.blibli.experience.model.request.cart.PostProductToCartRequest;
import com.blibli.experience.repository.CartRepository;
import com.blibli.experience.repository.ProductRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PostProductToCartCommandImpl implements PostProductToCartCommand {

  private CartRepository cartRepository;
  private ProductRepository productRepository;

  @Autowired
  public PostProductToCartCommandImpl(CartRepository cartRepository,
      ProductRepository productRepository) {
    this.cartRepository = cartRepository;
    this.productRepository = productRepository;
  }

  @Override
  public Mono<String> execute(PostProductToCartRequest request) {
    return cartRepository.findFirstByUserIdAndCartTag(request.getUserId(), request.getTag())
        .switchIfEmpty(constructCart(request))
        .map(cart -> {
          cart.setCartForms(toCartForm(cart, request.getProductId()));
        });
  }

  private Mono<Cart> constructCart(PostProductToCartRequest request) {
    log.info("#PostProductToCart - Creating new cart for user {}", request.getUserId());
    return Mono.fromCallable(() -> toCart(request));
  }

  private Cart toCart(PostProductToCartRequest request) {
    return Cart.builder()
        .cartId(UUID.randomUUID())
        .userId(request.getUserId())
        .cartTag(request.getTag())
        .createdAt(LocalDateTime.now())
        .lastUpdated(LocalDateTime.now())
        .build();
  }

  private List<CartForm> toCartForm(Cart cart, UUID productId) {
    Product product = productRepository.findFirstByProductId(productId)
        .switchIfEmpty(Mono.error(new NotFoundException("Product not found!")))
        .block();

    // Check if the Shop is already exist in this Cart
    if (!isShopExists(cart, product.getProductShopForm().getShopId())) {
      List<CartForm> cartForms = cart.getCartForms();
      CartForm cartForm = new CartForm();
      BeanUtils.copyProperties(product.getProductShopForm(), cartForm);
      cartForms.add(cartForm);

      // Check if the Product is already exist in corresponding Shop
      if (!isProductExists(cart, productId)) {
        List<CartProductForm> cartProductForms = cartForm.getShopProducts();
        CartProductForm productForm = new CartProductForm();
        BeanUtils.copyProperties(product, productForm);
        productForm.setAmount(1);
        cartProductForms.add(productForm);

      } else {
        throw new RuntimeException("Product already exists.");
      }

      return cartForms;

    } else {

      if (!isProductExists(cart, productId)) {
        List<CartProductForm> cartProductForms = cartForm.getShopProducts();
        CartProductForm productForm = new CartProductForm();
        BeanUtils.copyProperties(product, productForm);
        productForm.setAmount(1);
        cartProductForms.add(productForm);

      }

    }

  }

  private CartForm newCartForm(Product product) {
    CartForm newCartForm = new CartForm();
    BeanUtils.copyProperties(product, newCartForm);
    return newCartForm;
  }

  private CartProductForm toCartProductForm(Product product) {
    CartProductForm productForm = new CartProductForm();
    BeanUtils.copyProperties(product, productForm);
    return productForm;
  }

  private Boolean isShopExists(Cart cart, UUID shopId) {
    return cartRepository.existsByCartForms_ShopId(cart.getCartId(), shopId).block();
  }

  private Boolean isProductExists(Cart cart, UUID productId) {
    return cartRepository.existsByCartForms_ShopProducts_ProductId(cart.getCartId(), productId)
        .block();
  }

}
