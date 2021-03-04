package com.example.contractmanagement.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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

import com.example.contractmanagement.exceptionhandling.RequirementsEmptyException;
import com.example.contractmanagement.model.AuthResponse;
import com.example.contractmanagement.model.Requirement;
import com.example.contractmanagement.model.Supplier;
import com.example.contractmanagement.model.Types;
import com.example.contractmanagement.repository.RequirementRepository;
import com.example.contractmanagement.repository.TypesRepository;
import com.example.contractmanagement.util.DateUtil;
import com.example.contractmanagement.util.RequirementDetails;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class RequirementServiceImplTest {

	private Integer typeId = 1;
	private Integer supplierId = 1;
	private Requirement requirement;
	private List<Requirement> requirements;
	private Types type;
	private RequirementDetails requirementDetails;
	String token;
	private Supplier supplier;
	private AuthResponse sauthResponse;
	private AuthResponse anothersauthResponse;
	private AuthResponse cauthResponse;
	@BeforeAll
	public void commonFunctionality() {
		
		Requirement requirement = new Requirement();
		Types type =new Types();
		RequirementDetails requirementDetails= new RequirementDetails();
		type.setId(typeId);
		type.setType("type0");
		this.type=type;
		requirement.setId(1);
		requirement.setDeliveryDate(DateUtil.convertToDate("2021-01-20"));
		requirement.setDescription("Description");
		requirement.setType(type);
		this.requirement=requirement;
		requirementDetails.setId(1);
		requirementDetails.setDeliveryDate("2021-01-20");
		requirementDetails.setDescription("Description");
		this.requirementDetails=requirementDetails;
		
		
		List<Requirement> requirements = new ArrayList<Requirement>();
		requirements.add(requirement);
		this.requirements = requirements;
		
		AuthResponse authResponse = new AuthResponse(supplierId.toString(), "supplier1", true, "supplier");
		this.sauthResponse = authResponse;
		this.anothersauthResponse = new AuthResponse(supplierId.toString(), "supplier1", false, "supplier");
		this.cauthResponse = new AuthResponse("1", "admin", true, "contractor");
		
	}

	@InjectMocks
	RequirementServiceImpl reqService;
	
	@Mock
	ContractorSerivceImpl conService;
	
	@Mock
	SupplierServiceImpl supService;
	
	
	
	@Mock
	RequirementRepository reqRepo;
	
	@Mock
	TypesRepository typeRepo;
	
	@Test
	void createRequirementTest() {
		when(conService.getValidity(token)).thenReturn(cauthResponse);
		assertEquals(reqService.createRequirement(requirementDetails, typeId, token), true );
	}
	
	@Test
	void viewAllRequirementsAdminTest() {
		List<Requirement> requirements1 = new ArrayList<Requirement>();
		for(Requirement r : requirements) requirements1.add(r);
		List<Integer> requirementIds = new ArrayList<Integer>();
		for(Requirement r : requirements) requirementIds.add(r.getId());
		when(conService.getValidity(token)).thenReturn(cauthResponse);
		when(reqRepo.findAll()).thenReturn(requirements1);
		assertFalse(reqService.viewAllRequirementsAdmin(token).isEmpty());
	}
	@Test
	void viewEmptyRequirements() {
		List <Requirement> emptyList=new ArrayList<Requirement>();
		AuthResponse authr=new AuthResponse();
		when(conService.getValidity(token)).thenReturn(cauthResponse);
		when(reqRepo.findAll()).thenReturn(emptyList);
		assertThrows(RequirementsEmptyException.class, ()->reqService.viewAllRequirementsAdmin(token));
	}
	
	@Test 
	void viewAllRequirementsBySupplier() {
		List<Requirement> requirements1 = new ArrayList<Requirement>();
		for(Requirement r : requirements) requirements1.add(r);
		List<Integer> requirementIds = new ArrayList<Integer>();
		for(Requirement r : requirements) requirementIds.add(r.getId());
		when(supService.getValidity(token)).thenReturn(sauthResponse);
		when(typeRepo.findById(typeId)).thenReturn(Optional.ofNullable(type));
		when(reqRepo.findAllByType(type)).thenReturn(requirements1);
		assertEquals(reqService.viewAllRequirementByTypeSupplier(typeId,token), requirementIds);
	}
	
	@Test
	void viewEmptyRequirementsBySupplier() {
		List <Requirement> emptyList=new ArrayList<Requirement>();
		when(supService.getValidity(token)).thenReturn(sauthResponse);
		when(typeRepo.findById(typeId)).thenReturn(Optional.ofNullable(type));
		when(reqRepo.findAllByType(type)).thenReturn(emptyList);
		assertThrows(RequirementsEmptyException.class, ()->reqService.viewAllRequirementByTypeSupplier(typeId,token));
	}
	
	@Test
	void viewRequirementById() {
		when(supService.getValidity(token)).thenReturn(sauthResponse);
		when(reqRepo.findById(1)).thenReturn(Optional.ofNullable(requirement));
		
		assertNotNull(reqService.viewRequirementById(1,token));
	}
	
	@Test
	void editRequirement() {
		when(conService.getValidity(token)).thenReturn(cauthResponse);
		when(reqRepo.findById(1)).thenReturn(Optional.ofNullable(requirement));
		assertEquals(reqService.editRequirement("2020-01-11","Description", typeId,1,token), true );
	}
	
	@Test
	void deleteRequirement() {
		when(conService.getValidity(token)).thenReturn(cauthResponse);
		assertEquals(reqService.deleteRequirementById(1,token), true );
	}
}
