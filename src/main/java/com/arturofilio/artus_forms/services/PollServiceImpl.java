package com.arturofilio.artus_forms.services;

import java.util.UUID;

import org.modelmapper.ModelMapper;

import com.arturofilio.artus_forms.entities.AnswerEntity;
import com.arturofilio.artus_forms.entities.PollEntity;
import com.arturofilio.artus_forms.entities.QuestionEntity;
import com.arturofilio.artus_forms.entities.UserEntity;
import com.arturofilio.artus_forms.models.requests.PollCreationRequestModel;
import com.arturofilio.artus_forms.repositories.IPollRepository;
import com.arturofilio.artus_forms.repositories.IUserRepository;

public class PollServiceImpl implements IPollService {

    IPollRepository pollRepository;
    IUserRepository userRepository;

    public PollServiceImpl(IPollRepository pollRepository, IUserRepository userRepository) {
        this.pollRepository = pollRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String createPoll(PollCreationRequestModel model, String email) {
        UserEntity user = userRepository.findByEmail(email);
        ModelMapper mapper = new ModelMapper();
        PollEntity pollEntity = mapper.map(model, PollEntity.class);

        pollEntity.setUser(user);
        pollEntity.setPollId(UUID.randomUUID().toString());
        
        for(QuestionEntity question: pollEntity.getQuestions()) {
            question.setPoll(pollEntity);
            for(AnswerEntity answer: question.getAnswers()) {
                answer.setQuestion(question);
            }
        }
        pollRepository.save(pollEntity);
        return pollEntity.getPollId();
    }
    
}
