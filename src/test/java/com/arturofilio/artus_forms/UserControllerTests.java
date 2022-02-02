package com.arturofilio.artus_forms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import com.arturofilio.artus_forms.entities.UserEntity;
import com.arturofilio.artus_forms.models.requests.UserLoginRequestModel;
import com.arturofilio.artus_forms.models.requests.UserRegisterRequestModel;
import com.arturofilio.artus_forms.models.responses.UserRest;
import com.arturofilio.artus_forms.models.responses.ValidationErrors;
import com.arturofilio.artus_forms.repositories.IUserRepository;
import com.arturofilio.artus_forms.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerTests {
    
    private static final String API_URL = "/api/users";
    private static final String API_LOGIN_URL = "/api/users/login";

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    UserService userService;

    @Autowired
    IUserRepository userRespository;

    @BeforeEach
    public void cleanUp() {
        userRespository.deleteAll();
    }

    @Test // no fields
    public void createUser_withoutFields_badRequest() {
        ResponseEntity<Object> response = register(new UserRegisterRequestModel(), Object.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test // no name provided
    public void createUser_withoutName_badRequest() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        user.setName(null);
        ResponseEntity<Object> response = register(user, Object.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test // no name error display
    public void createUser_withoutName_displaysErrors_ForName() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        user.setName(null);
        ResponseEntity<ValidationErrors> response = 
            register(user, ValidationErrors.class);
        Map<String, String> errors = response.getBody().getErrors();
        assertTrue(errors.containsKey("name"));
    }
    
    @Test // no email error display
    public void createUser_withouEmail_displaysErrors_ForEmail() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        user.setEmail(null);
        ResponseEntity<ValidationErrors> response = 
            register(user, ValidationErrors.class);
        Map<String, String> errors = response.getBody().getErrors();
        assertTrue(errors.containsKey("email"));
    }

    @Test // no password error display
    public void createUser_withoutPassword_displaysErrors_ForPassword() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        user.setPassword(null);
        ResponseEntity<ValidationErrors> response = 
            register(user, ValidationErrors.class);
        Map<String, String> errors = response.getBody().getErrors();
        assertTrue(errors.containsKey("password"));
    }

    @Test
    public void createUser_withoutPassword_badRequest() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        user.setPassword(null);
        ResponseEntity<Object> response = register(user, Object.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createUser_withoutEmail_badRequest() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        user.setEmail(null);
        ResponseEntity<Object> response = register(user, Object.class);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test // no fields display errors
    public void createUser_withoutFields_displaysErrors() {
        ResponseEntity<ValidationErrors> response = 
            register(new UserRegisterRequestModel(), ValidationErrors.class);
        Map<String, String> errors = response.getBody().getErrors();
        assertEquals(errors.size(), 3); // number of map count is equal of number of fields required == 3;
    }

    @Test
    public void createValidUser_returnOkStatus() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        ResponseEntity<UserRest> response = register(user, UserRest.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void createValidUser_saveUserToDB() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        ResponseEntity<UserRest> response = register(user, UserRest.class);
        UserEntity userDb = userRespository.findById(response.getBody().getId());
        assertNotNull(userDb);
    }

    @Test
    public void createValidUser_passwordIsHashed() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        ResponseEntity<UserRest> response = register(user, UserRest.class);
        UserEntity userDb = userRespository.findById(response.getBody().getId());
        assertNotEquals(user.getPassword(), userDb.getPassword()); // they equal, means password is not being hashed
    }

    @Test
    public void createValidUser_returnsError_emailAlreadyInUse() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        register(user, UserRest.class);
        ResponseEntity<UserRest> response2 = register(user, UserRest.class);
        assertEquals(response2.getStatusCode(), HttpStatus.BAD_REQUEST); // user duplicate
    }

    @Test // no email error display
    public void createValidUser_emailAlreadyInUser_returnsErrors() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        register(user, UserRest.class);
        ResponseEntity<ValidationErrors> response2 = register(user, ValidationErrors.class);
        Map<String, String> errors = response2.getBody().getErrors();
        assertTrue(errors.containsKey("email"));
    }
    
    @Test // user without token
    public void getUser_withoutToken_returnsErrors() {
        ResponseEntity<Object> response = getUser(null, new ParameterizedTypeReference<Object>(){});
        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test // get user with valid token
    public void getUser_withToken_returns200Status() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        userService.createUser(user);
        UserLoginRequestModel model = new UserLoginRequestModel();
        model.setEmail(user.getEmail());
        model.setPassword(user.getPassword());
        ResponseEntity<Map<String, String>> responseLogin = login(model, new ParameterizedTypeReference<Map<String, String>>(){});
        Map<String, String> body = responseLogin.getBody();
        String token = body.get("token");
        ResponseEntity<UserRest> response = getUser(token, new ParameterizedTypeReference<UserRest>(){});
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test // get user with valid token
    public void getUser_withToken_returnsUserRest() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        userService.createUser(user);
        UserLoginRequestModel model = new UserLoginRequestModel();
        model.setEmail(user.getEmail());
        model.setPassword(user.getPassword());
        ResponseEntity<Map<String, String>> responseLogin = login(model, new ParameterizedTypeReference<Map<String, String>>(){});
        Map<String, String> body = responseLogin.getBody();
        String token = body.get("token");
        ResponseEntity<UserRest> response = getUser(token, new ParameterizedTypeReference<UserRest>(){});
        assertEquals(user.getName(), response.getBody().getName());
    }

    public <T> ResponseEntity<T> register(UserRegisterRequestModel data, Class<T> responseType) {
        return testRestTemplate.postForEntity(API_URL, data, responseType);
    }

    public <T> ResponseEntity<T> login(UserLoginRequestModel data, ParameterizedTypeReference<T> responseType) {
        HttpEntity<UserLoginRequestModel> entity = new HttpEntity<UserLoginRequestModel>(data, new HttpHeaders());
        return testRestTemplate.exchange(API_LOGIN_URL, HttpMethod.POST, entity, responseType);
    }

    public <T> ResponseEntity<T> getUser(String token, ParameterizedTypeReference<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Object> entity = new HttpEntity<Object>(null, headers);
        return testRestTemplate.exchange(API_URL, HttpMethod.GET, entity, responseType);
    }
}
