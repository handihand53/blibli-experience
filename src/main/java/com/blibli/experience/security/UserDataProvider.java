package com.blibli.experience.security;

import com.blibli.experience.entity.document.Shop;
import com.blibli.experience.entity.document.User;
import com.blibli.experience.entity.form.UserRoleForm;
import com.blibli.experience.enums.UserRole;
import com.blibli.experience.repository.ShopRepository;
import com.blibli.experience.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserDataProvider {

    private UserRepository userRepository;
    private ShopRepository shopRepository;

    @Autowired
    public UserDataProvider(UserRepository userRepository, ShopRepository shopRepository) {
        this.userRepository = userRepository;
        this.shopRepository = shopRepository;
    }

    public UserRoleForm provideUserData(String userEmail) {
        UserRoleForm dataForm = new UserRoleForm();
        User user = userRepository.findFirstByUserEmail(userEmail).block();
        if(user != null && user.getUserRoles().contains(UserRole.ROLE_MERCHANT)) {
            BeanUtils.copyProperties(user, dataForm);
            dataForm.setShopId(provideShopData(user.getUserId()).getShopId());
            return dataForm;
        }
        else if(user != null) {
            BeanUtils.copyProperties(user, dataForm);
            return dataForm;
        }
        else {
            throw new RuntimeException("User not found.");
        }
    }

    private Shop provideShopData(UUID userId) {
        Shop shop = shopRepository.findFirstByUserId(userId).block();
        if(shop != null) {
            return shop;
        }
        else {
            throw new RuntimeException("Shop not found.");
        }
    }

}
