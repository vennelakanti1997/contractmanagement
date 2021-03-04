package com.example.contractmanagement.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ContractDetailsTest {

	ContractDetails contractDetails= new  ContractDetails(); 
	ContractDetails anotherContractDetails= new  ContractDetails(1,"contract type", 1, "Terms and COnditions", 1, "status", "amenities"); 
	@Test
	void test() {
		contractDetails.setId(1);
		assertEquals(contractDetails.getId(), 1);
		contractDetails.setContractType("contract type");
		assertEquals(contractDetails.getContractType(),"contract type");
		contractDetails.setDuration(1);
		assertEquals(contractDetails.getDuration(), 1);
		contractDetails.setTnc("Terms and COnditions");
		assertEquals(contractDetails.getTnc(), "Terms and COnditions");
		contractDetails.setStatus("status");
		assertEquals(contractDetails.getStatus(), "status");
		contractDetails.setAmenities("amenities");
		assertEquals(contractDetails.getAmenities(), "amenities");
		contractDetails.setSupplierId(1);
		assertEquals(contractDetails.getSupplierId(), 1);
		assertEquals(contractDetails.toString(), anotherContractDetails.toString());
	}

}
