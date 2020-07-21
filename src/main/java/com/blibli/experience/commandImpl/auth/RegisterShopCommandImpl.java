package com.blibli.experience.commandImpl.auth;

import com.blibli.experience.command.auth.RegisterShopCommand;
import com.blibli.experience.entity.document.Shop;
import com.blibli.experience.entity.document.User;
import com.blibli.experience.enums.UserRole;
import com.blibli.experience.model.request.auth.RegisterShopRequest;
import com.blibli.experience.model.response.auth.RegisterShopResponse;
import com.blibli.experience.repository.ShopRepository;
import com.blibli.experience.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class RegisterShopCommandImpl implements RegisterShopCommand {

  private ShopRepository shopRepository;
  private UserRepository userRepository;

  @Autowired
  public RegisterShopCommandImpl(ShopRepository shopRepository,
      UserRepository userRepository) {
    this.shopRepository = shopRepository;
    this.userRepository = userRepository;
  }

  @Autowired
  private PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public Mono<RegisterShopResponse> execute(RegisterShopRequest request) {
    return userRepository.findFirstByUserEmail(request.getUserEmail())
        .doOnNext(user -> updateUser(request, user))
        .switchIfEmpty(registerUser(request))
        .map(data -> toShop(request, data))
        .flatMap(shop -> shopRepository.save(shop))
        .map(this::toResponse);
  }

  private void updateUser(RegisterShopRequest request, User user) {
    log.info("#RegisterShopCommand - Updating user data... ");
    user.setUserPhoneNumber(request.getUserPhoneNumber());
    user.setUserIdentityId(request.getUserIdentityId());
    user.setUserRoles(addMerchantRole(user));
    userRepository.save(user).subscribe();
  }

  private List<UserRole> addMerchantRole(User user) {
    List<UserRole> roles = user.getUserRoles();
    roles.add(UserRole.ROLE_MERCHANT);
    return roles;
  }

  private Mono<User> registerUser(RegisterShopRequest request) {
    log.info("#RegisterShopCommand - Creating new user for shop {}", request.getShopName());
    return Mono.fromCallable(() -> toUser(request))
        .flatMap(user -> userRepository.save(user));
  }

  private User toUser(RegisterShopRequest request) {
    User user = User.builder()
        .userId(UUID.randomUUID())
        .userCreatedAt(LocalDateTime.now())
        .build();
    BeanUtils.copyProperties(request, user);
    user.setUserPassword(passwordEncoder().encode(user.getUserPassword()));
    user.setUserRoles(getMerchantRole());
    return user;
  }

  private List<UserRole> getMerchantRole() {
    List<UserRole> roles = new ArrayList<>();
    roles.add(UserRole.ROLE_MERCHANT);
    return roles;
  }

  private Shop toShop(RegisterShopRequest request, User user) {
    Shop shop = Shop.builder()
        .shopId(UUID.randomUUID())
        .shopCreatedAt(LocalDateTime.now())
        .build();
    BeanUtils.copyProperties(request, shop);
    shop.setUserId(user.getUserId());
    return shop;
  }

  private RegisterShopResponse toResponse(Shop shop) {
    RegisterShopResponse response = new RegisterShopResponse();
    BeanUtils.copyProperties(shop, response);
    return response;
  }

}
