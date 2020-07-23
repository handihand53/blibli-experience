package com.blibli.experience.commandImpl.barterSubmission;

import com.blibli.experience.command.barterSubmission.GetAllBarterSubmissionByUserIdCommand;
import com.blibli.experience.entity.document.BarterSubmission;
import com.blibli.experience.model.response.barterSubmission.GetAllBarterSubmissionByUserIdResponse;
import com.blibli.experience.repository.BarterSubmissionRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class GetAllBarterSubmissionByUserIdCommandImpl implements GetAllBarterSubmissionByUserIdCommand {

    private BarterSubmissionRepository barterSubmissionRepository;

    @Autowired
    public GetAllBarterSubmissionByUserIdCommandImpl(BarterSubmissionRepository barterSubmissionRepository) {
        this.barterSubmissionRepository = barterSubmissionRepository;
    }

    @Override
    public Mono<List<GetAllBarterSubmissionByUserIdResponse>> execute(UUID request) {
        return barterSubmissionRepository.findAllByUserData_UserId(request)
                .switchIfEmpty(Mono.error(new NotFoundException("Barter submission not found.")))
                .map(this::toResponse)
                .collectList();
    }

    private GetAllBarterSubmissionByUserIdResponse toResponse(BarterSubmission barterSubmission) {
        GetAllBarterSubmissionByUserIdResponse response = new GetAllBarterSubmissionByUserIdResponse();
        BeanUtils.copyProperties(barterSubmission, response);
        return response;
    }

}
