package com.java.csncl.nutritracker.Controller;

import com.baeldung.openapi.api.UserprofileApiDelegate;
import com.baeldung.openapi.model.UserProfile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApiControllerImpl implements UserprofileApiDelegate {

    @Override
    public ResponseEntity<Void> createUser(Integer userId) {
//        return UserprofileApiDelegate.super.createUser(userId);
        System.out.println("TESTING...");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserProfile> getUserById(Integer userId) {
//        return UserprofileApiDelegate.super.getUserById(userId);
        return ResponseEntity.of(Optional.of(new UserProfile(2, "ANBOB")));
    }
}
