package com.blibli.experience.command.productBarter;

import com.blibli.experience.model.request.productBarter.GetAllProductBarterAvailableAndCategoryRequest;
import com.blibli.experience.model.response.productBarter.GetAllProductBarterAvailableAndCategoryResponse;
import com.blibli.oss.command.Command;

import java.util.List;

public interface GetAllProductBarterAvailableAndCategoryCommand extends Command<GetAllProductBarterAvailableAndCategoryRequest, List<GetAllProductBarterAvailableAndCategoryResponse>> {
}
