package com.arturofilio.artus_forms.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.arturofilio.artus_forms.entities.PollReplyDetailEntity;
import com.arturofilio.artus_forms.entities.PollReplyEntity;
import com.arturofilio.artus_forms.models.requests.PollReplyRequestModel;
import com.arturofilio.artus_forms.repositories.IPollReplyRepository;
import com.arturofilio.artus_forms.repositories.IPollRepository;

@Service
public class PollReplyServiceImpl implements IPollReplyService {

    IPollReplyRepository pollReplyRepository;
    IPollRepository pollRepository;

    public  PollReplyServiceImpl(IPollReplyRepository pollReplyRepository, IPollRepository pollRepository) {
        this.pollReplyRepository = pollReplyRepository;
        this.pollRepository = pollRepository;
    }

    @Override
    public void createPollReply(PollReplyRequestModel model) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setAmbiguityIgnored(true);
        PollReplyEntity pollReply = mapper.map(model, PollReplyEntity.class);
        pollReply.setPoll(pollRepository.findById(model.getPoll()));
        for (PollReplyDetailEntity pollReplyDetailEntity: pollReply.getPollReplies()) {
            pollReplyDetailEntity.setPollReply(pollReply);
        }
        pollReplyRepository.save(pollReply);
    }
    
}
