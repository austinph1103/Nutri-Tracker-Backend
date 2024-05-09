package com.java.csncl.nutritracker.Service;

import com.baeldung.openapi.api.UserApiDelegate;
import com.baeldung.openapi.model.UserProfile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UsersServices implements UserApiDelegate {
    @Override
    public ResponseEntity<Void> createUser(UserProfile userProfile) {
        System.out.println("TESTING1...");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserProfile> getUserById(Integer userId) {
//        return UserprofileApiDelegate.super.getUserById(userId);
        return ResponseEntity.of(Optional.of(new UserProfile(2, "ANBOB")));
    }

}
