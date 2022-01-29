package com.arturofilio.artus_forms.models.responses;

import lombok.Data;

@Data
public class UserRest {
    private long id;
    private String name;
    private String email;
}
