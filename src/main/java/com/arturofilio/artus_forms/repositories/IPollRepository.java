package com.arturofilio.artus_forms.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.arturofilio.artus_forms.entities.PollEntity;

@Repository
public interface IPollRepository extends CrudRepository<PollEntity, Long> {
    
}
