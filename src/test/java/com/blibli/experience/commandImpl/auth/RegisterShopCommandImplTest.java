package com.blibli.experience.commandImpl.auth;

import com.blibli.experience.entity.document.Shop;
import com.blibli.experience.entity.document.User;
import com.blibli.experience.enums.GenderType;
import com.blibli.experience.model.request.auth.RegisterShopRequest;
import com.blibli.experience.model.response.auth.RegisterShopResponse;
import com.blibli.experience.repository.ShopRepository;
import com.blibli.experience.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class RegisterShopCommandImplTest {

    private final UUID randomUUID = UUID.randomUUID();
    private final String email = "email@gmail.com";
    private final GenderType genderType = GenderType.PRIA;
    private final LocalDate birthDate = LocalDate.now();
    private final LocalDateTime createdAt = LocalDateTime.now();

    @InjectMocks
    RegisterShopCommandImpl registerShopCommand;

    @Mock
    UserRepository userRepository;

    @Mock
    ShopRepository shopRepository;

    private User user;
    private Shop shop;
    private RegisterShopRequest request;
    private RegisterShopResponse response;

    @BeforeEach
    void setUp() {
        initMocks(this);
        user = User.builder()
                .userId(randomUUID)
                .userEmail(email)
                .userPassword("password")
                .userName("User Name")
                .userBirthDate(birthDate)
                .userPhoneNumber("08126107686")
                .userGender(genderType)
                .userCreatedAt(createdAt)
                .userIdentityId("01679765443368363")
                .build();
        shop = Shop.builder()
                .shopId(randomUUID)
                .shopName("Shop Name")
                .shopDescription("Description")
                .shopCreatedAt(createdAt)
                .build();
        request = RegisterShopRequest.builder()
                .userEmail("email@gmail.com")
                .userPassword("password")
                .userName("User Name")
                .userPhoneNumber("08126107686")
                .userIdentityId("01679765443368363")
                .build();
        response = RegisterShopResponse.builder()
                .shopId(randomUUID)
                .shopName("Shop Name")
                .build();
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(shopRepository);
    }

    @Test
    void execute() {
//        when(userRepository.findFirstByUserEmail(email))
//                .thenReturn(Mono.just(user));
//        when(shopRepository.save(shop))
//                .thenReturn(Mono.just(shop));
//
//        RegisterShopResponse result = registerShopCommand.execute(request).block();
//        assertEquals(response, result);
//
//        verify(userRepository).findFirstByUserEmail(email);
//        verify(shopRepository).save(shop);
    }

}
