package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.user.GetUserDetailCommand;
import com.blibli.experience.command.user.UpdateUserPasswordCommand;
import com.blibli.experience.enums.GenderType;
import com.blibli.experience.model.request.user.UpdateUserPasswordRequest;
import com.blibli.experience.model.response.user.GetUserDetailResponse;
import com.blibli.oss.command.CommandExecutor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class UserControllerTest {

  private final String request = "request";
  private final String response = "response";
  private final UUID randomUUID = UUID.randomUUID();
  private final GenderType genderType = GenderType.PRIA;
  private final LocalDate birthDate = LocalDate.now();
  private final LocalDateTime createdAt = LocalDateTime.now();
  @MockBean
  private CommandExecutor commandExecutor;
  @Autowired
  private MockMvc mockMvc;
  private UpdateUserPasswordRequest updateUserPasswordRequest;
  private GetUserDetailResponse getUserDetailResponse;
  private HttpHeaders httpHeaders;

  @BeforeEach
  void setUp() {
    initMocks(this);
    updateUserPasswordRequest = UpdateUserPasswordRequest.builder()
        .userId(randomUUID)
        .userPassword("password")
        .userNewPassword("newPassword")
        .build();
    getUserDetailResponse = GetUserDetailResponse.builder()
        .userId(randomUUID)
        .userEmail("email@gmail.com")
        .userName("User Name")
        .userBirthDate(birthDate)
        .userPhoneNumber("08126107686")
        .userGender(genderType)
        .userCreatedAt(createdAt)
        .build();
    httpHeaders = new HttpHeaders();
  }

  @Test
  void getUserData() throws Exception {
    when(commandExecutor.execute(GetUserDetailCommand.class, randomUUID))
        .thenReturn(Mono.just(getUserDetailResponse));

    MockHttpServletRequestBuilder requestBuilder = get(ApiPath.USER + "?id=" + randomUUID)
        .headers(httpHeaders)
        .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
        .andExpect(status().isOk());

    verify(commandExecutor).execute(GetUserDetailCommand.class, randomUUID);
  }

  @Test
  void updateUserPassword() throws Exception {
    when(commandExecutor.execute(UpdateUserPasswordCommand.class, updateUserPasswordRequest))
        .thenReturn(Mono.just(response));

    MockHttpServletRequestBuilder requestBuilder = put(ApiPath.USER_UPDATE_PASSWORD)
        .headers(httpHeaders)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(updateUserPasswordRequest));

    mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
        .andExpect(status().isOk());

    verify(commandExecutor).execute(UpdateUserPasswordCommand.class, updateUserPasswordRequest);
  }
}