package com.example.contractmanagement.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;



class RequirementDetailsTest {

	RequirementDetails requirement =new RequirementDetails();
	
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
		requirement.setDeliveryDate("2022-11-11");
		assertEquals(requirement.getDeliveryDate(), "2022-11-11");
	}
	
	@Test
	void testType() {
		requirement.setType("Some Text");
		assertEquals(requirement.getType(), "Some Text");
	}
	
	

}


