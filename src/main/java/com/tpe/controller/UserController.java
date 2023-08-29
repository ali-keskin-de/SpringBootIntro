package com.tpe.controller;


import com.tpe.dto.UserRequest;
import com.tpe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController("/register") // http://localhost:8080/register
public class UserController {

    private final UserService userService;

    //Note: register()********************************************************
   @PostMapping // http://localhost:8080/register + POST
    public ResponseEntity<String> register(@RequestBody @Valid UserRequest userRequest){

      userService.saveUser(userRequest);

      String message = "User Registered Successfully";

       return new ResponseEntity<>(message, HttpStatus.CREATED);

   }


}
