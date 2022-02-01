package com.arturofilio.artus_forms.models.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.arturofilio.artus_forms.annotations.UniqueEmail;

import lombok.Data;

@Data // We use lombok to generate getters and setters
public class UserRegisterRequestModel {
    @NotEmpty
    private String name;
    
    @NotEmpty
    @Email
    @UniqueEmail
    private String email;

    @NotEmpty
    @Size(min = 8, max = 16)
    private String password;

}
