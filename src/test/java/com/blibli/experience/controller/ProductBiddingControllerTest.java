package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.productBidding.*;
import com.blibli.experience.command.productMaster.GetAllProductMasterCommand;
import com.blibli.experience.command.productMaster.GetProductMasterMetadataCommand;
import com.blibli.experience.command.productMaster.UpdateProductMasterCommand;
import com.blibli.experience.enums.UserRole;
import com.blibli.experience.model.request.productBidding.GetAllProductBiddingByAvailableAndCategoryRequest;
import com.blibli.experience.model.request.productBidding.PostProductBiddingRequest;
import com.blibli.experience.model.request.productBidding.UpdateProductBiddingToBidRequest;
import com.blibli.experience.model.response.productBidding.*;
import com.blibli.experience.model.response.productMaster.GetAllProductMasterResponse;
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
public class ProductBiddingControllerTest {

    private final UUID randomUUID = UUID.randomUUID();

    @MockBean
    private CommandExecutor commandExecutor;

    @Autowired
    private MockMvc mockMvc;
    private HttpHeaders userHttpHeaders;
    private HttpHeaders adminHttpHeaders;
    private GetAllProductBiddingByAvailableAndCategoryRequest getAllProductBiddingByAvailableAndCategoryRequest;
    private PostProductBiddingRequest postProductBiddingRequest;
    private UpdateProductBiddingToBidRequest updateProductBiddingToBidRequest;
    private GetProductBiddingMetadataResponse getProductBiddingMetadataResponse;
    private GetProductBiddingDetailResponse getProductBiddingDetailResponse;
    private GetAllProductBiddingAvailableResponse getAllProductBiddingAvailableResponse;
    private GetAllProductBiddingByAvailableAndCategoryResponse getAllProductBiddingByAvailableAndCategoryResponse;
    private GetAllProductBiddingByUserAndAvailableResponse getAllProductBiddingByUserAndAvailableResponse;
    private GetAllProductBiddingFinishedByUserIdResponse getAllProductBiddingFinishedByUserIdResponse;
    private GetAllProductBiddingBidByUserResponse getAllProductBiddingBidByUserResponse;
    private PostProductBiddingResponse postProductBiddingResponse;
    private UpdateProductBiddingToBidResponse updateProductBiddingToBidResponse;
    private UpdateProductBiddingToCloseResponse updateProductBiddingToCloseResponse;

    @BeforeEach
    void setUp() {
        initMocks(this);
        String userId = "9edae4d1-8df9-48d5-a375-fb413e9ffff3";
        userHttpHeaders = new HttpHeaders();
        userHttpHeaders.setBearerAuth(JwtTokenProvider.generateTokenFromRole(UserRole.ROLE_USER, userId));
        String adminId = "bfebe547-3fc3-4cd4-85d1-6d20f732f82b";
        adminHttpHeaders = new HttpHeaders();
        adminHttpHeaders.setBearerAuth(JwtTokenProvider.generateTokenFromRole(UserRole.ROLE_ADMIN, adminId));

        getAllProductBiddingByAvailableAndCategoryRequest = GetAllProductBiddingByAvailableAndCategoryRequest.builder()
                .build();
        postProductBiddingRequest = PostProductBiddingRequest.builder()
                .userId(randomUUID)
                .build();
        updateProductBiddingToBidRequest = UpdateProductBiddingToBidRequest.builder()
                .userId(randomUUID)
                .build();
        getProductBiddingMetadataResponse = GetProductBiddingMetadataResponse.builder()
                .build();
        getProductBiddingDetailResponse =  GetProductBiddingDetailResponse.builder()
                .productBiddingId(randomUUID)
                .build();
        getAllProductBiddingAvailableResponse = GetAllProductBiddingAvailableResponse.builder()
                .productBiddingId(randomUUID)
                .build();
        getAllProductBiddingByAvailableAndCategoryResponse = GetAllProductBiddingByAvailableAndCategoryResponse.builder()
                .productBiddingId(randomUUID)
                .build();
        getAllProductBiddingByUserAndAvailableResponse = GetAllProductBiddingByUserAndAvailableResponse.builder()
                .productBiddingId(randomUUID)
                .build();
        getAllProductBiddingFinishedByUserIdResponse = GetAllProductBiddingFinishedByUserIdResponse.builder()
                .productBiddingId(randomUUID)
                .build();
        getAllProductBiddingBidByUserResponse = GetAllProductBiddingBidByUserResponse.builder()
                .productBiddingId(randomUUID)
                .build();
        postProductBiddingResponse = PostProductBiddingResponse.builder()
                .productBiddingId(randomUUID)
                .build();
        updateProductBiddingToBidResponse = UpdateProductBiddingToBidResponse.builder()
                .productBiddingId(randomUUID)
                .build();
        updateProductBiddingToCloseResponse = UpdateProductBiddingToCloseResponse.builder()
                .productBiddingId(randomUUID)
                .build();
    }

    @Test
    void getProductBiddingMetadata() throws Exception {
        when(commandExecutor.execute(GetProductBiddingMetadataCommand.class, 1))
                .thenReturn(Mono.just(getProductBiddingMetadataResponse));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.METADATA_PRODUCT_BIDDING)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetProductBiddingMetadataCommand.class, 1);

    }

    @Test
    void getProductBiddingDetail() throws Exception {
        when(commandExecutor.execute(GetProductBiddingDetailCommand.class, randomUUID))
                .thenReturn(Mono.just(getProductBiddingDetailResponse));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.PRODUCT_BIDDING + "?productBiddingId=" + randomUUID)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetProductBiddingDetailCommand.class, randomUUID);

    }

    @Test
    void getProductBiddingAvailable() throws Exception {
        List<GetAllProductBiddingAvailableResponse> getAllProductBiddingAvailableResponses  = new ArrayList<>();
        getAllProductBiddingAvailableResponses.add(getAllProductBiddingAvailableResponse);
        when(commandExecutor.execute(GetAllProductBiddingAvailableCommand.class, 1))
                .thenReturn(Mono.just(getAllProductBiddingAvailableResponses));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.PRODUCT_BIDDING_AVAILABLE + "?skipCount=" + 1)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetAllProductBiddingAvailableCommand.class, 1);

    }

    @Test
    void getProductBiddingByCategory() throws Exception {
        List<GetAllProductBiddingByAvailableAndCategoryResponse> getAllProductBiddingByAvailableAndCategoryResponses = new ArrayList<>();
        getAllProductBiddingByAvailableAndCategoryResponses.add(getAllProductBiddingByAvailableAndCategoryResponse);
        when(commandExecutor.execute(GetAllProductBiddingByAvailableAndCategoryCommand.class, getAllProductBiddingByAvailableAndCategoryRequest))
                .thenReturn(Mono.just(getAllProductBiddingByAvailableAndCategoryResponses));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.PRODUCT_BIDDING_BY_CATEGORY)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetAllProductBiddingByAvailableAndCategoryCommand.class, getAllProductBiddingByAvailableAndCategoryRequest);

    }

    @Test
    void getProductBiddingByUserAndAvailable() throws Exception {
        List<GetAllProductBiddingByUserAndAvailableResponse> getAllProductBiddingByUserAndAvailableResponses = new ArrayList<>();
        getAllProductBiddingByUserAndAvailableResponses.add(getAllProductBiddingByUserAndAvailableResponse);
        when(commandExecutor.execute(GetAllProductBiddingByUserAndAvailableCommand.class, randomUUID))
                .thenReturn(Mono.just(getAllProductBiddingByUserAndAvailableResponses));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.PRODUCT_BIDDING_BY_USER + "?userId=" + randomUUID)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetAllProductBiddingByUserAndAvailableCommand.class, randomUUID);

    }

    @Test
    void getAllProductBiddingFinishedByUserId() throws Exception {
        List<GetAllProductBiddingFinishedByUserIdResponse> getAllProductBiddingFinishedByUserIdResponses = new ArrayList<>();
        getAllProductBiddingFinishedByUserIdResponses.add(getAllProductBiddingFinishedByUserIdResponse);
        when(commandExecutor.execute(GetAllProductBiddingFinishedByUserIdCommand.class, randomUUID))
                .thenReturn(Mono.just(getAllProductBiddingFinishedByUserIdResponses));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.PRODUCT_BIDDING_BY_USER_FINISHED + "?userId=" + randomUUID)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetAllProductBiddingFinishedByUserIdCommand.class, randomUUID);

    }

    @Test
    void getAllProductBiddingBidByUser() throws Exception {
        List<GetAllProductBiddingBidByUserResponse> getAllProductBiddingBidByUserResponses = new ArrayList<>();
        getAllProductBiddingBidByUserResponses.add(getAllProductBiddingBidByUserResponse);
        when(commandExecutor.execute(GetAllProductBiddingBidByUserCommand.class, randomUUID))
                .thenReturn(Mono.just(getAllProductBiddingBidByUserResponses));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.PRODUCT_BIDDING_BIDDING_FORM_BY_USER + "?userId=" + randomUUID)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetAllProductBiddingBidByUserCommand.class, randomUUID);

    }

    @Test
    void postProductBidding() throws Exception {

    }

    @Test
    void updateProductBiddingToBid() throws Exception {
        when(commandExecutor.execute(UpdateProductBiddingToBidCommand.class, updateProductBiddingToBidRequest))
                .thenReturn(Mono.just(updateProductBiddingToBidResponse));

        MockHttpServletRequestBuilder requestBuilder = put(ApiPath.PRODUCT_BIDDING_TO_BID)
                .headers(adminHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updateProductBiddingToBidRequest));

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(UpdateProductBiddingToBidCommand.class, updateProductBiddingToBidRequest);

    }

    @Test
    void updateProductBiddingToClose() throws Exception {
        List<UpdateProductBiddingToCloseResponse> updateProductBiddingToCloseResponses = new ArrayList<>();
        updateProductBiddingToCloseResponses.add(updateProductBiddingToCloseResponse);
        when(commandExecutor.execute(UpdateProductBiddingToCloseCommand.class, 1))
                .thenReturn(Mono.just(updateProductBiddingToCloseResponses));

        MockHttpServletRequestBuilder requestBuilder = put(ApiPath.ADMIN_PRODUCT_BIDDING_TO_CLOSE)
                .headers(adminHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(UpdateProductBiddingToCloseCommand.class, 1);

    }

}
