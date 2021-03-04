package com.example.contractmanagement.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.Test;

import com.example.contractmanagement.util.DateUtil;

class ProposalTest {
	
	Proposal proposal = new Proposal();
	
	@Test
	void testId() {
		proposal.setId(1);
		assertEquals(proposal.getId(),1);
	}
	
	@Test
	void testProposalDate() {
		Date date= DateUtil.convertToDate("2022-11-11");
		proposal.setProposalDate(date);
		assertEquals(proposal.getProposalDate(),date);
	}
	
	@Test
	void testQuotation() {
		BigDecimal bd =  new BigDecimal("124567890.0987654321"); 
		proposal.setQuotation(bd);
		assertEquals(proposal.getQuotation(),bd);
	}
	
	@Test
	void testStatus() {
		proposal.setStatus("dummy status");
		assertEquals(proposal.getStatus(), "dummy status");
	}
	
	@Test
	void testRequirement() {
		Requirement requirement =new Requirement();
		proposal.setRequirement(requirement);
		assertEquals(proposal.getRequirement(),requirement);
	}
	
	@Test
	void testSupplier() {
		Supplier supplier = new Supplier();
		proposal.setSupplier(supplier);
		assertEquals(proposal.getSupplier(),supplier);
	}
	
	@Test
	void testToString() {
		Proposal proposal =new Proposal();
		proposal.setStatus("Submitted");
		proposal.setProposalDate(DateUtil.convertToDate("2022-11-11"));
		Requirement requirement =new Requirement();
		proposal.setRequirement(requirement);
		Supplier supplier =new Supplier();
		supplier.setName("supplier1");
		supplier.setPassword("Password@0");
		proposal.setSupplier(supplier);
		BigDecimal bd =  new BigDecimal("17890.091");
		proposal.setQuotation(bd);
		String string= "Proposal [id="+proposal.getId()+", proposalDate=" + DateUtil.convertToDate("2022-11-11") + ", quotation=" + bd + ", requirement="+ requirement.getId() + ", supplier=" + supplier.getId() + ", status=" + "Submitted" + "]";
		assertEquals(proposal.toString(),string);
	}	

}
