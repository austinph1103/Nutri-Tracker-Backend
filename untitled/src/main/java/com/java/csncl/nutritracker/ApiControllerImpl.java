package com.java.csncl.nutritracker;

import com.baeldung.openapi.api.UserprofileApiDelegate;
import com.baeldung.openapi.model.UserProfile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Service
//@RestController
//@RequestMapping
public class  ApiControllerImpl implements UserprofileApiDelegate {

//    @Override
//    public ResponseEntity<Void> createUser(Integer userId) {
//        return UserprofileApiDelegate.super.createUser();
//        System.out.println("TESTING...");
//        return new ResponseEntity<>(HttpStatus.OK);
//    }


    @Override
    public ResponseEntity<Void> createUser() {
//        return UserprofileApiDelegate.super.createUser();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserProfile> getUserById(Integer userId) {
//        return UserprofileApiDelegate.super.getUserById(userId);
        return ResponseEntity.of(Optional.of(new UserProfile(2, "ANBOB")));
    }
}
