package com.blibli.experience.command.auth;

import com.blibli.experience.model.request.auth.RegisterShopRequest;
import com.blibli.experience.model.response.auth.RegisterShopResponse;
import com.blibli.oss.command.Command;

public interface RegisterShopCommand extends Command<RegisterShopRequest, RegisterShopResponse> {
}
