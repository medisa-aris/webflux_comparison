package com.fusi24.training.imperatives.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusi24.training.imperatives.services.BiodataServices;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class BiodataController {
	
	@Autowired
	BiodataServices biodataService;
	
	@Autowired
    HikariDataSource dataSource;
	
	/**
     * Endpoint to insert a random Biodata.
     * Logs the current thread ID and memory usage in bytes.
     */
    @GetMapping("/v1/insert")
    public String insertRandomBiodata() {
        // Get and log the current thread ID
        long threadId = Thread.currentThread().getId();
        String threadName = Thread.currentThread().getName();
        log.info("Thread insertRandomBiodata Start: {} ()", threadId, threadName);

        // Get and log memory usage in bytes before execution
        long memoryBefore = getMemoryUsage();
//        log.info("Memory before insertRandomBiodata: {} bytes", memoryBefore);

        // Insert random biodata
        biodataService.insertRandomBiodata();
        
        // Get and log memory usage in bytes after execution
        long memoryAfter = getMemoryUsage();
        log.info("Thread getAllBiodata {}, Memory before {} after {} bytes", threadId, memoryBefore, memoryAfter);
        logHikariCPStats();

        // Log memory used by the thread
        logMemoryUsage(memoryBefore, memoryAfter);

        return "Random Biodata inserted!";
    }

    /**
     * Endpoint to get all biodata.
     * Logs the current thread ID and memory usage in bytes, then simulates delay.
     */
    @GetMapping("/v1/getall")
    public String getAllBiodata() {
        // Get and log the current thread ID
        long threadId = Thread.currentThread().getId();
        String threadName = Thread.currentThread().getName();
        log.info("Thread getAllBiodata Start: {} ()", threadId, threadName);

        // Get and log memory usage in bytes before execution
        long memoryBefore = getMemoryUsage();
//        log.info("Memory before getAllBiodata: {} bytes", memoryBefore);

        // Get all biodata with delay
        biodataService.getAllBiodata();
        
        // Get and log memory usage in bytes after execution
        long memoryAfter = getMemoryUsage();
        log.info("Thread getAllBiodata {}, Memory before {} after {} bytes", threadId, memoryBefore, memoryAfter);
        logHikariCPStats();

        // Log memory used by the thread
        logMemoryUsage(memoryBefore, memoryAfter);

        return "All Biodata retrieved!";
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
        log.info("Memory used by thread: {} kb", memoryUsed /1024);
    }
    
    /**
     * Logs HikariCP connection pool stats.
     */
    private void logHikariCPStats() {
        HikariPoolMXBean poolMXBean = dataSource.getHikariPoolMXBean();
        log.info("HikariCP Pool Stats - Total: {}, Active: {}, Idle: {}, Waiting: {}",
                 poolMXBean.getTotalConnections(),
                 poolMXBean.getActiveConnections(),
                 poolMXBean.getIdleConnections(),
                 poolMXBean.getThreadsAwaitingConnection());
    }
}
