package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.product.*;
import com.blibli.experience.enums.UserRole;
import com.blibli.experience.model.request.product.GetAllProductByCategoryRequest;
import com.blibli.experience.model.request.product.GetProductDetailWithBarcodeAndShopRequest;
import com.blibli.experience.model.response.product.*;
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
public class ProductControllerTest {

    private final UUID randomUUID = UUID.randomUUID();

    @MockBean
    private CommandExecutor commandExecutor;

    @Autowired
    private MockMvc mockMvc;
    private HttpHeaders userHttpHeaders;
    private GetProductDetailWithBarcodeAndShopRequest getProductDetailWithBarcodeAndShopRequest;
    private GetAllProductByCategoryRequest getAllProductByCategoryRequest;
    private GetProductDetailWithBarcodeAndShopResponse getProductDetailWithBarcodeAndShopResponse;
    private GetProductCategoryEnumResponse getProductCategoryEnumResponse;
    private GetAllProductAvailableResponse getAllProductAvailableResponse;
    private GetAllProductByCategoryResponse getAllProductByCategoryResponse;
    private GetAllProductWithNameAndAvailableStatusResponse getAllProductWithNameAndAvailableStatusResponse;

    @BeforeEach
    void setUp() {
        initMocks(this);
        String userId = "9edae4d1-8df9-48d5-a375-fb413e9ffff3";
        userHttpHeaders = new HttpHeaders();
        userHttpHeaders.setBearerAuth(JwtTokenProvider.generateTokenFromRole(UserRole.ROLE_USER, userId));

        getProductDetailWithBarcodeAndShopRequest = GetProductDetailWithBarcodeAndShopRequest.builder()
                .shopId(randomUUID)
                .productBarcode("string")
                .build();
        getAllProductByCategoryRequest = GetAllProductByCategoryRequest.builder()
                .build();
        getProductDetailWithBarcodeAndShopResponse = GetProductDetailWithBarcodeAndShopResponse.builder()
                .stockId(randomUUID)
                .build();
        getProductCategoryEnumResponse = GetProductCategoryEnumResponse.builder()
                .build();
        getAllProductAvailableResponse = GetAllProductAvailableResponse.builder()
                .stockId(randomUUID)
                .build();
        getAllProductByCategoryResponse = GetAllProductByCategoryResponse.builder()
                .stockId(randomUUID)
                .build();
        getAllProductWithNameAndAvailableStatusResponse = GetAllProductWithNameAndAvailableStatusResponse.builder()
                .stockId(randomUUID)
                .build();
    }

    @Test
    void getDetailProductWithBarcodeAndShop() throws Exception {
        when(commandExecutor.execute(GetProductDetailWithBarcodeAndShopCommand.class, getProductDetailWithBarcodeAndShopRequest))
                .thenReturn(Mono.just(getProductDetailWithBarcodeAndShopResponse));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.PRODUCT_WITH_BARCODE + "?shopId=" + randomUUID + "&productBarcode=string")
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetProductDetailWithBarcodeAndShopCommand.class, getProductDetailWithBarcodeAndShopRequest);

    }

    @Test
    void getProductCategoryEnum() throws Exception {
        when(commandExecutor.execute(GetProductCategoryEnumCommand.class, ""))
                .thenReturn(Mono.just(getProductCategoryEnumResponse));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.PRODUCT_CATEGORY_ENUM)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetProductCategoryEnumCommand.class, "");

    }

    @Test
    void getAllProductAvailable() throws Exception {
        List<GetAllProductAvailableResponse> getAllProductAvailableResponses = new ArrayList<>();
        getAllProductAvailableResponses.add(getAllProductAvailableResponse);
        when(commandExecutor.execute(GetAllProductAvailableCommand.class, 1))
                .thenReturn(Mono.just(getAllProductAvailableResponses));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.PRODUCT_AVAILABLE + "?skipCount=" + 1)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetAllProductAvailableCommand.class, 1);

    }

    @Test
    void getAllProductCategory() throws Exception {
        List<GetAllProductByCategoryResponse> getAllProductByCategoryResponses = new ArrayList<>();
        getAllProductByCategoryResponses.add(getAllProductByCategoryResponse);
        when(commandExecutor.execute(GetAllProductByCategoryCommand.class, getAllProductByCategoryRequest))
                .thenReturn(Mono.just(getAllProductByCategoryResponses));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.PRODUCT_CATEGORY)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetAllProductByCategoryCommand.class, getAllProductByCategoryRequest);

    }

    @Test
    void getAllProductWithSearchKeyToStock() throws Exception {
        List<GetAllProductWithNameAndAvailableStatusResponse> getAllProductWithNameAndAvailableStatusResponses = new ArrayList<>();
        getAllProductWithNameAndAvailableStatusResponses.add(getAllProductWithNameAndAvailableStatusResponse);
        when(commandExecutor.execute(GetAllProductWithNameAndAvailableStatusCommand.class, "search"))
                .thenReturn(Mono.just(getAllProductWithNameAndAvailableStatusResponses));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.PRODUCT_SEARCH_AVAILABLE + "?searchKey=" + "search")
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetAllProductWithNameAndAvailableStatusCommand.class, "search");

    }

}
