package com.csncl.nutritracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
//        UserProfile up = new UserProfile("ANBob", "13-09-2003",
//                "male", "an@gmail.com", 175.0, 70.0);
//        UserProfile up2 = new UserProfile("ANN", "13-09-2003",
//                "male", "bob@yahoo.com", 180.0, 75.0);
//        SqlFileReader SqlFileReader = new SqlFileReader();
//
//        UserProfileControllerImpl upControl = new UserProfileControllerImpl(SqlFileReader);
//
////        upControl.createUser(up);
//        ResponseEntity<List<UserProfile>> userList = upControl.getUsers();
////        System.out.println(UserProfileControllerImpl.userCounts);
////        System.out.println(userList);
//        System.out.println(upControl.getUserById(4));
//        upControl.deleteUserById(11);
        SpringApplication.run(Application.class, args);
    }
}