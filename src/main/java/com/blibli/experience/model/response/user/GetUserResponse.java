package com.blibli.experience.model.response.user;

import com.blibli.experience.enums.GenderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserResponse {

  private UUID id;
  private String fullName;
  private String email;
  private Date birthDate;
  private String phoneNumber;
  private GenderType gender;
  private Date createdAt;

}
