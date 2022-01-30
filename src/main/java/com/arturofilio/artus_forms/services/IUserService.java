package com.arturofilio.artus_forms.services;

import com.arturofilio.artus_forms.entities.UserEntity;
import com.arturofilio.artus_forms.models.requests.UserRegisterRequestModel;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    public UserDetails loadUserByUsername(String email);
    public UserEntity createUser(UserRegisterRequestModel user);
}
