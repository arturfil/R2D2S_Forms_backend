package com.arturofilio.artus_forms.services;

import com.arturofilio.artus_forms.entities.UserEntity;
import com.arturofilio.artus_forms.models.requests.UserRegisterRequestModel;
import com.arturofilio.artus_forms.repositories.IUserRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    
    IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity createUser(UserRegisterRequestModel user) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        userEntity.setPassword(user.getPassword());
        return userRepository.save(userEntity);
    }
    
}
