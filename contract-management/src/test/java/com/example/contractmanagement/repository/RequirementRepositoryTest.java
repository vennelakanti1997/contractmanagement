package com.example.contractmanagement.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import com.example.contractmanagement.model.Requirement;
import com.example.contractmanagement.model.Types;
import com.example.contractmanagement.util.DateUtil;

@DataJpaTest
@AutoConfigureTestDatabase(replace= Replace.NONE)
class RequirementRepositoryTest {
	
	@Autowired
	private RequirementRepository repo;
	
	private Requirement getRequirement() {
		Requirement requirement =new Requirement();
		requirement.setDescription("Requirement Description");
		requirement.setDeliveryDate(DateUtil.convertToDate("2022-11-11"));
		Types type =new Types();
		type.setType("Type0");
		requirement.setType(type);
		return requirement;
	}
	
	@Test
	void testCreateRequirement() {
		Requirement requirement=getRequirement();
		Requirement savedRequirement = repo.save(requirement);
		assertNotNull(savedRequirement);
	}
	
	
	@Test
	void testViewRequirementById() {
		Requirement requirement=getRequirement();
		Requirement savedRequirement = repo.save(requirement);
		Requirement retrievedRequirement = repo.findById(savedRequirement.getId()).orElse(null);
		assertThat(retrievedRequirement).isEqualTo(savedRequirement);
	}
	
	@Test
	void testViewRequirementsByType() {
		Requirement requirement=getRequirement();
		Requirement savedRequirement = repo.save(requirement);
		List<Requirement> retrievedRequirements = repo.findAllByType(savedRequirement.getType());
		assertThat(retrievedRequirements).contains(savedRequirement);
	}
	
	@Test
	void testViewAllRequirements() {
		Requirement requirement=getRequirement();
		Requirement savedRequirement = repo.save(requirement);
		List<Requirement> retrievedRequirements = repo.findAll();
		assertThat(retrievedRequirements).contains(savedRequirement);
	}
	
	@Test
	void testEditRequirement(){
		Requirement requirement=getRequirement();
		repo.save(requirement);
		Requirement requirement0=new Requirement();
		requirement0 = requirement;
		requirement0.setDescription("Updated Description");
		Requirement updatedRequirement=repo.save(requirement0);
		Requirement retrievedRequirement = repo.findById(requirement.getId()).orElse(null);
		assertThat(retrievedRequirement).isEqualTo(updatedRequirement);
		
	}

	@Test
	void testDeleteRequirement() {
		Requirement requirement =getRequirement();
		repo.save(requirement);
		boolean existsBeforeDel =repo.findById(requirement.getId()).isPresent();
		repo.delete(requirement);
		boolean existAfterDel =repo.findById(requirement.getId()).isPresent();
		assertTrue(existsBeforeDel);
		assertFalse(existAfterDel);
	}

}


