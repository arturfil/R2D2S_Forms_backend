package com.arturofilio.artus_forms.models.responses;

import java.util.List;

import org.aspectj.weaver.patterns.TypePatternQuestions.Question;

import lombok.Data;

@Data
public class PollRest {
    private long id;
    private String pollId;
    private String content;
    private boolean opened;

    private List<QuestionRest> questions;
}
