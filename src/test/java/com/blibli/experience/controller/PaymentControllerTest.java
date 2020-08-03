package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.payment.PostPaymentCommand;
import com.blibli.experience.command.productMaster.GenerateProductMasterQRCodeCommand;
import com.blibli.experience.enums.UserRole;
import com.blibli.experience.model.request.payment.PostPaymentRequest;
import com.blibli.experience.model.response.payment.PostPaymentResponse;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PaymentControllerTest {

    private final UUID randomUUID = UUID.randomUUID();

    @MockBean
    private CommandExecutor commandExecutor;

    @Autowired
    private MockMvc mockMvc;
    private HttpHeaders userHttpHeaders;
    private PostPaymentRequest postPaymentRequest;
    private PostPaymentResponse postPaymentResponse;

    @BeforeEach
    void setUp() {
        String userId = "9edae4d1-8df9-48d5-a375-fb413e9ffff3";
        userHttpHeaders = new HttpHeaders();
        userHttpHeaders.setBearerAuth(JwtTokenProvider.generateTokenFromRole(UserRole.ROLE_USER, userId));

        postPaymentRequest = PostPaymentRequest.builder()
                .orderId(randomUUID)
                .build();
        postPaymentResponse = PostPaymentResponse.builder()
                .paymentId(randomUUID)
                .build();
    }

    @Test
    void postPayment() throws Exception {
        when(commandExecutor.execute(PostPaymentCommand.class, postPaymentRequest))
                .thenReturn(Mono.just(postPaymentResponse));

        MockHttpServletRequestBuilder requestBuilder = post(ApiPath.PAYMENT)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(postPaymentRequest));

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(PostPaymentCommand.class, postPaymentRequest);

    }

}
