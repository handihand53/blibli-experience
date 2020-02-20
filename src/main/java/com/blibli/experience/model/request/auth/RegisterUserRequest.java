package com.blibli.experience.model.request.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequest {

  @NotBlank
  private String email;

  @NotBlank
  private String password;

  @NotBlank
  private String fullName;

  private LocalDate birthDate;

}
