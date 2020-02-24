package com.blibli.experience.commandImpl.user;

import com.blibli.experience.entity.document.User;
import com.blibli.experience.enums.GenderType;
import com.blibli.experience.model.response.user.GetUserDetailResponse;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class GetUserDetailCommandImplTest {

  private final UUID randomUUID = UUID.randomUUID();
  private final GenderType genderType = GenderType.PRIA;
  private final LocalDate birthDate = LocalDate.now();
  private final LocalDateTime createdAt = LocalDateTime.now();

  @InjectMocks
  private GetUserDetailCommandImpl getUserCommand;

  @Mock

  private UserRepository userRepository;
  private User user;
  private GetUserDetailResponse response;

  @BeforeEach
  void setUp() {
    initMocks(this);
    user = User.builder()
        .userId(randomUUID)
        .userEmail("email@gmail.com")
        .userPassword("password")
        .userName("User Name")
        .userBirthDate(birthDate)
        .userPhoneNumber("08126107686")
        .userGender(genderType)
        .userCreatedAt(createdAt)
        .userIdentityId("01679765443368363")
        .build();
    response = GetUserDetailResponse.builder()
        .userId(randomUUID)
        .userEmail("email@gmail.com")
        .userName("User Name")
        .userBirthDate(birthDate)
        .userPhoneNumber("08126107686")
        .userGender(genderType)
        .userCreatedAt(createdAt)
        .build();
  }

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(userRepository);
  }

  @Test
  void execute() {
    when(userRepository.findFirstByUserId(randomUUID))
        .thenReturn(Mono.just(user));

    GetUserDetailResponse result = getUserCommand.execute(randomUUID).block();
    assertEquals(response, result);

    verify(userRepository).findFirstByUserId(randomUUID);
  }
}