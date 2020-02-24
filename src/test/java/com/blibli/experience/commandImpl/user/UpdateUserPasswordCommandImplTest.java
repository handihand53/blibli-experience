package com.blibli.experience.commandImpl.user;

import com.blibli.experience.entity.document.User;
import com.blibli.experience.enums.GenderType;
import com.blibli.experience.model.request.user.UpdateUserPasswordRequest;
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

class UpdateUserPasswordCommandImplTest {

  private final UUID randomUUID = UUID.randomUUID();
  private final GenderType genderType = GenderType.PRIA;
  private final LocalDate birthDate = LocalDate.now();
  private final LocalDateTime createdAt = LocalDateTime.now();

  @InjectMocks
  UpdateUserPasswordCommandImpl updateUserPasswordCommand;

  @Mock
  UserRepository userRepository;

  private User user;
  private UpdateUserPasswordRequest request;

  @BeforeEach
  void setUp() {
    initMocks(this);
    user = User.builder()
        .userId(randomUUID)
        .userEmail("email@gmail.com")
        .userPassword("$2a$10$2a0RGB.YJNiNbhBkDnsbhubBRgr5Ys5hZqLOvRpRVnx34B3aVPGta")
        .userName("User Name")
        .userBirthDate(birthDate)
        .userPhoneNumber("08126107686")
        .userGender(genderType)
        .userCreatedAt(createdAt)
        .userIdentityId("01679765443368363")
        .build();
    request = UpdateUserPasswordRequest.builder()
        .userId(randomUUID)
        .userPassword("password")
        .userNewPassword("newPassword")
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
    when(userRepository.save(user))
        .thenReturn(Mono.just(user));

    String result = updateUserPasswordCommand.execute(request).block();
    String expected = "Success!";
    assertEquals(expected, result);

    verify(userRepository).findFirstByUserId(randomUUID);
    verify(userRepository).save(user);
  }
}