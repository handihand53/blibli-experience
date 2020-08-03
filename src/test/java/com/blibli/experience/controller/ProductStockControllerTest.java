package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.productStock.GetAllProductStockInShopCommand;
import com.blibli.experience.command.productStock.PostProductStockCommand;
import com.blibli.experience.command.productStock.SynchronizeAllProductDataFormCommand;
import com.blibli.experience.command.productStock.UpdateProductStockCommand;
import com.blibli.experience.enums.UserRole;
import com.blibli.experience.model.request.productStock.PostProductStockRequest;
import com.blibli.experience.model.request.productStock.UpdateProductStockRequest;
import com.blibli.experience.model.response.productStock.GetAllProductStockInShopResponse;
import com.blibli.experience.model.response.productStock.PostProductStockResponse;
import com.blibli.experience.model.response.productStock.UpdateProductStockResponse;
import com.blibli.experience.security.JwtTokenProvider;
import com.blibli.oss.command.CommandExecutor;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class ProductStockControllerTest {

    private final UUID randomUUID = UUID.randomUUID();

    @MockBean
    private CommandExecutor commandExecutor;

    @Autowired
    private MockMvc mockMvc;
    private HttpHeaders adminHttpHeaders;
    private HttpHeaders merchantHttpHeaders;
    private PostProductStockRequest postProductStockRequest;
    private UpdateProductStockRequest updateProductStockRequest;
    private PostProductStockResponse postProductStockResponse;
    private GetAllProductStockInShopResponse getAllProductStockInShopResponse;
    private UpdateProductStockResponse updateProductStockResponse;


    @BeforeEach
    void setUp() {
        initMocks(this);
        String adminId = "bfebe547-3fc3-4cd4-85d1-6d20f732f82b";
        adminHttpHeaders = new HttpHeaders();
        adminHttpHeaders.setBearerAuth(JwtTokenProvider.generateTokenFromRole(UserRole.ROLE_ADMIN, adminId));
        String merchantId = "3eb3e637-d956-42df-9cb8-b41a0fd57b7a";
        merchantHttpHeaders = new HttpHeaders();
        merchantHttpHeaders.setBearerAuth(JwtTokenProvider.generateTokenFromRole(UserRole.ROLE_MERCHANT, merchantId));

        postProductStockRequest = PostProductStockRequest.builder()
                .productId(randomUUID)
                .build();
        updateProductStockRequest = UpdateProductStockRequest.builder()
                .stockId(randomUUID)
                .build();
        postProductStockResponse = PostProductStockResponse.builder()
                .stockId(randomUUID)
                .build();
        getAllProductStockInShopResponse = GetAllProductStockInShopResponse.builder()
                .stockId(randomUUID)
                .build();
        updateProductStockResponse = UpdateProductStockResponse.builder()
                .stockId(randomUUID)
                .build();
    }

    @Test
    void postProductStock() throws Exception {
        when(commandExecutor.execute(PostProductStockCommand.class, postProductStockRequest))
                .thenReturn(Mono.just(postProductStockResponse));

        MockHttpServletRequestBuilder requestBuilder = post(ApiPath.MERCHANT_PRODUCT_STOCK)
                .headers(merchantHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(postProductStockRequest));

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(PostProductStockCommand.class, postProductStockRequest);

    }

    @Test
    void synchronizeProductDataForm() throws Exception {
        when(commandExecutor.execute(SynchronizeAllProductDataFormCommand.class, 1))
                .thenReturn(Mono.just("Success"));

        MockHttpServletRequestBuilder requestBuilder = post(ApiPath.ADMIN_SYNCHRONIZE_PRODUCT_DATA_FORM_IN_STOCK)
                .headers(adminHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(SynchronizeAllProductDataFormCommand.class, 1);

    }

    @Test
    void getAllProductStockInShop() throws Exception {
        List<GetAllProductStockInShopResponse> getAllProductStockInShopResponses = new ArrayList<>();
        getAllProductStockInShopResponses.add(getAllProductStockInShopResponse);
        when(commandExecutor.execute(GetAllProductStockInShopCommand.class, randomUUID))
                .thenReturn(Mono.just(getAllProductStockInShopResponses));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.MERCHANT_PRODUCT_STOCK + "?shopId=" + randomUUID)
                .headers(merchantHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetAllProductStockInShopCommand.class, randomUUID);
    }

    @Test
    void updateProductStock() throws Exception {
        when(commandExecutor.execute(UpdateProductStockCommand.class, updateProductStockRequest))
                .thenReturn(Mono.just(updateProductStockResponse));

        MockHttpServletRequestBuilder requestBuilder = put(ApiPath.MERCHANT_PRODUCT_STOCK)
                .headers(merchantHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updateProductStockRequest));

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(UpdateProductStockCommand.class, updateProductStockRequest);

    }

}
