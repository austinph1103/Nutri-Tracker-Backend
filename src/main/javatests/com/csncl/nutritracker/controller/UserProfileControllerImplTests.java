package com.csncl.nutritracker.controller;

import com.csncl.nutritracker.model.UserProfile;
import com.csncl.nutritracker.testhelpers.TestHelpers;
import com.csncl.nutritracker.tools.SqlFileReader;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserProfileControllerImplTests {

    @Test
    public void createUser_returnsSuccessfulResponse() throws Exception {
        UserProfile userProfile = new UserProfile();

        SqlFileReader mockSqlFileReader = mock(SqlFileReader.class);
        when(mockSqlFileReader.getSqlQuery(any())).thenReturn(Optional.of("stuff"));

        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        Connection mockDbConnection = mock(Connection.class);
        when(mockDbConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        UserProfileControllerImpl userProfileController = new UserProfileControllerImpl(mockSqlFileReader, mockDbConnection);

        ResponseEntity<Void> actual = userProfileController.createUser(userProfile);

        verify(mockSqlFileReader).getSqlQuery(any());
        verify(mockDbConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).setString(eq(1), eq(userProfile.getName()));
        verify(mockPreparedStatement).setString(eq(2), eq(userProfile.getDob()));
        verify(mockPreparedStatement).setString(eq(3), eq(String.valueOf(userProfile.getSex())));
        verify(mockPreparedStatement).setString(eq(4), eq(String.join(",", userProfile.getEmails())));
        verify(mockPreparedStatement).setDouble(eq(5), eq(userProfile.getWeight()));
        verify(mockPreparedStatement).setDouble(eq(6), eq(userProfile.getHeight()));
        verify(mockPreparedStatement).executeUpdate();

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }


    @Test
    public void getUsers_returnsSuccessfulResponse() throws Exception {
        ImmutableList.Builder<Triple<String, String, String>> columnMapping = ImmutableList.builder();
        columnMapping.add(Triple.of("userid", "int", "123"));
        columnMapping.add(Triple.of("fname", "string", "John"));
        columnMapping.add(Triple.of("lname", "string", "Doe"));
        columnMapping.add(Triple.of("height", "double", "165"));
        columnMapping.add(Triple.of("weight", "double", "77"));
        Connection mockDbConnection = TestHelpers.mockSimpleGetOnlyDbConnection(columnMapping.build());
        SqlFileReader mockSqlFileReader = mock(SqlFileReader.class);
        when(mockSqlFileReader.getSqlQuery(any())).thenReturn(Optional.of("some query"));
        UserProfileControllerImpl userProfileController =
                new UserProfileControllerImpl(mockSqlFileReader, mockDbConnection);

        ResponseEntity<List<UserProfile>> actual = userProfileController.getUsers();

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).hasSize(1);
        UserProfile userProfile = actual.getBody().get(0);
//        assertThat(userProfile.getUserId()).isEqualTo(123);
        assertThat(userProfile.getName()).isEqualTo("John Doe");
        assertThat(userProfile.getHeight()).isEqualTo(165);
        assertThat(userProfile.getWeight()).isEqualTo(77);
    }

    @Test
    public void getUsers_invalidSqlPath_returnsInternalServerError() throws Exception {
        Connection mockDbConnection = TestHelpers.mockSimpleGetOnlyDbConnection(ImmutableList.of());
        SqlFileReader mockSqlFileReader = mock(SqlFileReader.class);
        when(mockSqlFileReader.getSqlQuery(any())).thenReturn(Optional.empty());
        UserProfileControllerImpl userProfileController =
                new UserProfileControllerImpl(mockSqlFileReader, mockDbConnection);

        ResponseEntity<List<UserProfile>> actual = userProfileController.getUsers();

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void getUsers_otherErrors_returnsInternalServerError() throws Exception {
        Connection mockDbConnection = TestHelpers.mockSimpleGetOnlyDbConnection(ImmutableList.of());
        SqlFileReader mockSqlFileReader = mock(SqlFileReader.class);
        when(mockSqlFileReader.getSqlQuery(any())).thenReturn(Optional.of("some query"));
        when(mockDbConnection.createStatement()).thenThrow(new SQLException("some random error"));
        UserProfileControllerImpl userProfileController =
                new UserProfileControllerImpl(mockSqlFileReader, mockDbConnection);

        ResponseEntity<List<UserProfile>> actual = userProfileController.getUsers();

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
