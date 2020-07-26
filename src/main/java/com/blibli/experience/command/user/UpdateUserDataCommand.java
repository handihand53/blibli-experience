package com.blibli.experience.command.user;

import com.blibli.experience.model.request.user.UpdateUserDataRequest;
import com.blibli.experience.model.response.user.UpdateUserDataResponse;
import com.blibli.oss.command.Command;

public interface UpdateUserDataCommand extends Command<UpdateUserDataRequest, UpdateUserDataResponse> {
}
