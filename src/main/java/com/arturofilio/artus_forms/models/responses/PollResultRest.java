package com.arturofilio.artus_forms.models.responses;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PollResultRest {
    private String question;
    private List<ResultDetailRest> details;
}
