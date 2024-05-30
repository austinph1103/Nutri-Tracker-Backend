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
    public static int userCounts;
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
                String fullname = rs.getString("fullname");
                String dob = rs.getString("dob");
                String sex = (String) rs.getObject("sex");
                String email = rs.getString("emails");
                Double weight = rs.getDouble("weight");
                Double height = rs.getDouble("height");
                UserProfile userProfile =
                        new UserProfile(fullname, dob, sex, email, weight, height);

                userProfiles.add(userProfile);
            }
            return ResponseEntity.of(Optional.of(userProfiles.build()));
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Void> createUser(UserProfile userProfile) {
        Optional<String> queryString = sqlFileReader.getSqlQuery("CreateUser");
        if (queryString.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        try (PreparedStatement pStmt = dbConnection.prepareStatement(queryString.get())) {
            logger.log(Level.INFO, "PreparedStatement created successfully.");
            pStmt.setString(1, userProfile.getName());
            pStmt.setString(2, userProfile.getDob());
            pStmt.setString(3, String.valueOf(userProfile.getSex()));
            pStmt.setString(4, String.valueOf(userProfile.getEmails()));
            pStmt.setDouble(5, userProfile.getWeight());
            pStmt.setDouble(6, userProfile.getHeight());
            pStmt.executeUpdate();
            userCounts++;
            System.out.println("User created successfully !!");
            return ResponseEntity.status(HttpStatus.CREATED).build();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<UserProfile> getUserById(Integer userId) {
        Optional<String> queryString = sqlFileReader.getSqlQuery("GetUserByID");
        if (queryString.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        try (PreparedStatement pStmt = dbConnection.prepareStatement(queryString.get())) {
            pStmt.setInt(1, userId);
            ResultSet rs = pStmt.executeQuery();
            if (rs.next()) {
                String fullname = rs.getString("fullname");
                String dob = rs.getString("dob");
                String sex = (String) rs.getObject("sex");
                String email = rs.getString("emails");
                Double weight = rs.getDouble("weight");
                Double height = rs.getDouble("height");
                UserProfile userProfile = new UserProfile(fullname, dob, sex, email, weight, height);

                return ResponseEntity.of(Optional.of(userProfile));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<UserProfile> updateUserById(Integer userId, UserProfile userProfile) {
        Optional<String> queryString = sqlFileReader.getSqlQuery("UpdateUserByID");
        if (queryString.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        try (PreparedStatement pStmt = dbConnection.prepareStatement(queryString.get())) {
            pStmt.setString(1, userProfile.getName());
            pStmt.setString(2, userProfile.getDob());
            pStmt.setObject(3, userProfile.getSex());
            pStmt.setString(4, userProfile.getEmails());
            pStmt.setDouble(5, userProfile.getWeight());
            pStmt.setDouble(6, userProfile.getHeight());
            pStmt.setInt(7, userId);

            int rowsAffected = pStmt.executeUpdate();
            if (rowsAffected > 0) {
                return ResponseEntity.ok(userProfile);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Void> deleteUserById(Integer userId) {
        Optional<String> queryString = sqlFileReader.getSqlQuery("DeleteUserByID");
        if (queryString.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        try (PreparedStatement pStmt = dbConnection.prepareStatement(queryString.get())) {
            pStmt.setInt(1, userId);
            int rowsAffected = pStmt.executeUpdate();
            if (rowsAffected > 0) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
