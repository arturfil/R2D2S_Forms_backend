package com.arturofilio.artus_forms.services;

import java.util.ArrayList;

import com.arturofilio.artus_forms.entities.UserEntity;
import com.arturofilio.artus_forms.models.requests.UserRegisterRequestModel;
import com.arturofilio.artus_forms.repositories.IUserRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    
    IUserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(IUserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }   

    @Override
    public UserEntity createUser(UserRegisterRequestModel user) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        userEntity.setPassword(bCryptPasswordEncoder.encode( user.getPassword()) );
        return userRepository.save(userEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) throw new UsernameNotFoundException(email);
        return new User(userEntity.getEmail(), userEntity.getPassword(), new ArrayList<>());
    }

    @Override
    public UserEntity getUser(String email) {
        return userRepository.findByEmail(email);
    }
    
}
