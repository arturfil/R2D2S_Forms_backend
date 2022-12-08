package com.arturofilio.artus_forms.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.arturofilio.artus_forms.entities.PollEntity;
import com.arturofilio.artus_forms.interfaces.IPollResult;
import com.arturofilio.artus_forms.models.requests.PollCreationRequestModel;

public interface IPollService {
    public Page<PollEntity> getPolls(int page, int limit, String email);
    public String createPoll(PollCreationRequestModel model, String email);
    public PollEntity getPoll(String pollId);
    public void togglePollIsOpen(String pollId, String email);
    public void deletePoll(String pollId, String email);
    public List<IPollResult> getPollResults(String pollId, String email);
}