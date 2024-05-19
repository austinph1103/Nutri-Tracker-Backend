package com.csncl.nutritracker.controller;

import com.csncl.nutritracker.UserProfileApiDelegate;
import com.csncl.nutritracker.model.UserProfile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileControllerImpl implements UserProfileApiDelegate {

    @Override
    public ResponseEntity<Void> createUser() {
        System.out.println("TESTING...");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserProfile> getUserById(Integer userId) {
        return ResponseEntity.of(Optional.of(new UserProfile(2, "ANBOB")));
    }

    @Override
    public ResponseEntity<UserProfile> updateUserById(Integer userId, UserProfile userProfile) {
        return UserProfileApiDelegate.super.updateUserById(userId, userProfile);
    }

    @Override
    public ResponseEntity<Void> deleteUserById(Integer userId) {
        return UserProfileApiDelegate.super.deleteUserById(userId);
    }
}

