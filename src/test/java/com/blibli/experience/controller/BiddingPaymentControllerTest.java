package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.biddingPayment.PostBiddingPaymentCommand;
import com.blibli.experience.enums.UserRole;
import com.blibli.experience.model.request.biddingPayment.PostBiddingPaymentRequest;
import com.blibli.experience.model.response.biddingPayment.PostBiddingPaymentResponse;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BiddingPaymentControllerTest {

    private final UUID randomUUID = UUID.randomUUID();

    @MockBean
    private CommandExecutor commandExecutor;

    @Autowired
    private MockMvc mockMvc;
    private HttpHeaders userHttpHeaders;
    private PostBiddingPaymentRequest postBiddingPaymentRequest;
    private PostBiddingPaymentResponse postBiddingPaymentResponse;

    @BeforeEach
    void setUp() {
        initMocks(this);
        String userId = "9edae4d1-8df9-48d5-a375-fb413e9ffff3";
        userHttpHeaders = new HttpHeaders();
        userHttpHeaders.setBearerAuth(JwtTokenProvider.generateTokenFromRole(UserRole.ROLE_USER, userId));

        postBiddingPaymentRequest = PostBiddingPaymentRequest.builder()
                .biddingOrderId(randomUUID)
                .build();
        postBiddingPaymentResponse = PostBiddingPaymentResponse.builder()
                .biddingOrderPaymentId(randomUUID)
                .build();
    }

    @Test
    void postBiddingPayment() throws Exception {
        when(commandExecutor.execute(PostBiddingPaymentCommand.class, postBiddingPaymentRequest))
                .thenReturn(Mono.just(postBiddingPaymentResponse));

        MockHttpServletRequestBuilder requestBuilder = post(ApiPath.BIDDING_PAYMENT)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(postBiddingPaymentRequest));

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(PostBiddingPaymentCommand.class, postBiddingPaymentRequest);

    }

}
