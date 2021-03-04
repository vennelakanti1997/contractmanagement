package com.example.contractmanagement.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.contractmanagement.util.DateUtil;

class RequirementTest {
	
	Requirement requirement =new Requirement();
	private Validator validator;
	@Test
	void testId() {
		requirement.setId(1);
		assertEquals(requirement.getId(), 1);
	}
	
	@Test
	void testDescription() {
		requirement.setDescription("Some Text");
		assertEquals(requirement.getDescription(), "Some Text");
	}
	
	@Test
	void testDeliveryDate() {
		requirement.setDeliveryDate(DateUtil.convertToDate("2022-11-11"));
		assertEquals(requirement.getDeliveryDate(), DateUtil.convertToDate("2022-11-11"));
	}
	
	@Test
	void testTypes() {
		Types type =new Types();
		requirement.setType(type);
		assertEquals(requirement.getType(),type);
	}
	
	@Test 
	void testToString() {
		requirement.setId(1);
		requirement.setDescription("Some Text");
		requirement.setDeliveryDate(DateUtil.convertToDate("2022-11-11"));
		Types type =new Types();
		type.setType("Type1");
		requirement.setType(type);
		String toString = "Requirements [id=1, description=Some Text, deliveryDate="+(DateUtil.convertToDate("2022-11-11"))+", type=Type1]";
		assertEquals(requirement.toString(), toString);
	}
	
	@BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
	@Test
	void testDescriptionLength() {
		String ctype="";
		for (int i = 0; i<=600; i++)
			ctype = ctype+"a";
		requirement.setDescription(ctype);
		
		Set<ConstraintViolation<Requirement>> violations = validator.validate(requirement);
		assertFalse(violations.isEmpty());
	}
	
}
