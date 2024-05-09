package com.java.csncl.nutritracker.Service;

import com.baeldung.openapi.api.UserprofileApiDelegate;
import com.java.csncl.nutritracker.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UsersServices implements UserprofileApiDelegate {
    private final UserRepository userRepository;

    public UsersServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public ResponseEntity<Void> createUser(Integer userId) {
        // Logic to create user
        System.out.println("CREATE USER...");
        // Assuming user creation is handled here
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
