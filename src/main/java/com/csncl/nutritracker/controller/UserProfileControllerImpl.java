package com.csncl.nutritracker.controller;

import com.csncl.nutritracker.UserProfileApiDelegate;
import com.csncl.nutritracker.model.UserProfile;
import com.csncl.nutritracker.tools.SqlFileReader;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import dbConnect.DBConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserProfileControllerImpl implements UserProfileApiDelegate {
    private static final Logger logger = Logger.getLogger(UserProfileControllerImpl.class.getName());

    private final SqlFileReader sqlFileReader;
    private final Connection dbConnection;

    @Inject
    @Autowired
    public UserProfileControllerImpl(SqlFileReader sqlFileReader) {
        this.sqlFileReader = sqlFileReader;
        this.dbConnection = DBConfig.getConnection();
    }

    @VisibleForTesting
    UserProfileControllerImpl(SqlFileReader sqlFileReader, Connection dbConnection) {
        this.sqlFileReader = sqlFileReader;
        this.dbConnection = dbConnection;
    }

    @Override
    public ResponseEntity<List<UserProfile>> getUsers() {
        ImmutableList.Builder<UserProfile> userProfiles = ImmutableList.builder();
        Optional<String> queryString = sqlFileReader.getSqlQuery("GetAllUserProfiles");
        if (queryString.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        try (Statement stmt = dbConnection.createStatement()) {
            ResultSet rs = stmt.executeQuery(queryString.get());
            while (rs.next()) {
                UserProfile userProfile =
                        new UserProfile(
                                rs.getInt("userid"), rs.getString("fname") + " " + rs.getString("lname"))
                                .height(rs.getDouble("height"))
                                .weight(rs.getDouble("weight"));
                userProfiles.add(userProfile);
            }
            return ResponseEntity.of(Optional.of(userProfiles.build()));
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Void> createUser() {
        Optional<String> queryString = sqlFileReader.getSqlQuery("CreateUser");


        if (dbConnection == null) {
            logger.log(Level.SEVERE, "Database connection is not initialized.");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        if (queryString.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        String query = "INSERT INTO users(fullname, dob, sex, emails, weight, height)VALUES(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pStmt = dbConnection.prepareStatement(query)) {
            UserProfile userProfile = new UserProfile(1, "AN");
//            pStmt.setString(1, userProfile.getName());
//            pStmt.setString(2, String.valueOf(userProfile.getDob()));
//            pStmt.setString(3, String.valueOf(userProfile.getSex()));
//            pStmt.setString(4, String.valueOf(userProfile.getEmails()));
//            pStmt.setDouble(5, userProfile.getWeight());
//            pStmt.setDouble(6, userProfile.getHeight());

//            pStmt.executeUpdate();

            return ResponseEntity.status(HttpStatus.CREATED).build();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
