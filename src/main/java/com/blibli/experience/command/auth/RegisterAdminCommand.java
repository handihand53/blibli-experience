package com.blibli.experience.command.auth;

import com.blibli.experience.model.request.auth.RegisterAdminRequest;
import com.blibli.experience.model.response.auth.RegisterAdminResponse;
import com.blibli.oss.command.Command;

public interface RegisterAdminCommand extends Command<RegisterAdminRequest, RegisterAdminResponse> {
}
