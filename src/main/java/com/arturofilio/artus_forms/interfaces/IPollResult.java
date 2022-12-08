package com.arturofilio.artus_forms.interfaces;

public interface IPollResult {
    int getQuestionOrder();
    long getQuestionId();
    String getQuestion();
    long getAnswerId();
    String getAnswer();
    long getResult();
}
