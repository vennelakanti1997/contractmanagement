package com.example.contractmanagement.model;
/*
 * author : Madhurya
 * */
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


import org.junit.jupiter.api.BeforeEach;

public class SupplierTest {
	private Validator validator;
	Supplier supplier = new Supplier();
	Types type = new Types();
	Contract contract1 = new Contract();
	Contract contract2 = new Contract();
	
	@Test
	public void testId() {
		supplier.setId(1);
		assertEquals(supplier.getId(), 1);
	}
	
	@Test
	public void testName() {
		supplier.setName("supplier");
		assertEquals(supplier.getName(), "supplier");
	}
	
	@Test
	public void testPassword() {
		supplier.setPassword("supplier");
		assertEquals(supplier.getPassword(), "supplier");
	}
	
	
	@Test
	public void testContactNumber() {
		supplier.setContactNumber("1983323");
		assertEquals(supplier.getContactNumber(), "1983323");
	}
	
	
	@Test
	public void testAddress() {
		supplier.setAddress("address");
		assertEquals(supplier.getAddress(), "address");
	}
	
	
	@Test
	public void testType() {
		type.setId(1);
		type.setType("type1");
		supplier.setType(type);
		assertEquals(supplier.getType(), type);
	}
	
	@Test
	public void testContract() {
		contract1.setId(1);
		contract1.setContractType("contract1");
		contract1.setContractDuration(1);
		contract1.setTermsAndConditions("tnc1");
		contract1.setStatus("status1");
		contract1.setAmenities("amenities1");
		
		contract2.setId(2);
		contract2.setContractType("contract2");
		contract2.setContractDuration(2);
		contract2.setTermsAndConditions("tnc2");
		contract2.setStatus("status2");
		contract2.setAmenities("amenities2");
		
		List<Contract> list = new ArrayList<Contract>();
		list.add(contract1);
		list.add(contract2);
		
		supplier.setContracts(list);
		assertEquals(supplier.getContracts(), list);
	}
	
	@Test
	public void testToString() {
		supplier.setId(1);
		supplier.setName("supplier");
		supplier.setPassword("supplier");
		supplier.setContactNumber("123");
		supplier.setAddress("address");
		type.setId(1);
		type.setType("type1");
		supplier.setType(type);
		assertTrue(supplier.toString().contains("supplier"));
	}
	
	@BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
		
	@Test
	void testPasswordValidations() {
		String ctype="";
		for (int i = 0; i<=60; i++)
			ctype = ctype+"a";
		supplier.setPassword(ctype);;
		Set<ConstraintViolation<Supplier>> violations = validator.validate(supplier);
		assertFalse(violations.isEmpty());	
		
	}
	
	@Test
	void anothertestPasswordValidations() {
		String ctype="";
		for (int i = 0; i<=8; i++)
			ctype = ctype+"a";
		supplier.setPassword(ctype);;
		Set<ConstraintViolation<Supplier>> violations = validator.validate(supplier);
		assertFalse(violations.isEmpty());	
		
	}
}
