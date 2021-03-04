package com.example.contractmanagement.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.contractmanagement.exceptionhandling.ProposalsEmptyException;
import com.example.contractmanagement.exceptionhandling.ProposalNotFoundException;
import com.example.contractmanagement.model.AuthResponse;
import com.example.contractmanagement.model.Proposal;
import com.example.contractmanagement.model.Requirement;
import com.example.contractmanagement.model.Supplier;
import com.example.contractmanagement.repository.ProposalRepository;
import com.example.contractmanagement.repository.RequirementRepository;
import com.example.contractmanagement.repository.SupplierRepository;
import com.example.contractmanagement.util.DateUtil;
import com.example.contractmanagement.util.ProposalDetails;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class ProposalServiceImplTest {
	private Integer supplierId = 1;
	private Proposal proposal;
	private List<Proposal> proposalList;
	private List<ProposalDetails> proposals;
	private Supplier supplier;
	private Requirement requirement;
	private ProposalDetails proposalDetails;
	private BigDecimal bd;
	private String token;
	private AuthResponse sauthResponse;
	private AuthResponse anothersauthResponse;
	private AuthResponse cauthResponse;
	
	
	@BeforeAll
	public void commonFunctionality() {
		
		Proposal proposal = new Proposal();
		Supplier supplier =new Supplier();
		supplier.setId(1);
		supplier.setName("name");
		this.supplier=supplier;
		Requirement requirement=new Requirement();
		requirement.setId(1);
		this.requirement=requirement;
		ProposalDetails proposalDetails= new ProposalDetails();
		BigDecimal bd =  new BigDecimal("124567890.0987654321");
		this.bd=bd;
		proposal.setId(1);
		proposal.setProposalDate(DateUtil.convertToDate("2021-11-20"));
		proposal.setQuotation(bd);
		proposal.setRequirement(requirement);
		proposal.setStatus("To Be Revisited");
		proposal.setSupplier(supplier);
		this.proposal=proposal;
		
		proposalDetails.setId(1);
		proposalDetails.setProposalDate("2021-11-20");
		proposalDetails.setQuotation("124567890.0987654321");
		proposalDetails.setRequirementId(requirement.getId());
		proposalDetails.setStatus("To Be Revisited");
		proposalDetails.setSupplierName(supplier.getName());
		this.proposalDetails=proposalDetails;
		List<ProposalDetails> proposals = new ArrayList<ProposalDetails>();
		proposals.add(proposalDetails);
		this.proposals = proposals;
		List<Proposal> proposalList = new ArrayList<Proposal>();
		proposalList.add(proposal);
		this.proposalList = proposalList;
		
		AuthResponse authResponse = new AuthResponse(supplierId.toString(), "supplier1", true, "supplier");
		this.sauthResponse = authResponse;
		this.anothersauthResponse = new AuthResponse(supplierId.toString(), "supplier1", false, "supplier");
		this.cauthResponse = new AuthResponse("1", "admin", true, "contractor");
	}

	@InjectMocks
	ProposalServiceImpl proService;
	
	@Mock
	ProposalRepository proRepo;
	@Mock
	RequirementRepository reqRepo;
	@Mock
	SupplierRepository supRepo;
	@Mock
	ContractorSerivceImpl conService;
	
	@Mock
	SupplierServiceImpl supService;
	
	@Test
	void createProposalTest() {
		when(supService.getValidity(token)).thenReturn(sauthResponse);
		when(reqRepo.findById(1)).thenReturn(Optional.ofNullable(requirement));
		when(supRepo.findById(1)).thenReturn(Optional.ofNullable(supplier));
		assertEquals(proService.createProposal(proposalDetails,1,1,token), true );
	}
	
	@Test
	void viewAllProposalsTest() {
		when(conService.getValidity(token)).thenReturn(cauthResponse);
		when(proRepo.findAllByStatus("Submitted")).thenReturn(proposalList);
		assertNotNull(proService.viewAllProposals(token));
	}
	
	@Test
	void emptyProposalsTest() {
		List <Proposal> emptyList=new ArrayList<Proposal>();
		when(conService.getValidity(token)).thenReturn(cauthResponse);
		when(proRepo.findAllByStatus("Submitted")).thenReturn(emptyList);
		assertThrows(ProposalsEmptyException.class, ()->proService.viewAllProposals(token));
	}
	
	@Test
	void viewProposalByIdTest() {
		when(conService.getValidity(token)).thenReturn(cauthResponse);
		when(supService.getValidity(token)).thenReturn(sauthResponse);
		when(proRepo.findById(1)).thenReturn(Optional.ofNullable(proposal));
		assertNotNull(proService.viewProposalById(proposal.getId(),token));
	}
	
	
	
	@Test
	void viewRevisitedProposals() {
		when(conService.getValidity(token)).thenReturn(cauthResponse);
		when(proRepo.findAllByStatus("To Be Revisited")).thenReturn(proposalList);
		assertNotNull(proService.viewToBeRevisitedProposals(token));
	}
	
	@Test
	void emptyRevisitedProposals() {
		List <Proposal> emptyList=new ArrayList<Proposal>();
		when(conService.getValidity(token)).thenReturn(cauthResponse);
		when(proRepo.findAllByStatus("To Be Revisited")).thenReturn(emptyList);
		assertThrows(ProposalsEmptyException.class, ()->proService.viewToBeRevisitedProposals(token));
	}
	
	@Test
	void editProposalStatus() {
		when(conService.getValidity(token)).thenReturn(cauthResponse);
		when(proRepo.findById(1)).thenReturn(Optional.ofNullable(proposal));
		assertEquals(proService.editProposalStatus(1,"status",token), true );
	}
	
	@Test
	void editEmptyStatus() {
		when(conService.getValidity(token)).thenReturn(cauthResponse);
		when(proRepo.findById(1)).thenReturn(Optional.ofNullable(null));
		assertThrows(ProposalNotFoundException.class, ()->proService.editProposalStatus(1,"status",token));
	}
	
	@Test
	void editEmpty() {
		when(supService.getValidity(token)).thenReturn(sauthResponse);
		when(proRepo.findById(1)).thenReturn(Optional.ofNullable(null));
		assertThrows(ProposalNotFoundException.class, ()->proService.editProposalBySupplier(1,"as","a",token));
	}
	
	@Test
	void editProposal() {
		when(supService.getValidity(token)).thenReturn(sauthResponse);
		when(proRepo.findById(1)).thenReturn(Optional.ofNullable(proposal));
		assertEquals(proService.editProposalBySupplier(1, "2021-11-20", "23123",token), true);
	}
	
	@Test
	void viewProposalBySupplierTest() {
		when(supService.getValidity(token)).thenReturn(sauthResponse);
		when(supRepo.findById(1)).thenReturn(Optional.ofNullable(supplier));
		when(proRepo.findAllBySupplier(supplier)).thenReturn(proposalList);
		assertNotNull(proService.viewProposalsBySupplier(1,token));
	}
	
	@Test
	void emptyProposalBySupplier() {
		List <Proposal> emptyList=new ArrayList<Proposal>();
		when(supService.getValidity(token)).thenReturn(sauthResponse);
		when(supRepo.findById(1)).thenReturn(Optional.ofNullable(supplier));
		when(proRepo.findAllBySupplier(supplier)).thenReturn(emptyList);
		assertThrows(ProposalsEmptyException.class, ()->proService.viewProposalsBySupplier(1,token));
	}
	
}
