package com.arturofilio.artus_forms.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arturofilio.artus_forms.models.requests.PollReplyRequestModel;
import com.arturofilio.artus_forms.services.IPollReplyService;

@RestController
@RequestMapping("/api/polls/reply")
public class PollReplyController {
    @Autowired
    IPollReplyService pollReplyService;

    @PostMapping
    public void replyPoll(@RequestBody @Valid PollReplyRequestModel model) {
        pollReplyService.createPollReply(model);
    }
}
