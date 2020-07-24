package com.blibli.experience.command.productBarter;

import com.blibli.experience.model.response.productBarter.GetAllProductBarterAvailableResponse;
import com.blibli.oss.command.Command;

import java.util.List;

public interface GetAllProductBarterAvailableCommand extends Command<Integer, List<GetAllProductBarterAvailableResponse>> {
}
