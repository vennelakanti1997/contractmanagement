package com.example.contractmanagement.model;
/*
 * author : Madhurya
 * */
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SupplierDetailsTest {
	SupplierDetails supplierDetails = new SupplierDetails();
	SupplierDetails supplierDetails1 = new SupplierDetails("supplier", "abcdef@gh", "9876543212", "Delhi", 1);
	
	@Test
	void allArgsTest() {
		assertTrue(supplierDetails1.toString().contains("supplier"));
	}
	
	@Test
	public void testName() {
		supplierDetails.setName("supplier");
		assertEquals(supplierDetails.getName(), "supplier");
	}
	
	@Test
	public void testPassword() {
		supplierDetails.setPassword("supplier");
		assertEquals(supplierDetails.getPassword(), "supplier");
	}
	
	
	@Test
	public void testContactNumber() {
		supplierDetails.setContactNumber("1983323");
		assertEquals(supplierDetails.getContactNumber(), "1983323");
	}
	
	
	@Test
	public void testAddress() {
		supplierDetails.setAddress("address");
		assertEquals(supplierDetails.getAddress(), "address");
	}
	
	@Test
	void testTypeId() {
		supplierDetails.setTypeId(1);
		assertEquals(supplierDetails.getTypeId(), 1);
	}
	
	@Test
	public void testToString() {
		supplierDetails.setName("supplier");
		supplierDetails.setPassword("supplier");
		supplierDetails.setContactNumber("123");
		supplierDetails.setAddress("address");
		supplierDetails.setTypeId(1);
		assertTrue(supplierDetails.toString().contains("supplier"));
	}
}

