package com.java.csncl.nutritracker.Controller;

import com.baeldung.openapi.api.UserprofileApi;
import com.baeldung.openapi.api.UserprofileApiDelegate;
import com.baeldung.openapi.model.UserProfile;
import com.java.csncl.nutritracker.Service.UsersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Service
public class ApiControllerImpl implements UserprofileApiDelegate {
    UsersServices usersServices;

    public ApiControllerImpl(UsersServices usersServices) {
        this.usersServices = usersServices;
    }
    @Override
    public ResponseEntity<Void> createUser(Integer userId) {
        usersServices.createUser(userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UserProfile> getUserById(Integer userId) {
//        return UserprofileApiDelegate.super.getUserById(userId);
        return ResponseEntity.of(Optional.of(new UserProfile(2, "ANBOB")));
    }
}
