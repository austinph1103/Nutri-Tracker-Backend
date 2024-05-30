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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserProfileControllerImplTests {

    @Test
    public void createUser_returnsSuccessfulResponse() throws Exception {
        UserProfile userProfile = new UserProfile();
        userProfile.setName("John");
        userProfile.setDob("2000-12-09");
        userProfile.setSex("male");
        userProfile.setEmails("john.doe@example.com");
        userProfile.setWeight(77.0);
        userProfile.setHeight(165.0);

        Connection mockDbConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        when(mockDbConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);  // Simulate successful insert

        SqlFileReader mockSqlFileReader = mock(SqlFileReader.class);
        when(mockSqlFileReader.getSqlQuery(any())).thenReturn(Optional.of("INSERT INTO users ..."));

        UserProfileControllerImpl userProfileController =
                new UserProfileControllerImpl(mockSqlFileReader, mockDbConnection);
        ResponseEntity<Void> actual = userProfileController.createUser(userProfile);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
    
    @Test
    public void getUsers_returnsSuccessfulResponse() throws Exception {
        ImmutableList.Builder<Triple<String, String, String>> columnMapping = ImmutableList.builder();
//        columnMapping.add(Triple.of("userid", "int", "123"));
        columnMapping.add(Triple.of("fullname", "string", "John"));
        columnMapping.add(Triple.of("dob", "string", "2000-12-09"));
        columnMapping.add(Triple.of("sex", "string", "female"));
        columnMapping.add(Triple.of("emails", "string", "joe@gmail.com"));
        columnMapping.add(Triple.of("height", "double", "77.0"));
        columnMapping.add(Triple.of("weight", "double", "165.0"));

        Connection mockDbConnection = TestHelpers.mockSimpleGetOnlyDbConnection(columnMapping.build());
        SqlFileReader mockSqlFileReader = mock(SqlFileReader.class);
        when(mockSqlFileReader.getSqlQuery(any())).thenReturn(Optional.of("some query"));
        UserProfileControllerImpl userProfileController =
                new UserProfileControllerImpl(mockSqlFileReader, mockDbConnection);

        ResponseEntity<List<UserProfile>> actual = userProfileController.getUsers();

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).hasSize(1);
        UserProfile userProfile = actual.getBody().get(0);
//        assertThat(userProfile.get()).isEqualTo(123);
        assertThat(userProfile.getName()).isEqualTo("John");
        assertThat(userProfile.getDob()).isEqualTo("2000-12-09");
        assertThat(userProfile.getSex()).isEqualTo("female");
        assertThat(userProfile.getEmails()).isEqualTo("joe@gmail.com");
        assertThat(userProfile.getWeight()).isEqualTo(77.0);
        assertThat(userProfile.getHeight()).isEqualTo(165.0);
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
