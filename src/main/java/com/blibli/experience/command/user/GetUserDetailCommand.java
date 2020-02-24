package com.blibli.experience.command.user;

import com.blibli.experience.model.response.user.GetUserDetailResponse;
import com.blibli.oss.command.Command;

import java.util.UUID;

public interface GetUserDetailCommand extends Command<UUID, GetUserDetailResponse> {
}
