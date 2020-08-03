package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.productBarter.*;
import com.blibli.experience.enums.UserRole;
import com.blibli.experience.model.request.productBarter.GetAllProductBarterAvailableAndCategoryRequest;
import com.blibli.experience.model.request.productBarter.PostProductBarterRequest;
import com.blibli.experience.model.response.productBarter.*;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductBarterControllerTest {

    private final UUID randomUUID = UUID.randomUUID();

    @MockBean
    private CommandExecutor commandExecutor;

    @Autowired
    private MockMvc mockMvc;
    private HttpHeaders userHttpHeaders;
    private GetAllProductBarterAvailableAndCategoryRequest getAllProductBarterAvailableAndCategoryRequest;
    private PostProductBarterRequest postProductBarterRequest;
    private GetProductBarterMetadataResponse getProductBarterMetadataResponse;
    private GetProductBarterDetailResponse getProductBarterDetailResponse;
    private GetAllProductBarterAvailableResponse getAllProductBarterAvailableResponse;
    private GetAllProductBarterAvailableAndCategoryResponse getAllProductBarterAvailableAndCategoryResponse;
    private GetAllProductBarterByUserIdResponse getAllProductBarterByUserIdResponse;
    private PostProductBarterResponse postProductBarterResponse;

    @BeforeEach
    void setUp() {
        initMocks(this);
        String userId = "9edae4d1-8df9-48d5-a375-fb413e9ffff3";
        userHttpHeaders = new HttpHeaders();
        userHttpHeaders.setBearerAuth(JwtTokenProvider.generateTokenFromRole(UserRole.ROLE_USER, userId));

        getAllProductBarterAvailableAndCategoryRequest = GetAllProductBarterAvailableAndCategoryRequest.builder()
                .build();
        postProductBarterRequest = PostProductBarterRequest.builder()
                .userId(randomUUID)
                .build();
        getProductBarterMetadataResponse = GetProductBarterMetadataResponse.builder()
                .build();
        getProductBarterDetailResponse = GetProductBarterDetailResponse.builder()
                .productBarterId(randomUUID)
                .build();
        getAllProductBarterAvailableResponse = GetAllProductBarterAvailableResponse.builder()
                .productBarterId(randomUUID)
                .build();
        getAllProductBarterAvailableAndCategoryResponse = GetAllProductBarterAvailableAndCategoryResponse.builder()
                .productBarterId(randomUUID)
                .build();
        getAllProductBarterByUserIdResponse = GetAllProductBarterByUserIdResponse.builder()
                .productBarterId(randomUUID)
                .build();
        postProductBarterResponse =  PostProductBarterResponse.builder()
                .productBarterId(randomUUID)
                .build();
    }

    @Test
    void getProductBarterMetadata() throws Exception {
        when(commandExecutor.execute(GetProductBarterMetadataCommand.class, 1))
                .thenReturn(Mono.just(getProductBarterMetadataResponse));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.METADATA_PRODUCT_BARTER)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetProductBarterMetadataCommand.class, 1);

    }

    @Test
    void getProductBarterDetail() throws Exception {
        when(commandExecutor.execute(GetProductBarterDetailCommand.class, randomUUID))
                .thenReturn(Mono.just(getProductBarterDetailResponse));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.PRODUCT_BARTER + "?productBarterId=" + randomUUID)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetProductBarterDetailCommand.class, randomUUID);

    }

    @Test
    void getAllProductBarterAvailable() throws Exception {
        List<GetAllProductBarterAvailableResponse> getAllProductBarterAvailableResponses = new ArrayList<>();
        getAllProductBarterAvailableResponses.add(getAllProductBarterAvailableResponse);
        when(commandExecutor.execute(GetAllProductBarterAvailableCommand.class, 1))
                .thenReturn(Mono.just(getAllProductBarterAvailableResponses));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.PRODUCT_BARTER_AVAILABLE + "?skipCount=" + 1)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetAllProductBarterAvailableCommand.class, 1);

    }

    @Test
    void getAllProductBarterByCategory() throws Exception {
        List<GetAllProductBarterAvailableAndCategoryResponse> getAllProductBarterAvailableAndCategoryResponses = new ArrayList<>();
        getAllProductBarterAvailableAndCategoryResponses.add(getAllProductBarterAvailableAndCategoryResponse);
        when(commandExecutor.execute(GetAllProductBarterAvailableAndCategoryCommand.class, getAllProductBarterAvailableAndCategoryRequest))
                .thenReturn(Mono.just(getAllProductBarterAvailableAndCategoryResponses));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.PRODUCT_BARTER_BY_CATEGORY)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetAllProductBarterAvailableAndCategoryCommand.class, getAllProductBarterAvailableAndCategoryRequest);

    }

    @Test
    void getAllProductBarterByUserId() throws Exception {
        List<GetAllProductBarterByUserIdResponse> getAllProductBarterByUserIdResponses = new ArrayList<>();
        getAllProductBarterByUserIdResponses.add(getAllProductBarterByUserIdResponse);
        when(commandExecutor.execute(GetAllProductBarterByUserIdCommand.class, randomUUID))
                .thenReturn(Mono.just(getAllProductBarterByUserIdResponses));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.PRODUCT_BARTER_BY_USER + "?userId=" + randomUUID)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetAllProductBarterByUserIdCommand.class, randomUUID);

    }

    @Test
    void postProductBarter() throws Exception {

    }

}
