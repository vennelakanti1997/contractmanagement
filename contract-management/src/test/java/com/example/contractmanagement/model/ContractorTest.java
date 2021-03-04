package com.example.contractmanagement.model;
/*
 * author : Madhurya
 * */
import static org.junit.jupiter.api.Assertions.*;


import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ContractorTest {

	Contractor contractor = new Contractor();
	private Validator validator;
	Contractor contractor1 = new Contractor("admin", "admin123");
	
	@Test
	void testTwoArgs() {
		assertEquals(contractor1.getName(), new Contractor("admin", "admin123").getName());
		assertEquals(contractor1.getPassword(), new Contractor("admin", "admin123").getPassword());
	}
	
	@Test
	void testId() {
		contractor.setId(1);
		assertEquals(contractor.getId(), 1);
		
	}

	@Test
	void testName() {
		contractor.setName("contractor");
		assertEquals(contractor.getName(), "contractor");
	}

	@Test
	void testPassword() {
		contractor.setPassword("contractor");
		assertEquals(contractor.getPassword(),"contractor");
	}

	
	@Test
	void testToString() {
		contractor.setId(1);
		contractor.setName("contractor");
		contractor.setPassword("contractor");
		String tostring="Contractor(id=1, name=contractor, password=contractor)";
		assertEquals(contractor.toString(), tostring);
	}
	
	@BeforeEach
    public void anothersetUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
	
	@Test
	void testValidations() {
		String ctype="";
		for (int i = 0; i<=600; i++)
			ctype = ctype+"a";
		contractor.setName(ctype);
		Set<ConstraintViolation<Contractor>> violations = validator.validate(contractor);
		assertFalse(violations.isEmpty());
	} 
	
	@Test
	void anothertestValidations() {
		String ctype="";
		for (int i = 0; i<=4; i++)
			ctype = ctype+"a";
		contractor.setPassword(ctype);
		Set<ConstraintViolation<Contractor>> violations = validator.validate(contractor);
		assertFalse(violations.isEmpty());
	} 
	
	@Test
	void antestValidations() {
		String ctype="";
		for (int i = 0; i<=60; i++)
			ctype = ctype+"a";
		contractor.setPassword(ctype);
		Set<ConstraintViolation<Contractor>> violations = validator.validate(contractor);
		assertFalse(violations.isEmpty());
	} 

}
