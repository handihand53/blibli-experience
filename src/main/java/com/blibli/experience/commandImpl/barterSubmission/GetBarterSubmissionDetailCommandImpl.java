package com.blibli.experience.commandImpl.barterSubmission;

import com.blibli.experience.command.barterSubmission.GetBarterSubmissionDetailCommand;
import com.blibli.experience.entity.document.BarterSubmission;
import com.blibli.experience.model.response.barterSubmission.GetBarterSubmissionDetailResponse;
import com.blibli.experience.repository.BarterSubmissionRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
public class GetBarterSubmissionDetailCommandImpl implements GetBarterSubmissionDetailCommand {

    private BarterSubmissionRepository barterSubmissionRepository;

    @Autowired
    public GetBarterSubmissionDetailCommandImpl(BarterSubmissionRepository barterSubmissionRepository) {
        this.barterSubmissionRepository = barterSubmissionRepository;
    }

    @Override
    public Mono<GetBarterSubmissionDetailResponse> execute(UUID request) {
        return barterSubmissionRepository.findByBarterSubmissionId(request)
                .switchIfEmpty(Mono.error(new NotFoundException("Submission not found.")))
                .map(this::toResponse);
    }

    private GetBarterSubmissionDetailResponse toResponse(BarterSubmission barterSubmission) {
        GetBarterSubmissionDetailResponse response = new GetBarterSubmissionDetailResponse();
        BeanUtils.copyProperties(barterSubmission, response);
        return response;
    }
}
