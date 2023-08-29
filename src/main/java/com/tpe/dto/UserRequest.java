package com.tpe.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserRequest {

    @NotBlank(message = "Please Provide FirstName")
    private String firstName;

    @NotBlank(message = "Please Provide lastName")
    private String lastName;

    @NotBlank(message = "Please Provide userName")
    @Size(min=5,max=10, message="Please provide a User Name min=5, max=10 chars long")
    private String userName;

    @NotBlank(message = "Please Provide password")
    private String password;


}
