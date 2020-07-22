package com.blibli.experience.command.barterSubmission;

import com.blibli.experience.model.response.barterSubmission.GetBarterSubmissionDetailResponse;
import com.blibli.oss.command.Command;

import java.util.UUID;

public interface GetBarterSubmissionDetailCommand extends Command<UUID, GetBarterSubmissionDetailResponse> {
}
