package com.blibli.experience.command.productBarter;

import com.blibli.experience.model.response.productBarter.GetProductBarterAvailableResponse;
import com.blibli.oss.command.Command;

import java.util.List;

public interface GetProductBarterAvailableCommand extends Command<Integer, List<GetProductBarterAvailableResponse>> {
}
