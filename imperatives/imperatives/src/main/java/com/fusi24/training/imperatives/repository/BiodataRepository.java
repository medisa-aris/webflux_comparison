package com.fusi24.training.imperatives.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.fusi24.training.imperatives.model.Biodata;

public interface BiodataRepository extends JpaRepository<Biodata, Long> {
	
}