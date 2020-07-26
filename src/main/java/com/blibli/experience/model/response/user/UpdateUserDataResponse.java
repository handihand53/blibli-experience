package com.blibli.experience.model.response.user;

import com.blibli.experience.entity.form.AddressForm;
import com.blibli.experience.enums.GenderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDataResponse {

    private UUID userId;
    private String userEmail;
    private String userName;
    private List<AddressForm> userAddressForms;
    private LocalDate userBirthDate;
    private String userPhoneNumber;
    private GenderType userGender;
    private LocalDateTime userCreatedAt;

}
