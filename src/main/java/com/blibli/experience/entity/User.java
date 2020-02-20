package com.blibli.experience.entity;

import com.blibli.experience.enums.GenderType;
import com.blibli.experience.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = User.COLLECTION_NAME)
public class User {

  public static final String COLLECTION_NAME = "user";
  public static final String ID = "id";
  public static final String EMAIL = "email";
  public static final String PASSWORD = "password";
  public static final String FULL_NAME = "fullName";
  public static final String PHONE_NUMBER = "phoneNumber";
  public static final String GENDER = "gender";
  public static final String BIRTH_DATE = "birthDate";
  public static final String ROLE = "role";
  public static final String CREATED_AT = "createdAt";

  @Id
  @Field(value = ID)
  private UUID id;

  @Field(value = EMAIL)
  @Indexed(unique = true)
  @Length(max = 254)
  private String email;

  @Field(value = PASSWORD)
  private String password;

  @Field(value = FULL_NAME)
  @Length(max = 30)
  private String fullName;

  @Field(value = BIRTH_DATE)
  private LocalDate birthDate;

  @Field(value = PHONE_NUMBER)
  @Length(max = 15)
  private String phoneNumber;

  @Field(value = GENDER)
  private GenderType gender;

  @Field(value = ROLE)
  private List<UserRole> userRole;

  @Field(value = CREATED_AT)
  private LocalDateTime createdAt;

}
