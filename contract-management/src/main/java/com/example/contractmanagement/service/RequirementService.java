package com.example.contractmanagement.service;

import java.util.List;

import com.example.contractmanagement.util.RequirementDetails;

public interface RequirementService {
	public boolean createRequirement(RequirementDetails requirementD, Integer typeId, String token);
	public List<RequirementDetails> viewAllRequirementsAdmin( String token);
	public List<Integer> viewAllRequirementByTypeSupplier(Integer typeId, String token);
	public RequirementDetails viewRequirementById(Integer requirementId, String token);
	public boolean editRequirement(String deliveryDate, String description,Integer typeId, Integer requirementId, String token);
	public boolean deleteRequirementById(Integer requirementId, String token);
}
