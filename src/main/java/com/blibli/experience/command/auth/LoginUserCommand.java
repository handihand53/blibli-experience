package com.blibli.experience.command.auth;

import com.blibli.experience.model.request.auth.LoginUserRequest;
import com.blibli.experience.model.response.auth.LoginUserResponse;
import com.blibli.oss.command.Command;

public interface LoginUserCommand extends Command<LoginUserRequest, LoginUserResponse> {
}
