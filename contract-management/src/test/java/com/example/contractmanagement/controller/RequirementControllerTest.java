package com.example.contractmanagement.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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


import com.example.contractmanagement.model.Requirement;
import com.example.contractmanagement.model.Types;
import com.example.contractmanagement.service.RequirementService;
import com.example.contractmanagement.util.DateUtil;
import com.example.contractmanagement.util.RequirementDetails;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class RequirementControllerTest {
	private Integer typeId = 1;
	private Requirement requirement;
	private List<Requirement> requirements;
	private Types type;
	private RequirementDetails requirementDetails;
	private String token;
	
	
	
	@BeforeAll
	public void commonFunctionality() {
		
		Requirement requirement = new Requirement();
		Types type =new Types();
		RequirementDetails requirementDetails= new RequirementDetails();
		type.setId(typeId);
		type.setType("type0");
		this.type=type;
		requirement.setId(1);
		requirement.setDeliveryDate(DateUtil.convertToDate("2022-11-11"));
		requirement.setDescription("Description");
		requirement.setType(type);
		this.requirement=requirement;
		requirementDetails.setId(1);
		requirementDetails.setDeliveryDate("2022-11-11");
		requirementDetails.setDescription("Description");
		this.requirementDetails=requirementDetails;
		
		List<Requirement> requirements = new ArrayList<Requirement>();
		requirements.add(requirement);
		this.requirements = requirements;
	}

	@InjectMocks
	RequirementController reqController;
	
	@Mock
	RequirementService reqService;
	
	
	
	@Test
	void createRequirementTest() {
		 when(reqService.createRequirement(requirementDetails,1,token)).thenReturn(true);
		 assertEquals(reqController.createRequirement(requirementDetails, 1,token).getBody(), "Requirement Created Successfully" );
	}
	
	@Test
	void viewAllRequirementsAdminTest() {
		List<RequirementDetails> requirementIds = new ArrayList<RequirementDetails>();
		for(Requirement r : requirements)
		{
			RequirementDetails req =new RequirementDetails();
			req.setDeliveryDate(DateUtil.convertToString(r.getDeliveryDate()));
			req.setDescription(r.getDescription());
			req.setId(r.getId());
			requirementIds.add(req);
		}
		when(reqService.viewAllRequirementsAdmin(token)).thenReturn(requirementIds);
		assertEquals(reqController.viewAllRequirementAdmin(token).getBody(), requirementIds);
	}
	
	@Test
	void viewAllBySupplierTest() {
		List<Integer> requirementIds = new ArrayList<Integer>();
		for(Requirement r : requirements)
			requirementIds.add(r.getId());
		when(reqService.viewAllRequirementByTypeSupplier(typeId,token)).thenReturn(requirementIds);
		assertEquals(reqController.viewAllBySupplier(typeId,token).getBody(), requirementIds);
	}
	
	@Test
	void viewByIdTest() {
		when(reqService.viewRequirementById(1,token)).thenReturn(requirementDetails);
		assertEquals(reqController.viewById(1,token).getBody(), requirementDetails);
	}
	
	@Test
	void editRequirementTest() {
		when(reqService.editRequirement("2022-11-11","Description",typeId,1,token)).thenReturn(true);
		assertEquals(reqController.editRequirement("2022-11-11","Description",typeId,1,token).getBody(), "Requirement Edited Successfully" );
	}

	@Test
	void deleteRequirementTest() {
		when(reqService.deleteRequirementById(1,token)).thenReturn(true);
		assertEquals(reqController.deleteRequirement(1,token).getBody(), "Requirement Deleted Successfully" );
	}
}