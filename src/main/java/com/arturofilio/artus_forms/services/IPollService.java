package com.arturofilio.artus_forms.services;

import com.arturofilio.artus_forms.entities.PollEntity;
import com.arturofilio.artus_forms.models.requests.PollCreationRequestModel;

public interface IPollService {
    public String createPoll(PollCreationRequestModel model, String email);
    public PollEntity getPoll(String pollId);
}