package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.auth.RegisterAdminCommand;
import com.blibli.experience.command.auth.RegisterShopCommand;
import com.blibli.experience.command.auth.RegisterUserCommand;
import com.blibli.experience.command.productMaster.UpdateProductMasterCommand;
import com.blibli.experience.model.request.auth.RegisterAdminRequest;
import com.blibli.experience.model.request.auth.RegisterShopRequest;
import com.blibli.experience.model.request.auth.RegisterUserRequest;
import com.blibli.experience.model.response.auth.RegisterAdminResponse;
import com.blibli.experience.model.response.auth.RegisterShopResponse;
import com.blibli.experience.model.response.auth.RegisterUserResponse;
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
public class AuthControllerTest {

    private final UUID randomUUID = UUID.randomUUID();

    @MockBean
    private CommandExecutor commandExecutor;

    @Autowired
    private MockMvc mockMvc;
    private HttpHeaders httpHeaders;
    private RegisterUserRequest registerUserRequest;
    private RegisterShopRequest registerShopRequest;
    private RegisterAdminRequest registerAdminRequest;
    private RegisterUserResponse registerUserResponse;
    private RegisterShopResponse registerShopResponse;
    private RegisterAdminResponse registerAdminResponse;

    @BeforeEach
    void setUp() {
        initMocks(this);
        httpHeaders = new HttpHeaders();

        registerUserRequest = RegisterUserRequest.builder()
                .userEmail("string")
                .build();
        registerShopRequest = RegisterShopRequest.builder()
                .userEmail("string")
                .build();
        registerAdminRequest = RegisterAdminRequest.builder()
                .userEmail("string")
                .build();
        registerUserResponse = RegisterUserResponse.builder()
                .userId(randomUUID)
                .build();
        registerShopResponse = RegisterShopResponse.builder()
                .shopId(randomUUID)
                .build();
        registerAdminResponse = RegisterAdminResponse.builder()
                .userId(randomUUID)
                .build();
    }

    @Test
    void registerUser() throws Exception {
        when(commandExecutor.execute(RegisterUserCommand.class, registerUserRequest))
                .thenReturn(Mono.just(registerUserResponse));

        MockHttpServletRequestBuilder requestBuilder = post(ApiPath.REGISTER)
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(registerUserRequest));

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(RegisterUserCommand.class, registerUserRequest);

    }

    @Test
    void registerShop() throws Exception {
        when(commandExecutor.execute(RegisterShopCommand.class, registerShopRequest))
                .thenReturn(Mono.just(registerShopResponse));

        MockHttpServletRequestBuilder requestBuilder = post(ApiPath.REGISTER_SHOP)
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(registerShopRequest));

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(RegisterShopCommand.class, registerShopRequest);

    }

    @Test
    void registerAdmin() throws Exception {
        when(commandExecutor.execute(RegisterAdminCommand.class, registerAdminRequest))
                .thenReturn(Mono.just(registerAdminResponse));

        MockHttpServletRequestBuilder requestBuilder = post(ApiPath.REGISTER_ADMIN)
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(registerAdminRequest));

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(RegisterAdminCommand.class, registerAdminRequest);

    }

    @Test
    void login() throws Exception {

    }

}
