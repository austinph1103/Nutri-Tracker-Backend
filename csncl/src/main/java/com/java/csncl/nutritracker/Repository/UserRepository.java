package com.java.csncl.nutritracker.Repository;

import com.java.csncl.nutritracker.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {
    void createUser(int userId);
}
