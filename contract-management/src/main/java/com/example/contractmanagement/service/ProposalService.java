package com.example.contractmanagement.service;

import java.util.List;
import com.example.contractmanagement.util.ProposalDetails;


public interface ProposalService {
	public boolean createProposal(ProposalDetails proposal, Integer requirementId,Integer supplierId,String token);
	public List<ProposalDetails> viewAllProposals(String token);
	public ProposalDetails viewProposalById(Integer id,String token);
	public boolean editProposalStatus(Integer id, String status,String token);
	public List<ProposalDetails> viewProposalsBySupplier(Integer supplierId,String token);
	public boolean editProposalBySupplier(Integer id, String proposalDate, String quotation,String token);
	public List<ProposalDetails> viewToBeRevisitedProposals(String token);
	
}
