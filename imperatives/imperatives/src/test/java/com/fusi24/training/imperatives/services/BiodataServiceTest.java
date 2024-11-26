package com.fusi24.training.imperatives.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fusi24.training.imperatives.model.Biodata;
import com.fusi24.training.imperatives.repository.BiodataRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BiodataServiceTest {
	@Mock
	private BiodataRepository biodataRepository;

	@InjectMocks
	private BiodataServices biodataServices;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testInsertRandomBiodata() {
		// Arrange
		Biodata mockBiodata = new Biodata();
		mockBiodata.setId(1L);
		mockBiodata.setFirstName("TestName");
		when(biodataRepository.save(any(Biodata.class))).thenReturn(mockBiodata);

		// Act
		Biodata savedBiodata = biodataServices.insertRandomBiodata();

		// Assert
		assertNotNull(savedBiodata);
		assertEquals("TestName", savedBiodata.getFirstName());
		verify(biodataRepository, times(1)).save(any(Biodata.class));
	}

	@Test
	void testGetAllBiodataWithDelay() throws InterruptedException {
		// Arrange
		List<Biodata> mockBiodataList = new ArrayList<>();
		Biodata mockBiodata = new Biodata();
		mockBiodata.setId(1L);
		mockBiodata.setFirstName("TestName");
		mockBiodataList.add(mockBiodata);
		when(biodataRepository.findAll()).thenReturn(mockBiodataList);

		// Act
		List<Biodata> allBiodata = biodataServices.getAllBiodataWithDelay();

		// Assert
		assertNotNull(allBiodata);
		assertEquals(1, allBiodata.size());
		assertEquals("TestName", allBiodata.get(0).getFirstName());
		verify(biodataRepository, times(1)).findAll();
	}
}
