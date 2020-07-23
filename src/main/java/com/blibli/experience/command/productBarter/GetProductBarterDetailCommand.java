package com.blibli.experience.command.productBarter;

import com.blibli.experience.model.response.productBarter.GetProductBarterDetailResponse;
import com.blibli.oss.command.Command;

import java.util.UUID;

public interface GetProductBarterDetailCommand extends Command<UUID, GetProductBarterDetailResponse> {
}
