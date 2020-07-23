package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.barterSubmission.GetAllBarterSubmissionByProductBarterCommand;
import com.blibli.experience.command.barterSubmission.GetAllBarterSubmissionByUserIdCommand;
import com.blibli.experience.command.barterSubmission.GetBarterSubmissionDetailCommand;
import com.blibli.experience.command.barterSubmission.PostBarterSubmissionCommand;
import com.blibli.experience.command.productBarter.PostProductBarterCommand;
import com.blibli.experience.model.request.barterSubmission.PostBarterSubmissionRequest;
import com.blibli.experience.model.request.productBarter.PostProductBarterRequest;
import com.blibli.experience.model.response.barterSubmission.GetAllBarterSubmissionByProductBarterResponse;
import com.blibli.experience.model.response.barterSubmission.GetAllBarterSubmissionByUserIdResponse;
import com.blibli.experience.model.response.barterSubmission.GetBarterSubmissionDetailResponse;
import com.blibli.experience.model.response.barterSubmission.PostBarterSubmissionResponse;
import com.blibli.experience.model.response.productBarter.PostProductBarterResponse;
import com.blibli.oss.command.CommandExecutor;
import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class BarterSubmissionController {

    private CommandExecutor commandExecutor;
    private ObjectMapper objectMapper;

    @Autowired
    public BarterSubmissionController(CommandExecutor commandExecutor, ObjectMapper objectMapper) {
        this.commandExecutor = commandExecutor;
        this.objectMapper = objectMapper;
    }

    @GetMapping(value = ApiPath.BARTER_SUBMISSION)
    public Mono<Response<GetBarterSubmissionDetailResponse>> getBarterSubmissionDetail(
            @RequestParam UUID barterSubmissionId) {
        return commandExecutor.execute(GetBarterSubmissionDetailCommand.class, barterSubmissionId)
                .log("#getBarterSubmissionDetail - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(value = ApiPath.SUBMISSION_BY_USER)
    public Mono<Response<List<GetAllBarterSubmissionByUserIdResponse>>> getAllBarterSubmissionByUserId(
            @RequestParam UUID userId) {
        return commandExecutor.execute(GetAllBarterSubmissionByUserIdCommand.class, userId)
                .log("#getAllBarterSubmissionByUserId - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @GetMapping(value = ApiPath.SUBMISSION_BY_PRODUCT_BARTER)
    public Mono<Response<List<GetAllBarterSubmissionByProductBarterResponse>>> getAllBarterSubmissionByProductBarter(
            @RequestParam UUID productBarterId) {
        return commandExecutor.execute(GetAllBarterSubmissionByProductBarterCommand.class, productBarterId)
                .log("#getAllBarterSubmissionByProductBarter - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @PostMapping(value = ApiPath.BARTER_SUBMISSION)
    public Mono<Response<PostBarterSubmissionResponse>> postBarterSubmission(
            @RequestParam List<MultipartFile> images, @RequestParam String barterSubmissionMetaData) throws IOException {
        PostBarterSubmissionRequest request = objectMapper.readValue(barterSubmissionMetaData, PostBarterSubmissionRequest.class);
        request.setBarterSubmissionImages(images);
        return commandExecutor.execute(PostBarterSubmissionCommand.class, request)
                .log("#postBarterSubmission - Successfully executing command.")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

}
