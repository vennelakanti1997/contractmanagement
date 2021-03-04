package com.example.contractmanagement.util;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class ProposalDetailsTest {

	ProposalDetails proposal = new ProposalDetails();
	
	@Test
	void test() {
		proposal.setId(1);
		assertEquals(proposal.getId(),1);
		proposal.setProposalDate("2022-11-11");
		assertEquals(proposal.getProposalDate(),"2022-11-11");
		BigDecimal bd =  new BigDecimal("124567890.0987654321");
		proposal.setQuotation(bd.toString());
		assertEquals(proposal.getQuotation(),bd.toString());
		proposal.setRequirementId(1);
		assertEquals(proposal.getRequirementId(),1);
		proposal.setStatus("Submitted");
		assertEquals(proposal.getStatus(),"Submitted");
		proposal.setSupplierName("SName");
		assertEquals(proposal.getSupplierName(),"SName");}

}
