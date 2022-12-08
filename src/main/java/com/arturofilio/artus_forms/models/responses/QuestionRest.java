package com.arturofilio.artus_forms.models.responses;

import java.util.List;
import com.arturofilio.artus_forms.enums.QuestionType;

import lombok.Data;

@Data
public class QuestionRest {
    private long id;
    private String content;
    private int questionOrder;
    private QuestionType type;
    private List<AnswerRest> answers;
}
 