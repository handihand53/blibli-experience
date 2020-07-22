package com.blibli.experience.command.barterSubmission;

import com.blibli.experience.model.response.barterSubmission.GetAllBarterSubmissionByProductBarterResponse;
import com.blibli.oss.command.Command;

import java.util.List;
import java.util.UUID;

public interface GetAllBarterSubmissionByProductBarterCommand extends Command<UUID, List<GetAllBarterSubmissionByProductBarterResponse>> {
}
