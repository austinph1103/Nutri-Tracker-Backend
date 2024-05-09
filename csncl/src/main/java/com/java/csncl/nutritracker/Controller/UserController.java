package com.java.csncl.nutritracker.Controller;

import com.baeldung.openapi.model.UserProfile;
import com.baeldung.openapi.api.UserApi;
import com.baeldung.openapi.api.UserApiDelegate;
import com.baeldung.openapi.model.UserProfile;
import com.java.csncl.nutritracker.Service.UsersServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController implements UserApi {
    public ResponseEntity<Void> createUser(UserProfile userProfile) {
        System.out.println("TESTING...");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
