package com.arturofilio.artus_forms.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.arturofilio.artus_forms.annotations.UniqueEmail;
import com.arturofilio.artus_forms.entities.UserEntity;
import com.arturofilio.artus_forms.repositories.IUserRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    IUserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        UserEntity user = userRepository.findByEmail(value);
        return user == null ? true : false;
    }
    
}
