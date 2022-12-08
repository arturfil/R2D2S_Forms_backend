package com.arturofilio.artus_forms.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDetailRest {
    private String answer;
    private long result;
}
