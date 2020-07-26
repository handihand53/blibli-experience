package com.blibli.experience.controller;

import com.blibli.experience.ApiPath;
import com.blibli.experience.command.auth.RegisterAdminCommand;
import com.blibli.experience.command.auth.RegisterShopCommand;
import com.blibli.experience.command.auth.RegisterUserCommand;
import com.blibli.experience.entity.form.UserRoleForm;
import com.blibli.experience.model.request.auth.LoginUserRequest;
import com.blibli.experience.model.request.auth.RegisterAdminRequest;
import com.blibli.experience.model.request.auth.RegisterShopRequest;
import com.blibli.experience.model.request.auth.RegisterUserRequest;
import com.blibli.experience.model.response.auth.LoginUserResponse;
import com.blibli.experience.model.response.auth.RegisterAdminResponse;
import com.blibli.experience.model.response.auth.RegisterShopResponse;
import com.blibli.experience.model.response.auth.RegisterUserResponse;
import com.blibli.experience.security.JwtTokenProvider;
import com.blibli.oss.command.CommandExecutor;
import com.blibli.oss.common.response.Response;
import com.blibli.oss.common.response.ResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private CommandExecutor commandExecutor;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider tokenProvider;

    @Autowired
    public AuthController(CommandExecutor commandExecutor, AuthenticationManager authenticationManager,
                          JwtTokenProvider tokenProvider) {
        this.commandExecutor = commandExecutor;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping(value = ApiPath.REGISTER,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response<RegisterUserResponse>> registerUser(@RequestBody RegisterUserRequest request) {
        return commandExecutor.execute(RegisterUserCommand.class, request)
                .log("#CommandExecutor - Executing RegisterUserCommand...")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @PostMapping(value = ApiPath.LOGIN,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody LoginUserRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserEmail(),
                        request.getUserPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        UserRoleForm roleForm = tokenProvider.generateUserData(request.getUserEmail());
        return ResponseEntity.ok(new LoginUserResponse(roleForm.getUserId(), roleForm.getShopId(), roleForm.getUserRoles(), jwt));
    }

    @PostMapping(value = ApiPath.REGISTER_SHOP,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response<RegisterShopResponse>> registerShop(@RequestBody RegisterShopRequest request) {
        return commandExecutor.execute(RegisterShopCommand.class, request)
                .log("#CommandExecutor - Executing RegisterShopCommand...")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

    @PostMapping(value = ApiPath.REGISTER_ADMIN,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Response<RegisterAdminResponse>> registerAdmin(@RequestBody RegisterAdminRequest request) {
        return commandExecutor.execute(RegisterAdminCommand.class, request)
                .log("#CommandExecutor - Executing RegisterAdminCommand...")
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.elastic());
    }

}
