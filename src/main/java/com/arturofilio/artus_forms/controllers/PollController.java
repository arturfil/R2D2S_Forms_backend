package com.arturofilio.artus_forms.controllers;

import javax.validation.Valid;

import com.arturofilio.artus_forms.models.requests.PollCreationRequestModel;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/polls")
public class PollController {

    @PostMapping
    public String createPoll(@RequestBody @Valid PollCreationRequestModel pollCreationRequestModel,
            Authentication authentication) {
        return authentication.getPrincipal().toString();
    }
}
