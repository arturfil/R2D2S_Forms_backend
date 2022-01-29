package com.arturofilio.artus_forms.controllers;

import javax.validation.Valid;

import com.arturofilio.artus_forms.entities.UserEntity;
import com.arturofilio.artus_forms.models.requests.UserRegisterRequestModel;

import org.apache.catalina.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @PostMapping()
    public String createUser(@RequestBody @Valid UserRegisterRequestModel userModel) {
        return "User Created";
    }
}
