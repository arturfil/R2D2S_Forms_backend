package com.arturofilio.artus_forms.services;

import com.arturofilio.artus_forms.models.requests.PollCreationRequestModel;

public interface IPollService {
    public String createPoll(PollCreationRequestModel model, String email);
}