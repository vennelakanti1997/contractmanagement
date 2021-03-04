package com.example.contractmanagement.model;
/*
 * author : Madhurya
 * */
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TypesTest {
	Types type = new Types();
	List<Supplier> suppliers = new ArrayList<Supplier>();
	
	Supplier supplier1 = new Supplier();
	Types type1 = new Types();
	Supplier supplier2 = new Supplier();
	Types type2 = new Types();
	
	@Test
	public void testId() {
		type.setId(1);
		assertEquals(type.getId(), 1);
	}
	
	@Test
	public void testType() {
		type.setType("type1");
		assertEquals(type.getType(), "type1");
	}
	
	@Test
	public void testSupplier() {
		suppliers.add(supplier1);
		suppliers.add(supplier2);
		type.setSuppliers(suppliers);
		assertEquals(type.getSuppliers(), suppliers);
	}
	
	@Test
	public void testToString() {
		type.setId(1);
		type.setType("type1");
		assertTrue(type.toString().contains("type1"));
	}
}
