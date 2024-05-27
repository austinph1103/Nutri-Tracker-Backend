package com.csncl.nutritracker.controller;

import com.csncl.nutritracker.tools.SqlFileReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private SqlFileReader sqlFileReader;

    @Mock
    private Connection dbConnection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private Logger logger;

    @InjectMocks
    private UserProfileControllerImpl userService; // Assuming the method is in UserService class

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser_Success() throws SQLException {
        String sqlQuery = "INSERT INTO users ..."; // Simplified for example
        when(sqlFileReader.getSqlQuery("CreateUser")).thenReturn(Optional.of(sqlQuery));
        when(dbConnection.prepareStatement(sqlQuery)).thenReturn(preparedStatement);

        ResponseEntity<Void> response = userService.createUser();

        verify(preparedStatement).setString(1, anyString());
        verify(preparedStatement).setString(2, anyString());
        verify(preparedStatement).setString(3, anyString());
        verify(preparedStatement).setString(4, anyString());
        verify(preparedStatement).setDouble(5, anyDouble());
        verify(preparedStatement).setDouble(6, anyDouble());
        verify(preparedStatement).executeUpdate();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testCreateUser_SqlQueryEmpty() {
        when(sqlFileReader.getSqlQuery("CreateUser")).thenReturn(Optional.empty());

        ResponseEntity<Void> response = userService.createUser();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testCreateUser_SqlException() throws SQLException {
        String sqlQuery = "INSERT INTO users ..."; // Simplified for example
        when(sqlFileReader.getSqlQuery("CreateUser")).thenReturn(Optional.of(sqlQuery));
        when(dbConnection.prepareStatement(sqlQuery)).thenThrow(new SQLException("Database error"));

        ResponseEntity<Void> response = userService.createUser();

        verify(logger).log(Level.SEVERE, "Database error");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}

