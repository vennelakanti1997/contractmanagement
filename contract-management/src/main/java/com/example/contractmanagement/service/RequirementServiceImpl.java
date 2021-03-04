package com.example.contractmanagement.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.contractmanagement.exceptionhandling.RequirementsEmptyException;
import com.example.contractmanagement.exceptionhandling.UnauthorizedException;
import com.example.contractmanagement.model.Requirement;
import com.example.contractmanagement.model.Types;
import com.example.contractmanagement.repository.RequirementRepository;
import com.example.contractmanagement.repository.TypesRepository;
import com.example.contractmanagement.util.DateUtil;
import com.example.contractmanagement.util.RequirementDetails;
@Service
public class RequirementServiceImpl implements RequirementService {

	@Autowired
	RequirementRepository requirementRepo;
	
	@Autowired
	TypesRepository typeRepo;
	
	 @Autowired
	 private SupplierService supplierService;

	 @Autowired
	 private ContractorSerivce contractorSerivce;
	
	@Override
	public boolean createRequirement(RequirementDetails requirementD, Integer typeId, String token){
		if(!contractorSerivce.getValidity(token).isValid()) {
    		throw new UnauthorizedException();
    	}
			Types type= typeRepo.findById(typeId).orElse(null);
			Requirement requirement=new Requirement();
			Date date= DateUtil.convertToDate(requirementD.getDeliveryDate());
			requirement.setDeliveryDate(date);
			requirement.setDescription(requirementD.getDescription());
			requirement.setType(type);
			requirementRepo.save(requirement);
			return true;
		}
	
	@Override
	public List<RequirementDetails> viewAllRequirementsAdmin(String token){
		if(!contractorSerivce.getValidity(token).isValid()) {
    		throw new UnauthorizedException();
    	}
		List<Requirement> list=new ArrayList<Requirement>();
		list = requirementRepo.findAll();
		if(list.isEmpty())
			throw new RequirementsEmptyException();
		else{
			List<RequirementDetails> idList=new ArrayList<RequirementDetails>();
		
			for (Requirement requirement:list) {
				RequirementDetails req =new RequirementDetails();
				req.setDeliveryDate(DateUtil.convertToString(requirement.getDeliveryDate()));
				req.setDescription(requirement.getDescription());
				req.setId(requirement.getId());
				req.setType(requirement.getType().getType());
				idList.add(req);
			}
			return idList;
		}
		
	}
	
	@Override
	public List<Integer> viewAllRequirementByTypeSupplier(Integer typeId, String token){
		if(!supplierService.getValidity(token).isValid()) {
          
    		throw new UnauthorizedException();
    	}
			Types type=typeRepo.findById(typeId).orElse(null);
			List<Requirement> list=new ArrayList<Requirement>();
			list = requirementRepo.findAllByType(type);
			if(list.isEmpty())
				throw new RequirementsEmptyException();
			else{
				List<Integer> idList=new ArrayList<Integer>();
			
				for (Requirement requirement:list) {
					idList.add(requirement.getId());
				}
				return idList;
			}
	}
	
	@Override
	public RequirementDetails viewRequirementById(Integer requirementId, String token) {
		if(!supplierService.getValidity(token).isValid()) {
	          
    		throw new UnauthorizedException();
    	}
		else {
			Requirement requirement = requirementRepo.findById(requirementId).orElse(null);
			RequirementDetails req = new RequirementDetails();
			req.setDeliveryDate(DateUtil.convertToString(requirement.getDeliveryDate()));
			req.setDescription(requirement.getDescription());
			req.setId(requirementId);
			return req;
		
	}
	}
	@Override
	public boolean editRequirement(String deliveryDate, String description,Integer typeId, Integer requirementId, String token){
		if(!contractorSerivce.getValidity(token).isValid()) {
    		throw new UnauthorizedException();
    	}
		Requirement updatedReq = requirementRepo.findById(requirementId).orElse(null);
		updatedReq.setDeliveryDate(DateUtil.convertToDate(deliveryDate));
		updatedReq.setDescription(description);
		Types type= typeRepo.findById(typeId).orElse(null); 
		updatedReq.setType(type);
		requirementRepo.save(updatedReq);
		return true;
	}
	
	@Override
	public boolean deleteRequirementById(Integer requirementId, String token){
		if(!contractorSerivce.getValidity(token).isValid()) {
    		throw new UnauthorizedException();
    	}
		requirementRepo.deleteById(requirementId);
		return true;
	}	
	
}
	