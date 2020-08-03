package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.order.*;
import com.blibli.experience.enums.UserRole;
import com.blibli.experience.model.request.order.PostOrderRequest;
import com.blibli.experience.model.request.order.UpdateOrderDeliveryReceiptRequest;
import com.blibli.experience.model.response.order.*;
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
public class OrderControllerTest {

    private final UUID randomUUID = UUID.randomUUID();

    @MockBean
    private CommandExecutor commandExecutor;

    @Autowired
    private MockMvc mockMvc;
    private HttpHeaders userHttpHeaders;
    private HttpHeaders merchantHttpHeaders;
    private PostOrderRequest postOrderRequest;
    private UpdateOrderDeliveryReceiptRequest updateOrderDeliveryReceiptRequest;
    private GetOrderDetailResponse getOrderDetailResponse;
    private GetAllOrderByUserIdResponse getAllOrderByUserIdResponse;
    private GetAllOrderByShopIdResponse getAllOrderByShopIdResponse;
    private PostOrderResponse postOrderResponse;
    private UpdateOrderStatusToFinishedResponse updateOrderStatusToFinishedResponse;
    private UpdateOrderDeliveryReceiptResponse updateOrderDeliveryReceiptResponse;

    @BeforeEach
    void setUp() {
        initMocks(this);
        String userId = "9edae4d1-8df9-48d5-a375-fb413e9ffff3";
        userHttpHeaders = new HttpHeaders();
        userHttpHeaders.setBearerAuth(JwtTokenProvider.generateTokenFromRole(UserRole.ROLE_USER, userId));
        String merchantId = "3eb3e637-d956-42df-9cb8-b41a0fd57b7a";
        merchantHttpHeaders = new HttpHeaders();
        merchantHttpHeaders.setBearerAuth(JwtTokenProvider.generateTokenFromRole(UserRole.ROLE_MERCHANT, merchantId));

        postOrderRequest = PostOrderRequest.builder()
                .shopId(randomUUID)
                .cartId(randomUUID)
                .build();
        updateOrderDeliveryReceiptRequest = UpdateOrderDeliveryReceiptRequest.builder()
                .orderId(randomUUID)
                .build();
        getOrderDetailResponse = GetOrderDetailResponse.builder()
                .orderId(randomUUID)
                .build();
        getAllOrderByUserIdResponse = GetAllOrderByUserIdResponse.builder()
                .orderId(randomUUID)
                .build();
        getAllOrderByShopIdResponse = GetAllOrderByShopIdResponse.builder()
                .orderId(randomUUID)
                .build();
        postOrderResponse = PostOrderResponse.builder()
                .orderId(randomUUID)
                .build();
        updateOrderStatusToFinishedResponse = UpdateOrderStatusToFinishedResponse.builder()
                .orderId(randomUUID)
                .build();
        updateOrderDeliveryReceiptResponse = UpdateOrderDeliveryReceiptResponse.builder()
                .orderId(randomUUID)
                .build();
    }

    @Test
    void getOrderDetail() throws Exception {
        when(commandExecutor.execute(GetOrderDetailCommand.class, randomUUID))
                .thenReturn(Mono.just(getOrderDetailResponse));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.ORDER + "?orderId=" + randomUUID)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetOrderDetailCommand.class, randomUUID);

    }

    @Test
    void getAllOrderByUserId() throws Exception {
        List<GetAllOrderByUserIdResponse> getAllOrderByUserIdResponses = new ArrayList<>();
        getAllOrderByUserIdResponses.add(getAllOrderByUserIdResponse);
        when(commandExecutor.execute(GetAllOrderByUserIdCommand.class, randomUUID))
                .thenReturn(Mono.just(getAllOrderByUserIdResponses));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.ORDER_BY_USER + "?userId=" + randomUUID)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetAllOrderByUserIdCommand.class, randomUUID);

    }

    @Test
    void getAllOrderByShopId() throws Exception {
        List<GetAllOrderByShopIdResponse> getAllOrderByShopIdResponses = new ArrayList<>();
        getAllOrderByShopIdResponses.add(getAllOrderByShopIdResponse);
        when(commandExecutor.execute(GetAllOrderByShopIdCommand.class, randomUUID))
                .thenReturn(Mono.just(getAllOrderByShopIdResponses));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.MERCHANT_ORDER + "?shopId=" + randomUUID)
                .headers(merchantHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetAllOrderByShopIdCommand.class, randomUUID);

    }

    @Test
    void postOrder() throws Exception {
        when(commandExecutor.execute(PostOrderCommand.class, postOrderRequest))
                .thenReturn(Mono.just(postOrderResponse));

        MockHttpServletRequestBuilder requestBuilder = post(ApiPath.ORDER)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(postOrderRequest));

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(PostOrderCommand.class, postOrderRequest);

    }

    @Test
    void updateOrderToFinished() throws Exception {
        when(commandExecutor.execute(UpdateOrderStatusToFinishedCommand.class, randomUUID))
                .thenReturn(Mono.just(updateOrderStatusToFinishedResponse));

        MockHttpServletRequestBuilder requestBuilder = put(ApiPath.ORDER_TO_FINISHED + "?orderId=" + randomUUID)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(UpdateOrderStatusToFinishedCommand.class, randomUUID);

    }

    @Test
    void updateOrderDeliveryReceipt() throws Exception {
        when(commandExecutor.execute(UpdateOrderDeliveryReceiptCommand.class, updateOrderDeliveryReceiptRequest))
                .thenReturn(Mono.just(updateOrderDeliveryReceiptResponse));

        MockHttpServletRequestBuilder requestBuilder = put(ApiPath.MERCHANT_ORDER_DELIVERY_RECEIPT)
                .headers(merchantHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updateOrderDeliveryReceiptRequest));

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(UpdateOrderDeliveryReceiptCommand.class, updateOrderDeliveryReceiptRequest);

    }
}
