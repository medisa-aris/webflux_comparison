package com.fusi24.training.reactive.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.fusi24.training.reactive.model.Biodata;

import reactor.core.publisher.Mono;

@Repository
public interface BiodataRepository extends ReactiveCrudRepository<Biodata, Long> {

	@Query("SELECT * FROM biodata ORDER BY id DESC LIMIT 1")
    Mono<Biodata> findLatest();
}
