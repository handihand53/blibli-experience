package com.blibli.experience.model.response.auth;

import com.blibli.experience.enums.UserRole;
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
public class LoginUserResponse {

  private UUID userId;
  private UUID shopId;
  private List<UserRole> userRoles;
  private String accessToken;
  private String tokenType = "Bearer";

  public LoginUserResponse(UUID userId, UUID shopId, List<UserRole> userRoles, String accessToken) {
    this.userId = userId;
    this.shopId = shopId;
    this.userRoles = userRoles;
    this.accessToken = accessToken;
  }

}
