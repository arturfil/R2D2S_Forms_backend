package com.arturofilio.artus_forms.services;

import com.arturofilio.artus_forms.models.requests.PollReplyRequestModel;

public interface IPollReplyService {
    public void createPollReply(PollReplyRequestModel model);
}
