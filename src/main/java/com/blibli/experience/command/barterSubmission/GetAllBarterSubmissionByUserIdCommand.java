package com.blibli.experience.command.barterSubmission;

import com.blibli.experience.model.response.barterSubmission.GetAllBarterSubmissionByUserIdResponse;
import com.blibli.oss.command.Command;

import java.util.List;
import java.util.UUID;

public interface GetAllBarterSubmissionByUserIdCommand extends Command<UUID, List<GetAllBarterSubmissionByUserIdResponse>> {
}
