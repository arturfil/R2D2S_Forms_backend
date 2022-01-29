package com.arturofilio.artus_forms.services;

import com.arturofilio.artus_forms.entities.UserEntity;
import com.arturofilio.artus_forms.models.requests.UserRegisterRequestModel;

public interface IUserService {
    public UserEntity createUser(UserRegisterRequestModel user);
}
