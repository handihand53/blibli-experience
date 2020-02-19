package com.blibli.experience.entity;

import com.blibli.experience.enums.GenderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
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
  public static final String CREATED_AT = "createdAt";

  @Id
  @Field(value = User.ID)
  private UUID id;

  @Field(value = User.EMAIL)
  @Indexed(unique = true)
  @Length(max = 254)
  private String email;

  @Field(value = User.PASSWORD)
  private String password;

  @Field(value = User.FULL_NAME)
  @Length(max = 30)
  private String fullName;

  @Field(value = PHONE_NUMBER)
  @Length(max = 15)
  private String phoneNumber;

  @Field(value = User.GENDER)
  private GenderType gender;

  @Field(value = BIRTH_DATE)
  private Date birthDate;

  @Field(value = User.CREATED_AT)
  private String createdAt;

}
