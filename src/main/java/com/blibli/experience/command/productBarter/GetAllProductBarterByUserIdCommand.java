package com.blibli.experience.command.productBarter;

import com.blibli.experience.model.response.productBarter.GetAllProductBarterByUserIdResponse;
import com.blibli.oss.command.Command;

import java.util.List;
import java.util.UUID;

public interface GetAllProductBarterByUserIdCommand extends Command<UUID, List<GetAllProductBarterByUserIdResponse>> {
}
