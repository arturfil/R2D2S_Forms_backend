package com.arturofilio.artus_forms.models.responses;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PollResultsWrapperRest {
    private List<PollResultRest> results;
    private String content;
    private long id;
}
