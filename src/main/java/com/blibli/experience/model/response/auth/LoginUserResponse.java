package com.blibli.experience.model.response.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserResponse {

  private String userId;
  private String accessToken;
  private String tokenType = "Bearer";

  public LoginUserResponse(String accessToken, String userId) {
    this.accessToken = accessToken;
    this.userId = userId;
  }

}
