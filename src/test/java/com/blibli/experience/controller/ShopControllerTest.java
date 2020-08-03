package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.shop.GetAllShopLocationCommand;
import com.blibli.experience.command.shop.GetShopDetailCommand;
import com.blibli.experience.enums.UserRole;
import com.blibli.experience.model.response.shop.GetAllShopLocationResponse;
import com.blibli.experience.model.response.shop.GetShopDetailResponse;
import com.blibli.experience.security.JwtTokenProvider;
import com.blibli.oss.command.CommandExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ShopControllerTest {

    private final UUID randomUUID = UUID.randomUUID();

    @MockBean
    private CommandExecutor commandExecutor;

    @Autowired
    private MockMvc mockMvc;
    private HttpHeaders userHttpHeaders;
    private GetShopDetailResponse getShopDetailResponse;
    private GetAllShopLocationResponse getAllShopLocationResponse;

    @BeforeEach
    void setup() {
        initMocks(this);
        String userId = "9edae4d1-8df9-48d5-a375-fb413e9ffff3";
        userHttpHeaders = new HttpHeaders();
        userHttpHeaders.setBearerAuth(JwtTokenProvider.generateTokenFromRole(UserRole.ROLE_USER, userId));

        getShopDetailResponse = GetShopDetailResponse.builder()
                .shopId(randomUUID)
                .build();
        getAllShopLocationResponse = GetAllShopLocationResponse.builder()
                .shopId(randomUUID)
                .build();
    }

    @Test
    void getShopDetailByUserId() throws Exception {
        when(commandExecutor.execute(GetShopDetailCommand.class, randomUUID))
                .thenReturn(Mono.just(getShopDetailResponse));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.SHOP + "?userId=" + randomUUID)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetShopDetailCommand.class, randomUUID);
    }

    @Test
    void getBliblimartLocation() throws Exception {
        List<GetAllShopLocationResponse> getAllShopLocationResponses = new ArrayList<>();
        getAllShopLocationResponses.add(getAllShopLocationResponse);

        when(commandExecutor.execute(GetAllShopLocationCommand.class, 100))
                .thenReturn(Mono.just(getAllShopLocationResponses));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.PRODUCT_BLIBLIMART)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetAllShopLocationCommand.class, 100);
    }

}
