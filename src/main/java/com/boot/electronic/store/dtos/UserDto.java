package com.boot.electronic.store.dtos;

import com.boot.electronic.store.valid.ImageNameValid;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {


    private String userId;
 @Size(min = 3,max = 20,message = "Invalid name !!!")
    private String name;
 //@Email(message = "Invalid Email")
 @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$",message="Please provide valid email")
    private String email;
@NotBlank(message = "Password is required!!!")
    private String password;
  @Size(min = 3,max = 6,message = "invalid gender !!!")
    private String gender;
   @NotBlank
    private String about;
@ImageNameValid(message = "Enter a valid message")
    private String imagename;




}
