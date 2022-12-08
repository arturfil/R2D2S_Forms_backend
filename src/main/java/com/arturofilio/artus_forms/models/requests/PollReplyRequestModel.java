package com.arturofilio.artus_forms.models.requests;

import java.util.List;

import lombok.Data;

@Data
public class PollReplyRequestModel {
    private String user;
    private long poll;
    private List<PollReplyDetailRequestModel> pollReplies;
}
