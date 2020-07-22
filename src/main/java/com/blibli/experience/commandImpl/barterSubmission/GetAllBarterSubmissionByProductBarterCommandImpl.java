package com.blibli.experience.commandImpl.barterSubmission;

import com.blibli.experience.command.barterSubmission.GetAllBarterSubmissionByProductBarterCommand;
import com.blibli.experience.entity.document.BarterSubmission;
import com.blibli.experience.model.response.barterSubmission.GetAllBarterSubmissionByProductBarterResponse;
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
public class GetAllBarterSubmissionByProductBarterCommandImpl implements GetAllBarterSubmissionByProductBarterCommand {

    private BarterSubmissionRepository barterSubmissionRepository;

    @Autowired
    public GetAllBarterSubmissionByProductBarterCommandImpl(BarterSubmissionRepository barterSubmissionRepository) {
        this.barterSubmissionRepository = barterSubmissionRepository;
    }

    @Override
    public Mono<List<GetAllBarterSubmissionByProductBarterResponse>> execute(UUID request) {
        return barterSubmissionRepository.findAllByBarterSubmissionTargetBarter_ProductBarterId(request)
                .switchIfEmpty(Mono.error(new NotFoundException("Submission not found.")))
                .map(this::toResponse)
                .collectList();
    }

    private GetAllBarterSubmissionByProductBarterResponse toResponse(BarterSubmission barterSubmission) {
        GetAllBarterSubmissionByProductBarterResponse response = new GetAllBarterSubmissionByProductBarterResponse();
        BeanUtils.copyProperties(barterSubmission, response);
        return response;
    }
}
