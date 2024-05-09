package com.java.csncl.nutritracker.Model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userID;
    private String fullName;
    private Date dob;
    private String gender;
    private String email;
    private double height;
    private double weight;
}
