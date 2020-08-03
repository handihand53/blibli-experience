package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.barterSubmission.GetAllBarterSubmissionByProductBarterCommand;
import com.blibli.experience.command.barterSubmission.GetAllBarterSubmissionByUserIdCommand;
import com.blibli.experience.command.barterSubmission.GetBarterSubmissionDetailCommand;
import com.blibli.experience.command.barterSubmission.PostBarterSubmissionCommand;
import com.blibli.experience.command.productMaster.GenerateProductMasterQRCodeCommand;
import com.blibli.experience.command.productMaster.GetAllProductMasterCommand;
import com.blibli.experience.command.productMaster.GetProductMasterMetadataCommand;
import com.blibli.experience.enums.UserRole;
import com.blibli.experience.model.request.barterSubmission.PostBarterSubmissionRequest;
import com.blibli.experience.model.response.barterSubmission.GetAllBarterSubmissionByProductBarterResponse;
import com.blibli.experience.model.response.barterSubmission.GetAllBarterSubmissionByUserIdResponse;
import com.blibli.experience.model.response.barterSubmission.GetBarterSubmissionDetailResponse;
import com.blibli.experience.model.response.barterSubmission.PostBarterSubmissionResponse;
import com.blibli.experience.model.response.productMaster.GetAllProductMasterResponse;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BarterSubmissionControllerTest {

    private final UUID randomUUID = UUID.randomUUID();

    @MockBean
    private CommandExecutor commandExecutor;

    @Autowired
    private MockMvc mockMvc;
    private HttpHeaders userHttpHeaders;
    private PostBarterSubmissionRequest postBarterSubmissionRequest;
    private GetBarterSubmissionDetailResponse getBarterSubmissionDetailResponse;
    private GetAllBarterSubmissionByUserIdResponse getAllBarterSubmissionByUserIdResponse;
    private GetAllBarterSubmissionByProductBarterResponse getAllBarterSubmissionByProductBarterResponse;
    private PostBarterSubmissionResponse postBarterSubmissionResponse;

    @BeforeEach
    void setUp() {
        initMocks(this);
        String userId = "9edae4d1-8df9-48d5-a375-fb413e9ffff3";
        userHttpHeaders = new HttpHeaders();
        userHttpHeaders.setBearerAuth(JwtTokenProvider.generateTokenFromRole(UserRole.ROLE_USER, userId));

        postBarterSubmissionRequest = PostBarterSubmissionRequest.builder()
                .productBarterId(randomUUID)
                .build();
        getBarterSubmissionDetailResponse = GetBarterSubmissionDetailResponse.builder()
                .barterSubmissionId(randomUUID)
                .build();
        getAllBarterSubmissionByUserIdResponse = GetAllBarterSubmissionByUserIdResponse.builder()
                .barterSubmissionId(randomUUID)
                .build();
        getAllBarterSubmissionByProductBarterResponse = GetAllBarterSubmissionByProductBarterResponse.builder()
                .barterSubmissionId(randomUUID)
                .build();
        postBarterSubmissionResponse = PostBarterSubmissionResponse.builder()
                .barterSubmissionId(randomUUID)
                .build();
    }

    @Test
    void getBarterSubmissionDetail() throws Exception {
        when(commandExecutor.execute(GetBarterSubmissionDetailCommand.class, randomUUID))
                .thenReturn(Mono.just(getBarterSubmissionDetailResponse));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.BARTER_SUBMISSION + "?barterSubmissionId=" + randomUUID)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetBarterSubmissionDetailCommand.class, randomUUID);

    }

    @Test
    void getAllBarterSubmissionByUserId() throws Exception {
        List<GetAllBarterSubmissionByUserIdResponse> getAllBarterSubmissionByUserIdResponses = new ArrayList<>();
        getAllBarterSubmissionByUserIdResponses.add(getAllBarterSubmissionByUserIdResponse);
        when(commandExecutor.execute(GetAllBarterSubmissionByUserIdCommand.class, randomUUID))
                .thenReturn(Mono.just(getAllBarterSubmissionByUserIdResponses));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.SUBMISSION_BY_USER + "?userId=" + randomUUID)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetAllBarterSubmissionByUserIdCommand.class, randomUUID);

    }

    @Test
    void getAllBarterSubmissionByProductBarter() throws Exception {
        List<GetAllBarterSubmissionByProductBarterResponse> getAllBarterSubmissionByProductBarterResponses = new ArrayList<>();
        getAllBarterSubmissionByProductBarterResponses.add(getAllBarterSubmissionByProductBarterResponse);
        when(commandExecutor.execute(GetAllBarterSubmissionByProductBarterCommand.class, randomUUID))
                .thenReturn(Mono.just(getAllBarterSubmissionByProductBarterResponses));

        MockHttpServletRequestBuilder requestBuilder = get(ApiPath.SUBMISSION_BY_PRODUCT_BARTER + "?productBarterId=" + randomUUID)
                .headers(userHttpHeaders)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
                .andExpect(status().isOk());

        verify(commandExecutor).execute(GetAllBarterSubmissionByProductBarterCommand.class, randomUUID);

    }

    @Test
    void postBarterSubmission() throws Exception {
//        when(commandExecutor.execute(PostBarterSubmissionCommand.class, postBarterSubmissionRequest))
//                .thenReturn(Mono.just(postBarterSubmissionResponse));
//
//        MockHttpServletRequestBuilder requestBuilder = post(ApiPath.BARTER_SUBMISSION)
//                .headers(userHttpHeaders)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON);
//
//        mockMvc.perform(asyncDispatch(mockMvc.perform(requestBuilder).andReturn()))
//                .andExpect(status().isOk());
//
//        verify(commandExecutor).execute(PostBarterSubmissionCommand.class, postBarterSubmissionRequest);

    }
}
