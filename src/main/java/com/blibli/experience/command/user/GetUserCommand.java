package com.blibli.experience.command.user;

import com.blibli.experience.model.response.user.GetUserResponse;
import com.blibli.oss.command.Command;

import java.util.UUID;

public interface GetUserCommand extends Command<UUID, GetUserResponse> {
}
