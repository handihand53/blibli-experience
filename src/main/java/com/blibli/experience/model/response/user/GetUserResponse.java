package com.blibli.experience.model.response.user;

import com.blibli.experience.enums.GenderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserResponse {

  private UUID id;
  private String email;
  private String fullName;
  private LocalDate birthDate;
  private String phoneNumber;
  private GenderType gender;
  private LocalDateTime createdAt;

}
