package com.fusi24.training.imperatives.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data // Lombok annotation for getter, setter, toString, equals, and hashCode
@Table(name = "biodata") // Specify table name
public class Biodata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "city", length = 50)
    private String city;

    @Column(name = "state", length = 50)
    private String state;

    @Column(name = "country", length = 50)
    private String country;

    @Column(name = "postal_code", length = 10)
    private String postalCode;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "nationality", length = 50)
    private String nationality;

    @Column(name = "marital_status", length = 20)
    private String maritalStatus;

    @Column(name = "occupation", length = 100)
    private String occupation;

    @Column(name = "education", length = 100)
    private String education;

    @Column(name = "skills", length = 255)
    private String skills;

    @Column(name = "hobbies", length = 255)
    private String hobbies;

    @Column(name = "experience", length = 255)
    private String experience;

    @Column(name = "linkedin_profile", length = 255)
    private String linkedinProfile;

    @Column(name = "github_profile", length = 255)
    private String githubProfile;
}

