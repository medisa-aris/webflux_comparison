package com.fusi24.training.reactive.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data // Lombok annotation for getter, setter, toString, equals, and hashCode
@Table("biodata") // Specify table name
public class Biodata {

    @Id
    private Long id; // Primary key, no need for @GeneratedValue as R2DBC handles it differently

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("email")
    private String email;

    @Column("phone_number")
    private String phoneNumber;

    @Column("address")
    private String address;

    @Column("city")
    private String city;

    @Column("state")
    private String state;

    @Column("country")
    private String country;

    @Column("postal_code")
    private String postalCode;

    @Column("birth_date")
    private String birthDate;

    @Column("gender")
    private String gender;

    @Column("nationality")
    private String nationality;

    @Column("marital_status")
    private String maritalStatus;

    @Column("occupation")
    private String occupation;

    @Column("education")
    private String education;

    @Column("skills")
    private String skills;

    @Column("hobbies")
    private String hobbies;

    @Column("experience")
    private String experience;

    @Column("linkedin_profile")
    private String linkedinProfile;

    @Column("github_profile")
    private String githubProfile;
}

