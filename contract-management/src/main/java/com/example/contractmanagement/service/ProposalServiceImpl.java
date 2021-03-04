package com.example.contractmanagement.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.contractmanagement.exceptionhandling.ProposalNotFoundException;
import com.example.contractmanagement.exceptionhandling.ProposalsEmptyException;
import com.example.contractmanagement.exceptionhandling.UnauthorizedException;
import com.example.contractmanagement.model.Proposal;
import com.example.contractmanagement.model.Requirement;
import com.example.contractmanagement.model.Supplier;
import com.example.contractmanagement.repository.ProposalRepository;
import com.example.contractmanagement.repository.RequirementRepository;
import com.example.contractmanagement.repository.SupplierRepository;
import com.example.contractmanagement.util.DateUtil;
import com.example.contractmanagement.util.ProposalDetails;
@Service
public class ProposalServiceImpl implements ProposalService {

	@Autowired
	ProposalRepository proposalRepo;
	
	@Autowired
	SupplierRepository supplierRepo;
	
	@Autowired
	RequirementRepository requirementRepo;
	
	 @Autowired
	 private SupplierService supplierService;

	 @Autowired
	 private ContractorSerivce contractorSerivce;
	
	@Override
	public boolean createProposal(ProposalDetails proposal, Integer requirementId, Integer supplierId,String token) {
		if(!supplierService.getValidity(token).isValid()) {
    		throw new UnauthorizedException();
    	}
		else{Requirement requirement = requirementRepo.findById(requirementId).orElse(null);
		Supplier supplier = supplierRepo.findById(supplierId).orElse(null);
		Proposal proposal0=new Proposal();
		proposal0.setRequirement(requirement);
		proposal0.setStatus("Submitted");
		proposal0.setProposalDate(DateUtil.convertToDate(proposal.getProposalDate()));
		BigDecimal bd =new BigDecimal(proposal.getQuotation());
		proposal0.setQuotation(bd);
		proposal0.setSupplier(supplier);
		proposalRepo.save(proposal0);
		return true;
		}
	}
	
	@Override
	public List<ProposalDetails> viewAllProposals(String token){
		if(!contractorSerivce.getValidity(token).isValid()) {
    		throw new UnauthorizedException();
    	}
		List<Proposal> list = proposalRepo.findAllByStatus("Submitted");
		if(list.isEmpty())
			throw new ProposalsEmptyException();
		else {
		List<ProposalDetails> idList=new ArrayList<ProposalDetails>();
		for (Proposal proposal : list){
			ProposalDetails proposalDetails = new ProposalDetails();
			proposalDetails.setId(proposal.getId());
			proposalDetails.setRequirementId(proposal.getRequirement().getId());
			proposalDetails.setSupplierName(proposal.getSupplier().getName());
			idList.add(proposalDetails);
			}
		return idList;	
		}
	}
	
	@Override
	public ProposalDetails viewProposalById(Integer id,String token) {
		if((!supplierService.getValidity(token).isValid() || !contractorSerivce.getValidity(token).isValid())) {
			throw new UnauthorizedException();
    	}
		else {
			Proposal proposal = proposalRepo.findById(id).orElse(null);
			if(proposal==null) throw new ProposalNotFoundException();
			else{
				ProposalDetails proposalDetails = new ProposalDetails();
				proposalDetails.setId(proposal.getId());
				proposalDetails.setRequirementId(proposal.getRequirement().getId());
				proposalDetails.setSupplierName(proposal.getSupplier().getName());
				proposalDetails.setQuotation(proposal.getQuotation().toString());
				proposalDetails.setStatus(proposal.getStatus());
				proposalDetails.setProposalDate(DateUtil.convertToString(proposal.getProposalDate()));
				return proposalDetails;
			}
		}
		
		
	}
	
	@Override
	public List<ProposalDetails> viewToBeRevisitedProposals(String token){
		if(!contractorSerivce.getValidity(token).isValid()) {
    		throw new UnauthorizedException();
    	}
		List<Proposal> list = proposalRepo.findAllByStatus("To Be Revisited");
		if(list.isEmpty())
			throw new ProposalsEmptyException();
		List<ProposalDetails> idList=new ArrayList<ProposalDetails>();
		for (Proposal proposal : list){
			ProposalDetails proposalDetails = new ProposalDetails();
			proposalDetails.setId(proposal.getId());
			proposalDetails.setRequirementId(proposal.getRequirement().getId());
			proposalDetails.setSupplierName(proposal.getSupplier().getName());
			idList.add(proposalDetails);
			}
		return idList;	
	}
	
	@Override
	public boolean editProposalStatus(Integer id, String status,String token) {
		if(!contractorSerivce.getValidity(token).isValid()) {
    		throw new UnauthorizedException();
    	}
		Proposal proposal = proposalRepo.findById(id).orElse(null);
		if(proposal==null) throw new ProposalNotFoundException();
		else{proposal.setStatus(status);
		proposalRepo.save(proposal);
		return true;
		}
	}
	
	@Override
	public List<ProposalDetails> viewProposalsBySupplier(Integer supplierId,String token){
		if(!supplierService.getValidity(token).isValid()) {
    		throw new UnauthorizedException();
    	}
		Supplier supplier= supplierRepo.findById(supplierId).orElse(null);
		List<Proposal> list = proposalRepo.findAllBySupplier(supplier);
		if(list.isEmpty())
			throw new ProposalsEmptyException();
		else {
			List<ProposalDetails> idList=new ArrayList<ProposalDetails>();
			for (Proposal proposal : list){
				ProposalDetails proposalDetails = new ProposalDetails();
				proposalDetails.setId(proposal.getId());
				proposalDetails.setRequirementId(proposal.getRequirement().getId());
				proposalDetails.setProposalDate(DateUtil.convertToString(proposal.getProposalDate()));
				proposalDetails.setQuotation(proposal.getQuotation().toString());
				proposalDetails.setStatus(proposal.getStatus());
				idList.add(proposalDetails);
			}
			return idList;
		}
	}
	
	@Override
	public boolean editProposalBySupplier(Integer id, String proposalDate, String quotation,String token) {
		if(!supplierService.getValidity(token).isValid()) {
    		throw new UnauthorizedException();
    	}
		Proposal proposal1 = proposalRepo.findById(id).orElse(null);
		if(proposal1==null) throw new ProposalNotFoundException();
		else{
			proposal1.setProposalDate(DateUtil.convertToDate(proposalDate));
			BigDecimal quotationBD=new BigDecimal(quotation);
			proposal1.setQuotation(quotationBD);
			proposalRepo.save(proposal1);
			return true;
		}
	}

}