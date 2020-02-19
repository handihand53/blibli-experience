package com.blibli.experience.model.request.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserPasswordRequest {

  private UUID id;

  @NotBlank
  private String password;

  @NotBlank
  private String newPassword;

}
