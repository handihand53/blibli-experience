package com.blibli.experience.command.user;

import com.blibli.experience.model.request.user.UpdateUserPasswordRequest;
import com.blibli.oss.command.Command;

public interface UpdateUserPasswordCommand extends Command<UpdateUserPasswordRequest, String> {
}
