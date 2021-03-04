package com.example.contractmanagement.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SupplierUtilTest {
	SupplierUtil util = new SupplierUtil();
	SupplierUtil util2 = new SupplierUtil(1, "supplier", "type 1", "1231231231", "delhi");
	
	@Test
	public void idTest() {
		util.setId(1);
		assertEquals(util.getId(), 1);
	}
	@Test
	public void nameTest() {
		util.setName("supplier");
		assertEquals(util.getName(), "supplier");
	}
	@Test
	public void typeTest() {
		util.setType("type 1");
		assertEquals(util.getType(), "type 1");
	}
	@Test
	public void addressTest() {
		util.setAddress("delhi");
		assertEquals(util.getAddress(), "delhi");
	}
	@Test
	public void numberTest() {
		util.setNumber("1231231231");
		assertEquals(util.getNumber(), "1231231231");
	}
	@Test
	public void allArgsTest() {
		assertTrue(util2.toString().contains("supplier"));
	}
	@Test
	public void toStringTest() {
		util.setId(1);
		util.setName("supplier");
		util.setType("type 1");
		util.setAddress("delhi");
		util.setNumber("1231231231");
		assertTrue(util.toString().contains("supplier"));
	}
}
