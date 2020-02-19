package com.blibli.experience;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
    org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class BlibliExperienceApplication {

  public static void main(String[] args) {
    SpringApplication.run(BlibliExperienceApplication.class, args);
  }

}
