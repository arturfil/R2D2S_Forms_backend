package com.arturofilio.artus_forms.repositories;

import org.springframework.data.repository.CrudRepository;

import com.arturofilio.artus_forms.entities.PollReplyEntity;

public interface IPollReplyRepository extends CrudRepository<PollReplyEntity, Long> {
    
}
