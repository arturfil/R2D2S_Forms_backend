package com.arturofilio.artus_forms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.arturofilio.artus_forms.models.requests.UserLoginRequestModel;
import com.arturofilio.artus_forms.models.requests.UserRegisterRequestModel;
import com.arturofilio.artus_forms.repositories.IUserRepository;
import com.arturofilio.artus_forms.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import net.bytebuddy.agent.VirtualMachine.ForHotSpot.Connection.Response;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class LoginTests {

    private static final String API_LOGIN_URL = "/api/users/login";

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    UserService userService;

    @Autowired
    IUserRepository userRepository;

    // clean database before initiating tests
    @BeforeEach
    public void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    public void postLogin_withoutCredentials_returnsUnauthorized() {
        ResponseEntity<Object> response = login(null, Object.class);
        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    public void postLogin_withWrongCredentials_returnsUnauthorized() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        userService.createUser(user);
        UserLoginRequestModel model = new UserLoginRequestModel();
        model.setEmail("tecache@hotmail.com");
        model.setPassword("Password123");
        ResponseEntity<Object> response = login(null, Object.class);
        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    public void postLogin_withCorrectCredentials() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        userService.createUser(user);
        UserLoginRequestModel model = new UserLoginRequestModel();
        model.setEmail(user.getEmail());
        model.setPassword(user.getPassword());
        ResponseEntity<Object> response = login(model, Object.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void postLogin_withCorrectCredentials_returnsToken() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        userService.createUser(user);
        UserLoginRequestModel model = new UserLoginRequestModel();
        model.setEmail(user.getEmail());
        model.setPassword(user.getPassword());
        ResponseEntity<Object> response = login(model, Object.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    public <T> ResponseEntity<T> login(UserLoginRequestModel data, Class<T> responseType) {
        return testRestTemplate.postForEntity(API_LOGIN_URL, data, responseType);
    }
}









