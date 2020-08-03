package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.productMaster.*;
import com.blibli.experience.enums.UserRole;
import com.blibli.experience.model.request.productMaster.PostProductMasterRequest;
import com.blibli.experience.model.request.productMaster.UpdateProductMasterRequest;
import com.blibli.experience.model.response.product.GetProductMasterDetailWithIdResponse;
import com.blibli.experience.model.response.productMaster.*;
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
public class ProductMasterControllerTest {

    private final UUID randomUUID = UUID.randomUUID();

    @MockBean
    private CommandExecutor commandExecutor;

    @Autowired
    private MockMvc mockMvc;
    private HttpHeaders userHttpHeaders;
    private HttpHeaders adminHttpHeaders;
    private PostProductMasterRequest postProductMasterRequest;
    private UpdateProductMasterRequest updateProductMasterRequest;
    private PostProductMasterResponse postProductMasterResponse;
    private UpdateProductMasterResponse updateProductMasterResponse;
    private GetProductMasterMetadataResponse getProductMasterMetadataResponse;
    private GetProductMasterDetailWithIdResponse getProductMasterDetailWithIdResponse;
    private GetAllProductMasterResponse getAllProductMasterResponse;
    private GetAllProductMasterWithNameContainingResponse getAllProductMasterWithNameContainingResponse;

    @BeforeEach
    void setUp() {
        initMocks(this);
        String userId = "9edae4d1-8df9-48d5-a375-fb413e9ffff3";
        userHttpHeaders = new HttpHeaders();
        userHttpHeaders.setBearerAuth(JwtTokenProvider.generateTokenFromRole(UserRole.ROLE_USER, userId));
        String adminId = "bfebe547-3fc3-4cd4-85d1-6d20f732f82b";
        adminHttpHeaders = new HttpHeaders();
        adminHttpHeaders.setBearerAuth(JwtTokenProvider.generateTokenFromRole(UserRole.ROLE_ADMIN, adminId));

        postProductMasterRequest = PostProductMasterRequest.builder()
                .productName("Product 1")
                .build();
        updateProductMasterRequest = UpdateProductMasterRequest.builder()
                .productId(randomUUID)
                .build();
        postProductMasterResponse = PostProductMasterResponse.builder()
                .productId(randomUUID)
                .build();
        updateProductMasterResponse = UpdateProductMasterResponse.builder()
                .productId(randomUUID)
                .build();
        getProductMasterMetadataResponse = GetProductMasterMetadataResponse.builder()
                .build();
        getProductMasterDetailWithIdResponse = GetProductMasterDetailWithIdResponse.builder()
                .productId(randomUUID)
                .build();
        getAllProductMasterResponse = GetAllProductMasterResponse.builder()
                .productId(randomUUID)
                .build();
        getAllProductMasterWithNameContainingResponse = GetAllProductMasterWithNameContainingResponse.builder()
                .productId(randomUUID)
                .build();
    }

    @Test
    void postProductMaster() throws Exception {
//        when(commandExecutor.execute(PostProductMasterCommand.class, postProductMasterRequest))
//                .thenReturn(Mono.just(postProductMasterResponse));
//
//        MockHttpServletRequestBuilder requestBuilder = put(ApiPath.ADMIN_PRODUCT_MASTER)
//                .headers(adminHttpHeaders)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(postProductMasterRequest));
//
//        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
//                .andExpect(status().isOk());
//
//        verify(commandExecutor).execute(PostProductMasterCommand.class, postProductMasterRequest);

    }

    @Test
    void generateProductMasterQr() throws Exception {
        when(commandExecutor.execute(GenerateProductMasterQRCodeCommand.class, 1))
                .thenReturn(Mono.just("Success"));

        MockHttpServletRequestBuilder requestBuilder = post(ApiPath.ADMIN_GENERATE_PRODUCT_MASTER_QR)
                .headers(adminHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GenerateProductMasterQRCodeCommand.class, 1);

    }

    @Test
    void updateProductMaster() throws Exception {
        when(commandExecutor.execute(UpdateProductMasterCommand.class, updateProductMasterRequest))
                .thenReturn(Mono.just(updateProductMasterResponse));

        MockHttpServletRequestBuilder requestBuilder = put(ApiPath.ADMIN_PRODUCT_MASTER)
                .headers(adminHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updateProductMasterRequest));

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(UpdateProductMasterCommand.class, updateProductMasterRequest);

    }

    @Test
    void getProductMasterMetadata() throws Exception {
        when(commandExecutor.execute(GetProductMasterMetadataCommand.class, 1))
                .thenReturn(Mono.just(getProductMasterMetadataResponse));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.METADATA_PRODUCT_MASTER)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetProductMasterMetadataCommand.class, 1);

    }

    @Test
    void getDetailProductWithId() throws Exception {
        when(commandExecutor.execute(GetProductMasterDetailWithIdCommand.class, randomUUID))
                .thenReturn(Mono.just(getProductMasterDetailWithIdResponse));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.PRODUCT + "?id=" + randomUUID)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetProductMasterDetailWithIdCommand.class, randomUUID);

    }

    @Test
    void getAllProductMaster() throws Exception {
        List<GetAllProductMasterResponse> getAllProductMasterResponses = new ArrayList<>();
        getAllProductMasterResponses.add(getAllProductMasterResponse);
        when(commandExecutor.execute(GetAllProductMasterCommand.class, 1))
                .thenReturn(Mono.just(getAllProductMasterResponses));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.PRODUCTS_ALL + "?skipCount=" + 1)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetAllProductMasterCommand.class, 1);

    }

    @Test
    void getProductWithSearchKey() throws Exception {
        List<GetAllProductMasterWithNameContainingResponse> getAllProductMasterWithNameContainingResponses = new ArrayList<>();
        getAllProductMasterWithNameContainingResponses.add(getAllProductMasterWithNameContainingResponse);
        when(commandExecutor.execute(GetAllProductMasterWithNameContainingCommand.class, "search"))
                .thenReturn(Mono.just(getAllProductMasterWithNameContainingResponses));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.PRODUCT_SEARCH + "?searchKey=" + "search")
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetAllProductMasterWithNameContainingCommand.class, "search");

    }

}
