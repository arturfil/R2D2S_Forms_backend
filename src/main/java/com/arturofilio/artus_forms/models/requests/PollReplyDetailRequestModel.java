package com.arturofilio.artus_forms.models.requests;

import lombok.Data;

@Data
public class PollReplyDetailRequestModel {
    private long questionId;
    private long answerId;
}
