package com.blibli.experience.commandImpl.user;

import com.blibli.experience.command.user.UpdateUserDataCommand;
import com.blibli.experience.entity.document.User;
import com.blibli.experience.enums.GenderType;
import com.blibli.experience.model.request.user.UpdateUserDataRequest;
import com.blibli.experience.model.response.user.UpdateUserDataResponse;
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
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UpdateUserDataCommandImplTest {

    private final UUID randomUUID = UUID.randomUUID();
    private final GenderType genderType = GenderType.PRIA;
    private final LocalDate birthDate = LocalDate.now();
    private final LocalDateTime createdAt = LocalDateTime.now();

    @InjectMocks
    UpdateUserDataCommandImpl updateUserDataCommand;

    @Mock
    UserRepository userRepository;

    private User user;
    private UpdateUserDataRequest request;
    private UpdateUserDataResponse response;

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
        request = UpdateUserDataRequest.builder()
                .userId(randomUUID)
                .build();
        response = UpdateUserDataResponse.builder()
                .userId(randomUUID)
                .userEmail("email@gmail.com")
                .userName("User Name")
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
        when(userRepository.save(user))
                .thenReturn(Mono.just(user));

        UpdateUserDataResponse result = updateUserDataCommand.execute(request).block();
        assertEquals(response, result);

        verify(userRepository).findFirstByUserId(randomUUID);
        verify(userRepository).save(user);
    }

}
