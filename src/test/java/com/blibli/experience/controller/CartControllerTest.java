package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.cart.DeleteProductInCartCommand;
import com.blibli.experience.command.cart.GetCartWithUserIdCommand;
import com.blibli.experience.command.cart.PostProductToCartCommand;
import com.blibli.experience.command.cart.UpdateCartProductAmountCommand;
import com.blibli.experience.enums.UserRole;
import com.blibli.experience.model.request.cart.DeleteProductInCartRequest;
import com.blibli.experience.model.request.cart.PostProductToCartRequest;
import com.blibli.experience.model.request.cart.UpdateCartProductAmountRequest;
import com.blibli.experience.model.response.cart.DeleteProductInCartResponse;
import com.blibli.experience.model.response.cart.GetCartWithUserIdResponse;
import com.blibli.experience.model.response.cart.PostProductToCartResponse;
import com.blibli.experience.model.response.cart.UpdateCartProductAmountResponse;
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

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest {

    private final UUID randomUUID = UUID.randomUUID();

    @MockBean
    private CommandExecutor commandExecutor;

    @Autowired
    private MockMvc mockMvc;
    private HttpHeaders userHttpHeaders;
    private PostProductToCartRequest postProductToCartRequest;
    private DeleteProductInCartRequest deleteProductInCartRequest;
    private UpdateCartProductAmountRequest updateCartProductAmountRequest;
    private GetCartWithUserIdResponse getCartWithUserIdResponse;
    private PostProductToCartResponse postProductToCartResponse;
    private UpdateCartProductAmountResponse updateCartProductAmountResponse;
    private DeleteProductInCartResponse deleteProductInCartResponse;

    @BeforeEach
    void setUp() {
        initMocks(this);
        String userId = "9edae4d1-8df9-48d5-a375-fb413e9ffff3";
        userHttpHeaders = new HttpHeaders();
        userHttpHeaders.setBearerAuth(JwtTokenProvider.generateTokenFromRole(UserRole.ROLE_USER, userId));

        postProductToCartRequest = PostProductToCartRequest.builder()
                .userId(randomUUID)
                .build();
        deleteProductInCartRequest = DeleteProductInCartRequest.builder()
                .cartId(randomUUID)
                .build();
        updateCartProductAmountRequest = UpdateCartProductAmountRequest.builder()
                .cartId(randomUUID)
                .build();
        getCartWithUserIdResponse = GetCartWithUserIdResponse.builder()
                .cartId(randomUUID)
                .build();
        postProductToCartResponse = PostProductToCartResponse.builder()
                .cartId(randomUUID)
                .build();
        updateCartProductAmountResponse = UpdateCartProductAmountResponse.builder()
                .cartId(randomUUID)
                .build();
        deleteProductInCartResponse = DeleteProductInCartResponse.builder()
                .cartId(randomUUID)
                .build();
    }

    @Test
    void getCartWithUserId() throws Exception {
        when(commandExecutor.execute(GetCartWithUserIdCommand.class, randomUUID))
                .thenReturn(Mono.just(getCartWithUserIdResponse));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.CARTS + "?userId=" + randomUUID)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetCartWithUserIdCommand.class, randomUUID);

    }

    @Test
    void postProductToCart() throws Exception {
        when(commandExecutor.execute(PostProductToCartCommand.class, postProductToCartRequest))
                .thenReturn(Mono.just(postProductToCartResponse));

        MockHttpServletRequestBuilder requestBuilder = post(ApiPath.CARTS)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(postProductToCartRequest));

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(PostProductToCartCommand.class, postProductToCartRequest);

    }

    @Test
    void updateCartProductAmount() throws Exception {
        when(commandExecutor.execute(UpdateCartProductAmountCommand.class, updateCartProductAmountRequest))
                .thenReturn(Mono.just(updateCartProductAmountResponse));

        MockHttpServletRequestBuilder requestBuilder = put(ApiPath.CARTS_UPDATE_AMOUNT)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updateCartProductAmountRequest));

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(UpdateCartProductAmountCommand.class, updateCartProductAmountRequest);

    }

    @Test
    void deleteProductInCart() throws Exception {
        when(commandExecutor.execute(DeleteProductInCartCommand.class, deleteProductInCartRequest))
                .thenReturn(Mono.just(deleteProductInCartResponse));

        MockHttpServletRequestBuilder requestBuilder = put(ApiPath.CARTS_DELETE_PRODUCT)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(deleteProductInCartRequest));

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(DeleteProductInCartCommand.class, deleteProductInCartRequest);

    }

}
