package com.blibli.experience.entity.dto;

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
public class UserRoleDto {

  private UUID userId;
  private UUID shopId;
  private List<UserRole> userRoles;

}
