package com.arturofilio.artus_forms.models.responses;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessage {
    private Date timestamp;
    private String message;
    private int status;
}
