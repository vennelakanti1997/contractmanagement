package com.example.contractmanagement.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.contractmanagement.model.Proposal;
import com.example.contractmanagement.model.Requirement;
import com.example.contractmanagement.model.Supplier;
import com.example.contractmanagement.service.ProposalService;
import com.example.contractmanagement.util.DateUtil;
import com.example.contractmanagement.util.ProposalDetails;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class ProposalControllerTest {

	private Proposal proposal;
	private List<ProposalDetails> proposals;
	private Supplier supplier;
	private Requirement requirement;
	private ProposalDetails proposalDetails;
	private BigDecimal bd;
	private String token;
	
	
	@BeforeAll
	public void commonFunctionality() {
		
		Proposal proposal = new Proposal();
		Supplier supplier =new Supplier();
		supplier.setName("name");
		this.supplier=supplier;
		Requirement requirement=new Requirement();
		requirement.setId(1);
		this.requirement=requirement;
		ProposalDetails proposalDetails= new ProposalDetails();
		BigDecimal bd =  new BigDecimal("124567890.0987654321");
		this.bd=bd;
		proposal.setId(1);
		proposal.setProposalDate(DateUtil.convertToDate("2022-11-11"));
		proposal.setQuotation(bd);
		proposal.setRequirement(requirement);
		proposal.setStatus("status");
		proposal.setSupplier(supplier);
		this.proposal=proposal;
		
		proposalDetails.setId(1);
		proposalDetails.setProposalDate("2022-11-11");
		proposalDetails.setQuotation(bd.toString());
		proposalDetails.setRequirementId(requirement.getId());
		proposalDetails.setStatus("status");
		proposalDetails.setSupplierName(supplier.getName());
		this.proposalDetails=proposalDetails;
		List<ProposalDetails> proposals = new ArrayList<ProposalDetails>();
		proposals.add(proposalDetails);
		this.proposals = proposals;
	}

	@InjectMocks
	ProposalController proController;
	
	@Mock
	ProposalService proService;
	
	@Test
	void createProposalTest() {
		 when(proService.createProposal(proposalDetails,1,1,token)).thenReturn(true);
		 assertEquals(proController.createProposal(proposalDetails,1, 1,token).getBody(), "Proposal Created Successfully" );
	}
	
	@Test
	void viewAllProposalsAdminTest() {
		List<ProposalDetails> proposalsList = new ArrayList<ProposalDetails>();
		for(ProposalDetails p : proposals)
			proposalsList.add(p);
		when(proService.viewAllProposals(token)).thenReturn(proposalsList);
		assertEquals(proController.viewAllProposalsAdmin(token).getBody(), proposalsList);
	}
	
	@Test
	void viewProposalById() {
		when(proService.viewProposalById(1,token)).thenReturn(proposalDetails);
		assertEquals(proController.viewProposalById(1,token).getBody(), proposalDetails);
	}
	
	@Test
	void editProposalStatus() {
		when(proService.editProposalStatus(1,"status",token)).thenReturn(true);
		assertEquals(proController.editProposalStatus(1,"status",token).getBody(), "Proposal Status Edited Successfully" );
	}
	
	@Test
	void viewProposalsBySupplierTest() {
		List<ProposalDetails> proposalsList = new ArrayList<ProposalDetails>();
		for(ProposalDetails p : proposals)
			proposalsList.add(p);
		when(proService.viewProposalsBySupplier(1,token)).thenReturn(proposalsList);
		assertEquals(proController.viewProposalsbySupplier(1,token).getBody(), proposalsList);
	}
	
	@Test
	void editProposalBySupplierTest() {
		when(proService.editProposalBySupplier(1, "2022-11-11", "1000",token)).thenReturn(true);
		assertEquals(proController.editProposalBySupplier(1, "2022-11-11", "1000",token).getBody(), "Proposal Edited Successfully" );
	}
	
	@Test
	void viewtobeRevisitedProposalsTest() {
		List<ProposalDetails> proposalsList = new ArrayList<ProposalDetails>();
		for(ProposalDetails p : proposals)
			proposalsList.add(p);
		when(proService.viewToBeRevisitedProposals(token)).thenReturn(proposalsList);
		assertEquals(proController.viewToBeRevisitedProposals(token).getBody(), proposalsList);
	}
}