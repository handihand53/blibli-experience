package com.blibli.experience.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private UUID userId;
    private String userEmail;
    private String userName;
    private List<AddressDto> userAddressDtos;
    private String userPhoneNumber;
    private String userIdentityId;

}
