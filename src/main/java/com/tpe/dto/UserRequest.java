package com.tpe.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

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
    private String userName;

    @NotBlank(message = "Please Provide password")
    private String password;


}
