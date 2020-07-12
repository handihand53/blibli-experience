package com.blibli.experience.command.productMaster;

import com.blibli.experience.model.response.productMaster.GetAllProductMasterResponse;
import com.blibli.oss.command.Command;

import java.util.List;

public interface GetAllProductMasterCommand extends Command<Integer, List<GetAllProductMasterResponse>> {
}
