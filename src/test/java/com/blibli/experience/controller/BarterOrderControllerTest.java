package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.barterOrder.*;
import com.blibli.experience.enums.BarterOrderStatus;
import com.blibli.experience.enums.UserRole;
import com.blibli.experience.model.request.barterOrder.*;
import com.blibli.experience.model.response.barterOrder.*;
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
public class BarterOrderControllerTest {

    private final UUID randomUUID = UUID.randomUUID();

    @MockBean
    private CommandExecutor commandExecutor;

    @Autowired
    private MockMvc mockMvc;
    private HttpHeaders userHttpHeaders;
    private HttpHeaders adminHttpHeaders;
    private GetAllBarterOrderByUserIdRequest getAllBarterOrderByUserIdRequest;
    private GetAllBarterOrderByItemStatusRequest getAllBarterOrderByItemStatusRequest;
    private PostBarterOrderRequest postBarterOrderRequest;
    private UpdateBarterOrderReceiptToWarehouseRequest updateBarterOrderReceiptToWarehouseRequest;
    private UpdateBarterOrderReceiptToVerifiedRequest updateBarterOrderReceiptToVerifiedRequest;
    private UpdateBarterOrderReceiptToConsumersRequest updateBarterOrderReceiptToConsumersRequest;
    private UpdateBarterOrderItemInConsumersRequest updateBarterOrderItemInConsumersRequest;
    private GetBarterOrderDetailResponse getBarterOrderDetailResponse;
    private GetAllBarterOrderByUserIdResponse getAllBarterOrderByUserIdResponse;
    private GetAllBarterOrderByItemStatusResponse getAllBarterOrderByItemStatusResponse;
    private GetAllBarterOrderByOrderStatusResponse getAllBarterOrderByOrderStatusResponse;
    private PostBarterOrderResponse postBarterOrderResponse;
    private UpdateBarterOrderReceiptToWarehouseResponse updateBarterOrderReceiptToWarehouseResponse;
    private UpdateBarterOrderReceiptToVerifiedResponse updateBarterOrderReceiptToVerifiedResponse;
    private UpdateBarterOrderReceiptToConsumersResponse updateBarterOrderReceiptToConsumersResponse;
    private UpdateBarterOrderItemInConsumersResponse updateBarterOrderItemInConsumersResponse;

    @BeforeEach
    void setUp() {
        initMocks(this);
        String userId = "9edae4d1-8df9-48d5-a375-fb413e9ffff3";
        userHttpHeaders = new HttpHeaders();
        userHttpHeaders.setBearerAuth(JwtTokenProvider.generateTokenFromRole(UserRole.ROLE_USER, userId));
        String adminId = "bfebe547-3fc3-4cd4-85d1-6d20f732f82b";
        adminHttpHeaders = new HttpHeaders();
        adminHttpHeaders.setBearerAuth(JwtTokenProvider.generateTokenFromRole(UserRole.ROLE_ADMIN, adminId));

        getAllBarterOrderByUserIdRequest = GetAllBarterOrderByUserIdRequest.builder()
                .userId(randomUUID)
                .build();
        getAllBarterOrderByItemStatusRequest = GetAllBarterOrderByItemStatusRequest.builder()
                .build();
        postBarterOrderRequest = PostBarterOrderRequest.builder()
                .barterSubmissionId(randomUUID)
                .build();
        updateBarterOrderReceiptToWarehouseRequest = UpdateBarterOrderReceiptToWarehouseRequest.builder()
                .barterOrderId(randomUUID)
                .build();
        updateBarterOrderReceiptToVerifiedRequest = UpdateBarterOrderReceiptToVerifiedRequest.builder()
                .barterOrderId(randomUUID)
                .build();
        updateBarterOrderReceiptToConsumersRequest = UpdateBarterOrderReceiptToConsumersRequest.builder()
                .barterOrderId(randomUUID)
                .build();
        updateBarterOrderItemInConsumersRequest = UpdateBarterOrderItemInConsumersRequest.builder()
                .barterOrderId(randomUUID)
                .build();
        getBarterOrderDetailResponse = GetBarterOrderDetailResponse.builder()
                .barterOrderId(randomUUID)
                .build();
        getAllBarterOrderByUserIdResponse = GetAllBarterOrderByUserIdResponse.builder()
                .barterOrderId(randomUUID)
                .build();
        getAllBarterOrderByItemStatusResponse = GetAllBarterOrderByItemStatusResponse.builder()
                .barterOrderId(randomUUID)
                .build();
        getAllBarterOrderByOrderStatusResponse = GetAllBarterOrderByOrderStatusResponse.builder()
                .barterOrderId(randomUUID)
                .build();
        postBarterOrderResponse = PostBarterOrderResponse.builder()
                .barterOrderId(randomUUID)
                .build();
        updateBarterOrderReceiptToWarehouseResponse = UpdateBarterOrderReceiptToWarehouseResponse.builder()
                .barterOrderId(randomUUID)
                .build();
        updateBarterOrderReceiptToVerifiedResponse = UpdateBarterOrderReceiptToVerifiedResponse.builder()
                .barterOrderId(randomUUID)
                .build();
        updateBarterOrderReceiptToConsumersResponse = UpdateBarterOrderReceiptToConsumersResponse.builder()
                .barterOrderId(randomUUID)
                .build();
        updateBarterOrderItemInConsumersResponse = UpdateBarterOrderItemInConsumersResponse.builder()
                .barterOrderId(randomUUID)
                .build();
    }

    @Test
    void getBarterOrderDetail() throws Exception {
        when(commandExecutor.execute(GetBarterOrderDetailCommand.class, randomUUID))
                .thenReturn(Mono.just(getBarterOrderDetailResponse));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.BARTER_ORDER + "?barterOrderId=" + randomUUID)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetBarterOrderDetailCommand.class, randomUUID);

    }

    @Test
    void getAllBarterOrderByUserId() throws Exception {
//        List<GetAllBarterOrderByUserIdResponse> getAllBarterOrderByUserIdResponses = new ArrayList<>();
//        getAllBarterOrderByUserIdResponses.add(getAllBarterOrderByUserIdResponse);
//        when(commandExecutor.execute(GetAllBarterOrderByUserIdCommand.class, getAllBarterOrderByUserIdRequest))
//                .thenReturn(Mono.just(getAllBarterOrderByUserIdResponses));
//
//        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.BARTER_ORDER_BY_USER)
//                .headers(userHttpHeaders)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(getAllBarterOrderByUserIdRequest));
//
//        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
//                .andExpect(status().isOk());
//
//        verify(commandExecutor).execute(GetAllBarterOrderByUserIdCommand.class, getAllBarterOrderByUserIdRequest);

    }

    @Test
    void getAllBarterOrderByItemStatus() throws Exception {
        List<GetAllBarterOrderByItemStatusResponse> getAllBarterOrderByItemStatusResponses = new ArrayList<>();
        getAllBarterOrderByItemStatusResponses.add(getAllBarterOrderByItemStatusResponse);
        when(commandExecutor.execute(GetAllBarterOrderByItemStatusCommand.class, getAllBarterOrderByItemStatusRequest))
                .thenReturn(Mono.just(getAllBarterOrderByItemStatusResponses));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.ADMIN_BARTER_ORDER_BY_ITEM_STATUS)
                .headers(adminHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(getAllBarterOrderByItemStatusRequest));

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetAllBarterOrderByItemStatusCommand.class, getAllBarterOrderByItemStatusRequest);

    }

    @Test
    void getAllBarterOrderByOrderStatus() throws Exception {
        List<GetAllBarterOrderByOrderStatusResponse> getAllBarterOrderByOrderStatusResponses = new ArrayList<>();
        getAllBarterOrderByOrderStatusResponses.add(getAllBarterOrderByOrderStatusResponse);
        when(commandExecutor.execute(GetAllBarterOrderByOrderStatusCommand.class, BarterOrderStatus.FINISHED))
                .thenReturn(Mono.just(getAllBarterOrderByOrderStatusResponses));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.ADMIN_BARTER_ORDER_BY_ORDER_STATUS + "?request=" + BarterOrderStatus.FINISHED)
                .headers(adminHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetAllBarterOrderByOrderStatusCommand.class, BarterOrderStatus.FINISHED);

    }

    @Test
    void postBarterOrder() throws Exception {
        when(commandExecutor.execute(PostBarterOrderCommand.class, postBarterOrderRequest))
                .thenReturn(Mono.just(postBarterOrderResponse));

        MockHttpServletRequestBuilder requestBuilder = post(ApiPath.BARTER_ORDER)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(postBarterOrderRequest));

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(PostBarterOrderCommand.class, postBarterOrderRequest);

    }

    @Test
    void updateBarterOrderReceiptToWarehouse() throws Exception {
        when(commandExecutor.execute(UpdateBarterOrderReceiptToWarehouseCommand.class, updateBarterOrderReceiptToWarehouseRequest))
                .thenReturn(Mono.just(updateBarterOrderReceiptToWarehouseResponse));

        MockHttpServletRequestBuilder requestBuilder = put(ApiPath.BARTER_ORDER_RECEIPT_TO_WAREHOUSE)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updateBarterOrderReceiptToWarehouseRequest));

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(UpdateBarterOrderReceiptToWarehouseCommand.class, updateBarterOrderReceiptToWarehouseRequest);

    }

    @Test
    void updateBarterOrderReceiptToVerified() throws Exception {
        when(commandExecutor.execute(UpdateBarterOrderReceiptToVerifiedCommand.class, updateBarterOrderReceiptToVerifiedRequest))
                .thenReturn(Mono.just(updateBarterOrderReceiptToVerifiedResponse));

        MockHttpServletRequestBuilder requestBuilder = put(ApiPath.ADMIN_BARTER_ORDER_RECEIPT_TO_VERIFIED)
                .headers(adminHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updateBarterOrderReceiptToVerifiedRequest));

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(UpdateBarterOrderReceiptToVerifiedCommand.class, updateBarterOrderReceiptToVerifiedRequest);

    }

    @Test
    void updateBarterOrderReceiptToConsumers() throws Exception {
        when(commandExecutor.execute(UpdateBarterOrderReceiptToConsumersCommand.class, updateBarterOrderReceiptToConsumersRequest))
                .thenReturn(Mono.just(updateBarterOrderReceiptToConsumersResponse));

        MockHttpServletRequestBuilder requestBuilder = put(ApiPath.ADMIN_BARTER_ORDER_RECEIPT_TO_CONSUMERS)
                .headers(adminHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updateBarterOrderReceiptToConsumersRequest));

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(UpdateBarterOrderReceiptToConsumersCommand.class, updateBarterOrderReceiptToConsumersRequest);

    }

    @Test
    void updateBarterOrderReceiptInConsumers() throws Exception {
        when(commandExecutor.execute(UpdateBarterOrderItemInConsumersCommand.class, updateBarterOrderItemInConsumersRequest))
                .thenReturn(Mono.just(updateBarterOrderItemInConsumersResponse));

        MockHttpServletRequestBuilder requestBuilder = put(ApiPath.BARTER_ORDER_RECEIPT_IN_CONSUMERS)
                .headers(adminHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updateBarterOrderItemInConsumersRequest));

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(UpdateBarterOrderItemInConsumersCommand.class, updateBarterOrderItemInConsumersRequest);

    }

}
