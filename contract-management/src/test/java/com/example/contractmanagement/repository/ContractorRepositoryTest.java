package com.example.contractmanagement.repository;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.example.contractmanagement.model.Contractor;
@AutoConfigureTestDatabase(replace=Replace.NONE)
@DataJpaTest
public class ContractorRepositoryTest {
	
	@Autowired
	private ContractorRepository adminrepo;
	
	@Test
	@Rollback(false)
	void createAdmin() {
		Contractor admin = new Contractor();
		admin.setId(1);
		admin.setName("admin");
		admin.setPassword("password");
		adminrepo.save(admin);
		
		Contractor savedAdmin = adminrepo.save(admin);
		
		assertNotNull(savedAdmin);
	}
}
