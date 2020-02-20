package com.blibli.experience.commandImpl.user;

import com.blibli.experience.entity.User;
import com.blibli.experience.enums.GenderType;
import com.blibli.experience.model.response.user.GetUserResponse;
import com.blibli.experience.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class GetUserCommandImplTest {

  @InjectMocks
  private GetUserCommandImpl getUserCommand;

  @Mock
  private UserRepository userRepository;

  private User user;
  private GetUserResponse response;
  private final UUID randomUUID = UUID.randomUUID();
  private final GenderType genderType = GenderType.PRIA;
  private final LocalDate birthDate = LocalDate.now();
  private final LocalDateTime createdAt = LocalDateTime.now();

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
    response = GetUserResponse.builder()
        .id(randomUUID)
        .email("email@gmail.com")
        .fullName("Full Name")
        .birthDate(birthDate)
        .phoneNumber("08126107686")
        .gender(genderType)
        .createdAt(createdAt)
        .build();
  }

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(userRepository);
  }

  @Test
  void execute() {
    when(userRepository.getFirstById(randomUUID))
        .thenReturn(Mono.just(user));

    GetUserResponse result = getUserCommand.execute(randomUUID).block();
    assertEquals(response, result);

    verify(userRepository).getFirstById(randomUUID);
  }
}