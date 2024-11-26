package com.fusi24.training.reactive.services;

import java.time.Duration;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fusi24.training.reactive.model.Biodata;
import com.fusi24.training.reactive.repository.BiodataRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@Slf4j
public class BiodataServices {

	@Autowired
    BiodataStream biodataStream;
	
    @Autowired
    private BiodataRepository biodataRepository;

    /**
     * Inserts a random Biodata entity into the database.
     * Returns a Mono to be reactive.
     */
    public Mono<Biodata> insertRandomBiodata() {
        Random random = new Random();

        // Create and set random values for Biodata
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

        // Save biodata reactively and return it as a Mono
        return biodataRepository.save(biodata)
        		.doOnSuccess(biodataStream::emit);
    }

    /**
     * Retrieves all Biodata entities with a 1-minute delay.
     * Returns a Flux to be reactive.
     */
    public Flux<Biodata> getAllBiodataWithDelay() {
        log.info("Task execution will start after TEN SECOND delay..");

        // Use Mono.delay to create a non-blocking delay
        return Mono.delay(Duration.ofSeconds(10))
                .thenMany(biodataRepository.findAll()) // Get all biodata after delay
                .subscribeOn(Schedulers.boundedElastic()); // Run on a bounded thread pool for I/O tasks
    }
    
    /**
     * Retrieves all Biodata entities without any delay.
     * Returns a Flux to be reactive.
     */
    public Flux<Biodata> getAllBiodata() {
        log.info("Fetching all biodata without delay...");

        // Get all biodata reactively without delay
        return biodataRepository.findAll()
                .subscribeOn(Schedulers.boundedElastic()); // Run on a bounded thread pool for I/O tasks
    }
    
    /**
     * Retrieves all Biodata by Stream process.
     * Returns a Flux to be reactive.
     */
    public Flux<Biodata> getStreamBiodata() {
        log.info("Fetching biodata with Stream...");

        // Get all biodata reactively without delay
        return biodataRepository.findAll()
        	.doOnNext(biodata -> {
        		// Get and log the current thread ID
                long threadId = Thread.currentThread().getId();
        		log.info("Thread {} Fetched Biodata: {}",threadId, biodata.getId());
        		});
    }
    
    /**
     * Retrieves all Biodata by Stream process.
     * Returns a Flux to be reactive.
     */
    public Mono<Biodata> getLatestBiodata() {
        log.info("Fetching latest biodata...");

        // Get all biodata reactively without delay
        return biodataRepository.findLatest();
    }
}