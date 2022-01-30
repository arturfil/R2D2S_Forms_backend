package com.arturofilio.artus_forms.repositories;

import com.arturofilio.artus_forms.entities.UserEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends CrudRepository<UserEntity, Long> {
    public UserEntity findByEmail(String email);    
}
