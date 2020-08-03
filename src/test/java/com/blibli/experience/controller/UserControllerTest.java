package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.user.GetUserDetailCommand;
import com.blibli.experience.command.user.UpdateUserDataCommand;
import com.blibli.experience.command.user.UpdateUserPasswordCommand;
import com.blibli.experience.enums.UserRole;
import com.blibli.experience.model.request.user.UpdateUserDataRequest;
import com.blibli.experience.model.request.user.UpdateUserPasswordRequest;
import com.blibli.experience.model.response.user.GetUserDetailResponse;
import com.blibli.experience.model.response.user.UpdateUserDataResponse;
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
class UserControllerTest {

    private final UUID randomUUID = UUID.randomUUID();

    @MockBean
    private CommandExecutor commandExecutor;

    @Autowired
    private MockMvc mockMvc;
    private HttpHeaders userHttpHeaders;
    private UpdateUserPasswordRequest updateUserPasswordRequest;
    private UpdateUserDataRequest updateUserDataRequest;
    private GetUserDetailResponse getUserDetailResponse;
    private UpdateUserDataResponse updateUserDataResponse;


    @BeforeEach
    void setUp() {
        initMocks(this);
        String userId = "9edae4d1-8df9-48d5-a375-fb413e9ffff3";
        userHttpHeaders = new HttpHeaders();
        userHttpHeaders.setBearerAuth(JwtTokenProvider.generateTokenFromRole(UserRole.ROLE_USER, userId));

        updateUserPasswordRequest = UpdateUserPasswordRequest.builder()
                .userId(randomUUID)
                .build();
        updateUserDataRequest = UpdateUserDataRequest.builder()
                .userId(randomUUID)
                .build();
        getUserDetailResponse = GetUserDetailResponse.builder()
                .userId(randomUUID)
                .build();
        updateUserDataResponse = UpdateUserDataResponse.builder()
                .userId(randomUUID)
                .build();
    }

    @Test
    void getUserData() throws Exception {
        when(commandExecutor.execute(GetUserDetailCommand.class, randomUUID))
                .thenReturn(Mono.just(getUserDetailResponse));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.USER + "?id=" + randomUUID)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetUserDetailCommand.class, randomUUID);
    }

    @Test
    void updateUserData() throws Exception {
        when(commandExecutor.execute(UpdateUserDataCommand.class, updateUserDataRequest))
                .thenReturn(Mono.just(updateUserDataResponse));

        MockHttpServletRequestBuilder requestBuilder = put(ApiPath.USER)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updateUserDataRequest));

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(UpdateUserDataCommand.class, updateUserDataRequest);

    }

    @Test
    void updateUserPassword() throws Exception {
        when(commandExecutor.execute(UpdateUserPasswordCommand.class, updateUserPasswordRequest))
                .thenReturn(Mono.just("Success"));

        MockHttpServletRequestBuilder requestBuilder = put(ApiPath.USER_UPDATE_PASSWORD)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updateUserPasswordRequest));

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(UpdateUserPasswordCommand.class, updateUserPasswordRequest);
    }
}