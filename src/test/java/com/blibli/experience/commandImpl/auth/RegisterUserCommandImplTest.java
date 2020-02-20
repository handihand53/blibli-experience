package com.blibli.experience.commandImpl.auth;

import com.blibli.experience.entity.User;
import com.blibli.experience.enums.GenderType;
import com.blibli.experience.model.request.auth.RegisterUserRequest;
import com.blibli.experience.model.response.auth.RegisterUserResponse;
import com.blibli.experience.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class RegisterUserCommandImplTest {

  private final UUID randomUUID = UUID.randomUUID();
  private final GenderType genderType = GenderType.PRIA;
  private final LocalDate birthDate = LocalDate.now();
  private final LocalDateTime createdAt = LocalDateTime.now();
  @InjectMocks
  RegisterUserCommandImpl registerUserCommand;
  @Mock
  UserRepository userRepository;
  private User user;
  private RegisterUserRequest request;
  private RegisterUserResponse response;

  @BeforeEach
  void setUp() {
    initMocks(this);
    user = User.builder()
        .id(randomUUID)
        .email("email@gmail.com")
        .password("password")
        .fullName("Full Name")
        .birthDate(birthDate)
        .phoneNumber("08126107686")
        .gender(genderType)
        .createdAt(createdAt)
        .build();
    request = RegisterUserRequest.builder()
        .email("email@gmail.com")
        .password("password")
        .fullName("Full Name")
        .build();
    response = RegisterUserResponse.builder()
        .id(randomUUID)
        .email("email@gmail.com")
        .fullName("Full Name")
        .build();
  }

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(userRepository);
  }

  @Test
  void execute() {
    when(userRepository.save(any(User.class)))
        .thenReturn(Mono.just(user));

    RegisterUserResponse result = registerUserCommand.execute(request).block();
    assertEquals(response, result);

    verify(userRepository).save(any(User.class));
  }
}