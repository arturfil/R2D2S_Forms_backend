package com.arturofilio.artus_forms;

import java.util.Random;

import com.arturofilio.artus_forms.models.requests.UserRegisterRequestModel;

public class TestUtil {
    public static UserRegisterRequestModel createValidUser() {
        var user = new UserRegisterRequestModel();
        user.setEmail(generateRandomString(8) + "@hotmail.com");
        user.setName(generateRandomString(8));
        user.setPassword(generateRandomString(8));
        return user;
    }

    public static String generateRandomString(int len) {
        String chars = "ABCDEFGHIJLMNOPQRSTUVWXYZabcdefghijlmnopqrstuvwxyz";
        Random randNumber = new Random();
        StringBuilder sbuilder = new StringBuilder(len);
        for (int i = 0; i < sbuilder.length(); i++) {
            sbuilder.append(chars.charAt(randNumber.nextInt(chars.length())));
        }
        return sbuilder.toString();
    }
}
