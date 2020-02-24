package com.blibli.experience.model.request.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequest {

  @NotBlank
  private String userEmail;

  @NotBlank
  @Length(max = 254)
  private String userPassword;

  @NotBlank
  private String userName;

}
