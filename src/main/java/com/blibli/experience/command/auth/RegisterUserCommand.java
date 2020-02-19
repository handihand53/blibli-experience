package com.blibli.experience.command.auth;

import com.blibli.experience.model.request.auth.RegisterUserRequest;
import com.blibli.experience.model.response.auth.RegisterUserResponse;
import com.blibli.oss.command.Command;

public interface RegisterUserCommand extends Command<RegisterUserRequest, RegisterUserResponse> {
}
