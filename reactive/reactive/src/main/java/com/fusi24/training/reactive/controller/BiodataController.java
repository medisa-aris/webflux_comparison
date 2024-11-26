package com.fusi24.training.reactive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusi24.training.reactive.model.Biodata;
import com.fusi24.training.reactive.services.BiodataServices;
import com.fusi24.training.reactive.services.BiodataStream;

import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class BiodataController {

    @Autowired
    BiodataServices biodataService;
    
    @Autowired
    BiodataStream biodataStream;
    
    @Autowired
    private ConnectionFactory connectionFactory;

    /**
     * Endpoint to insert a random Biodata.
     * Logs the current thread ID and memory usage in bytes (non-blocking).
     */
    @GetMapping("/v1/insert")
    public Mono<String> insertRandomBiodata() {
        // Get and log the current thread ID
        long threadId = Thread.currentThread().getId();
        log.info("Thread insertRandomBiodata Start: {}", threadId);

        // Log memory usage before execution in a non-blocking manner
        return Mono.defer(() -> {
            long memoryBefore = getMemoryUsage();
            // Insert random biodata reactively
            return biodataService.insertRandomBiodata()
                    .doOnTerminate(() -> {
                        // Log memory usage after execution
                        long memoryAfter = getMemoryUsage();
                        log.info("Thread insertRandomBiodata, Memory before {} and after {} bytes", memoryBefore, memoryAfter);
                        logMemoryUsage(memoryBefore, memoryAfter);
                    })
                    .then(Mono.just("Random Biodata inserted!")); // Return a Mono with a completion message
        });
    }

    /**
     * Endpoint to get all biodata.
     * Logs the current thread ID and memory usage in bytes, then simulates delay.
     */
    @GetMapping("/v1/getall")
    public Mono<String> getAllBiodata() {
        // Get and log the current thread ID
        long threadId = Thread.currentThread().getId();
        log.info("Thread getAllBiodata Start: {}", threadId);

        // Log memory usage before execution in a non-blocking manner
        return Mono.defer(() -> {
            long memoryBefore = getMemoryUsage();
            // Get all biodata reactively
            return biodataService.getAllBiodata()
                    .doOnTerminate(() -> {
                        // Log memory usage after execution
                        long memoryAfter = getMemoryUsage();
                        log.info("Thread getAllBiodata, Memory before {} and after {} bytes", memoryBefore, memoryAfter);
                        logMemoryUsage(memoryBefore, memoryAfter);
                    })
                    .then(Mono.just("All Biodata retrieved!")); // Return a Mono with a completion message
        });
    }
    
//    /**
//     * Endpoint to get all biodata without delay.
//     * Logs the current thread ID and memory usage in bytes.
//     * Returns all biodata as Flux in JSON format.
//     */
//    @GetMapping("/v1/getall")
//    public Flux<Biodata> getAllBiodata() {
//        // Get and log the current thread ID
//        long threadId = Thread.currentThread().getId();
//        log.info("Thread getAllBiodata Start: {}", threadId);
//
//        // Log memory usage before execution in a non-blocking manner
//        long memoryBefore = getMemoryUsage();
//        return biodataService.getAllBiodata()
//                .doOnTerminate(() -> {
//                    // Log memory usage after execution
//                    long memoryAfter = getMemoryUsage();
//                    log.info("Thread getAllBiodata, Memory before {} and after {} bytes", memoryBefore, memoryAfter);
//                    logMemoryUsage(memoryBefore, memoryAfter);
//                });
//    }
    
    /**
     * Endpoint to get all biodata.
     * Logs the current thread ID and memory usage in bytes, with stream.
     */
    @GetMapping(value = "/v1/getstream", produces = "text/event-stream")
    public Flux<Biodata> getStreamBiodata() {
    	return biodataService.getStreamBiodata();
    }
    
    /**
     * Endpoint to get Latest Biodata Stream.
     * Logs the current thread ID and memory usage in bytes, with stream.
     */
    @GetMapping(value = "/v1/getlatest", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Biodata> getLatestBiodata() {
    	// Get and log the current thread ID
        long threadId = Thread.currentThread().getId();
        log.info("Thread getLatestBiodata Start: {}", threadId);
        
        // Emit the latest row on first subscription
        biodataService.getLatestBiodata()
            .subscribe(biodataStream::emit); // Fetch and push latest row to the sink

        // Return the Flux from the sink for real-time updates
        return biodataStream.getFlux();
    }

    /**
     * Gets the current memory usage of the JVM.
     * @return Memory usage in bytes.
     */
    private long getMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();  // Suggests garbage collection to minimize memory fluctuations.
        return runtime.totalMemory() - runtime.freeMemory();
    }

    /**
     * Logs the difference in memory usage.
     */
    private void logMemoryUsage(long memoryBefore, long memoryAfter) {
        long memoryUsed = memoryAfter - memoryBefore;
        log.info("Memory used by thread: {} kb", memoryUsed / 1024);
    }

}
