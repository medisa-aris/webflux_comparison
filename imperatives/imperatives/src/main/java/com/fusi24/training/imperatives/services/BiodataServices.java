package com.fusi24.training.imperatives.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fusi24.training.imperatives.model.Biodata;
import com.fusi24.training.imperatives.repository.BiodataRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BiodataServices {
	
	@Autowired
	BiodataRepository biodataRepository;

	/**
     * Inserts a random Biodata entity into the database.
     *
     * @return The saved Biodata entity.
     */
    public Biodata insertRandomBiodata() {
        Random random = new Random();

        Biodata biodata = new Biodata();
        biodata.setFirstName("FirstName" + random.nextInt(1000));
        biodata.setLastName("LastName" + random.nextInt(1000));
        biodata.setEmail("user" + random.nextInt(1000) + "@example.com");
        biodata.setPhoneNumber(String.format("081%d", random.nextInt(10000000)));
        biodata.setAddress("Address" + random.nextInt(1000));
        biodata.setCity("City" + random.nextInt(100));
        biodata.setState("State" + random.nextInt(50));
        biodata.setCountry("Country" + random.nextInt(100));
        biodata.setPostalCode(String.valueOf(10000 + random.nextInt(90000)));
        biodata.setBirthDate("2000-01-" + (10 + random.nextInt(20))); // Random dates in January 2000
        biodata.setGender(random.nextBoolean() ? "Male" : "Female");
        biodata.setNationality("Nationality" + random.nextInt(100));
        biodata.setMaritalStatus(random.nextBoolean() ? "Single" : "Married");
        biodata.setOccupation("Occupation" + random.nextInt(50));
        biodata.setEducation("Education" + random.nextInt(100));
        biodata.setSkills("Skill" + random.nextInt(100) + ", Skill" + random.nextInt(100));
        biodata.setHobbies("Hobby" + random.nextInt(50) + ", Hobby" + random.nextInt(50));
        biodata.setExperience("Experience" + random.nextInt(10) + " years");
        biodata.setLinkedinProfile("https://linkedin.com/in/user" + random.nextInt(1000));
        biodata.setGithubProfile("https://github.com/user" + random.nextInt(1000));

        return biodataRepository.save(biodata);
    }
    
    /**
     * Retrieves all Biodata entities with a 1-minute delay.
     *
     * @return List of all Biodata entities.
     */
    public List<Biodata> getAllBiodataWithDelay() {
        try {
        	log.info("Task execution will start after TEN SECOND delay..");
            TimeUnit.SECONDS.sleep(10); // Introduces a 1-minute delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while sleeping", e);
        }
        
        return new ArrayList<>(biodataRepository.findAll());
    }
    
    /**
     * Retrieves all Biodata entities 
     *
     * @return List of all Biodata entities.
     */
    public List<Biodata> getAllBiodata() {       
        return new ArrayList<>(biodataRepository.findAll());
    }
}
