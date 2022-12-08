package com.arturofilio.artus_forms.controllers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.arturofilio.artus_forms.entities.PollEntity;
import com.arturofilio.artus_forms.interfaces.IPollResult;
import com.arturofilio.artus_forms.models.requests.PollCreationRequestModel;
import com.arturofilio.artus_forms.models.responses.CreatedPollRest;
import com.arturofilio.artus_forms.models.responses.PaginatedPollRest;
import com.arturofilio.artus_forms.models.responses.PollRest;
import com.arturofilio.artus_forms.models.responses.PollResultRest;
import com.arturofilio.artus_forms.models.responses.PollResultsWrapperRest;
import com.arturofilio.artus_forms.services.IPollService;
import com.arturofilio.artus_forms.utils.transformer.PollResultTransformer;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/polls")
public class PollController {

    @Autowired
    IPollService pollService;

    @GetMapping(path = "{id}/questions")
    public PollRest getPollWithQuestions(@PathVariable String id) {
        PollEntity poll = pollService.getPoll(id);
        ModelMapper mapper = new ModelMapper();
        return mapper.map(poll, PollRest.class);
    }

    @GetMapping
    public PaginatedPollRest getPoll(
        @RequestParam(value="page", defaultValue = "0") int page, 
        @RequestParam(value="limit", defaultValue = "10") int limit,
        Authentication authentication
    ) {
        Page<PollEntity> paginatedPolls = pollService.getPolls(page, limit, authentication.getPrincipal().toString());
        ModelMapper mapper = new ModelMapper();
        mapper.typeMap(PollEntity.class, PollRest.class).addMappings(m -> m.skip(PollRest::setQuestions));
        PaginatedPollRest paginatedPollRest = new PaginatedPollRest();
        paginatedPollRest.setPolls(
            paginatedPolls.getContent().stream().map(p -> mapper.map(p, PollRest.class)).collect(Collectors.toList())
        );
        paginatedPollRest.setTotalPages(paginatedPolls.getTotalPages());
        paginatedPollRest.setTotalRecords(paginatedPolls.getTotalElements());
        paginatedPollRest.setCurrentPageRecords(paginatedPolls.getNumberOfElements());
        paginatedPollRest.setCurrentPage(paginatedPolls.getPageable().getPageNumber() + 1);

        return paginatedPollRest;
    }

    @GetMapping(path = "{id}/results")
    public PollResultsWrapperRest getPollResults(@PathVariable String id, Authentication authentication) {
        List<IPollResult> results = pollService.getPollResults(id, authentication.getPrincipal().toString());
        PollEntity poll = pollService.getPoll(id);
        PollResultTransformer transformer = new PollResultTransformer();
        return new PollResultsWrapperRest(transformer.transformData(results), poll.getContent(), poll.getId());
    }

    @PostMapping
    public CreatedPollRest createPoll(@RequestBody @Valid PollCreationRequestModel model,
            Authentication authentication) {
        String pollId = pollService.createPoll(model, authentication.getPrincipal().toString());
        return new CreatedPollRest(pollId);
    }

    @PatchMapping(path="/{id}")
    public void togglePollIsOpen(@PathVariable String id, Authentication authentication) {
        pollService.togglePollIsOpen(id, authentication.getPrincipal().toString());
    }

    @DeleteMapping(path="/{id}")
    public void deletePoll(@PathVariable String id, Authentication authentication) {
        pollService.deletePoll(id, authentication.getPrincipal().toString());        
    }

}
