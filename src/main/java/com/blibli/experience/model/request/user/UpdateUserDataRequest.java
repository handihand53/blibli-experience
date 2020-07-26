package com.blibli.experience.model.request.user;

import com.blibli.experience.entity.form.AddressForm;
import com.blibli.experience.enums.GenderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDataRequest {

    @NotNull
    private UUID userId;

    private List<AddressForm> userAddressForms;
    private LocalDate userBirthDate;
    private String userPhoneNumber;
    private GenderType userGender;
    private String userIdentityId;

}
