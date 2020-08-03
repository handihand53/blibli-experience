package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.biddingOrder.*;
import com.blibli.experience.enums.UserRole;
import com.blibli.experience.model.request.biddingOrder.PostBiddingOrderRequest;
import com.blibli.experience.model.request.biddingOrder.UpdateBiddingOrderDeliveryReceiptRequest;
import com.blibli.experience.model.response.biddingOrder.*;
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
public class BiddingOrderControllerTest {

    private final UUID randomUUID = UUID.randomUUID();

    @MockBean
    private CommandExecutor commandExecutor;

    @Autowired
    private MockMvc mockMvc;
    private HttpHeaders userHttpHeaders;
    private PostBiddingOrderRequest postBiddingOrderRequest;
    private UpdateBiddingOrderDeliveryReceiptRequest updateBiddingOrderDeliveryReceiptRequest;
    private GetBiddingOrderDetailResponse getBiddingOrderDetailResponse;
    private GetAllBiddingOrderByOwnerUserIdResponse getAllBiddingOrderByOwnerUserIdResponse;
    private GetAllBiddingOrderByWinnerUserIdResponse getAllBiddingOrderByWinnerUserIdResponse;
    private PostBiddingOrderResponse postBiddingOrderResponse;
    private UpdateBiddingOrderDeliveryReceiptResponse updateBiddingOrderDeliveryReceiptResponse;
    private UpdateBiddingOrderToConfirmationResponse updateBiddingOrderToConfirmationResponse;

    @BeforeEach
    void setUp() {
        initMocks(this);
        String userId = "9edae4d1-8df9-48d5-a375-fb413e9ffff3";
        userHttpHeaders = new HttpHeaders();
        userHttpHeaders.setBearerAuth(JwtTokenProvider.generateTokenFromRole(UserRole.ROLE_USER, userId));

        postBiddingOrderRequest = PostBiddingOrderRequest.builder()
                .productBiddingId(randomUUID)
                .build();
        updateBiddingOrderDeliveryReceiptRequest = UpdateBiddingOrderDeliveryReceiptRequest.builder()
                .biddingOrderId(randomUUID)
                .build();
        getBiddingOrderDetailResponse = GetBiddingOrderDetailResponse.builder()
                .biddingOrderId(randomUUID)
                .build();
        getAllBiddingOrderByOwnerUserIdResponse = GetAllBiddingOrderByOwnerUserIdResponse.builder()
                .biddingOrderId(randomUUID)
                .build();
        getAllBiddingOrderByWinnerUserIdResponse = GetAllBiddingOrderByWinnerUserIdResponse.builder()
                .biddingOrderId(randomUUID)
                .build();
        postBiddingOrderResponse = PostBiddingOrderResponse.builder()
                .biddingOrderId(randomUUID)
                .build();
        updateBiddingOrderDeliveryReceiptResponse = UpdateBiddingOrderDeliveryReceiptResponse.builder()
                .biddingOrderId(randomUUID)
                .build();
        updateBiddingOrderToConfirmationResponse = UpdateBiddingOrderToConfirmationResponse.builder()
                .biddingOrderId(randomUUID)
                .build();
    }

    @Test
    void getBiddingOrderDetail() throws Exception {
        when(commandExecutor.execute(GetBiddingOrderDetailCommand.class, randomUUID))
                .thenReturn(Mono.just(getBiddingOrderDetailResponse));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.BIDDING_ORDER + "?biddingOrderId=" + randomUUID)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetBiddingOrderDetailCommand.class, randomUUID);

    }

    @Test
    void getAllBiddingOrderByOwnerUserId() throws Exception {
        List<GetAllBiddingOrderByOwnerUserIdResponse> getAllBiddingOrderByOwnerUserIdResponses = new ArrayList<>();
        getAllBiddingOrderByOwnerUserIdResponses.add(getAllBiddingOrderByOwnerUserIdResponse);
        when(commandExecutor.execute(GetAllBiddingOrderByOwnerUserIdCommand.class, randomUUID))
                .thenReturn(Mono.just(getAllBiddingOrderByOwnerUserIdResponses));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.BIDDING_ORDER_BY_OWNER + "?userId=" + randomUUID)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetAllBiddingOrderByOwnerUserIdCommand.class, randomUUID);

    }

    @Test
    void getAllBiddingOrderByWinnerUserId() throws Exception {
        List<GetAllBiddingOrderByWinnerUserIdResponse> getAllBiddingOrderByWinnerUserIdResponses = new ArrayList<>();
        getAllBiddingOrderByWinnerUserIdResponses.add(getAllBiddingOrderByWinnerUserIdResponse);
        when(commandExecutor.execute(GetAllBiddingOrderByWinnerUserIdCommand.class, randomUUID))
                .thenReturn(Mono.just(getAllBiddingOrderByWinnerUserIdResponses));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.BIDDING_ORDER_BY_WINNER + "?userId=" + randomUUID)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetAllBiddingOrderByWinnerUserIdCommand.class, randomUUID);

    }

    @Test
    void postBiddingOrder() throws Exception {
        when(commandExecutor.execute(PostBiddingOrderCommand.class, postBiddingOrderRequest))
                .thenReturn(Mono.just(postBiddingOrderResponse));

        MockHttpServletRequestBuilder requestBuilder = post(ApiPath.BIDDING_ORDER)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(postBiddingOrderRequest));

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(PostBiddingOrderCommand.class, postBiddingOrderRequest);

    }

    @Test
    void updateBiddingOrderDeliveryReceipt() throws Exception {
        when(commandExecutor.execute(UpdateBiddingOrderDeliveryReceiptCommand.class, updateBiddingOrderDeliveryReceiptRequest))
                .thenReturn(Mono.just(updateBiddingOrderDeliveryReceiptResponse));

        MockHttpServletRequestBuilder requestBuilder = put(ApiPath.BIDDING_ORDER_DELIVERY_RECEIPT)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updateBiddingOrderDeliveryReceiptRequest));

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(UpdateBiddingOrderDeliveryReceiptCommand.class, updateBiddingOrderDeliveryReceiptRequest);

    }

    @Test
    void updateBiddingOrderToConfirmation() throws Exception {
        when(commandExecutor.execute(UpdateBiddingOrderToConfirmationCommand.class, randomUUID))
                .thenReturn(Mono.just(updateBiddingOrderToConfirmationResponse));

        MockHttpServletRequestBuilder requestBuilder = put(ApiPath.BIDDING_ORDER_CONFIRMATION + "?biddingOrderId=" + randomUUID)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(UpdateBiddingOrderToConfirmationCommand.class, randomUUID);

    }

}
