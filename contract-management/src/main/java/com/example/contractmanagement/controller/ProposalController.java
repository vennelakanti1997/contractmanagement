package com.example.contractmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.contractmanagement.util.ProposalDetails;
import com.example.contractmanagement.service.ProposalService;

@CrossOrigin
@RequestMapping("/proposal")
@RestController
public class ProposalController {

	@Autowired
	ProposalService service;
	
	@PostMapping(value ="/create/{requirementId}/{supplierId}")
	public ResponseEntity<String> createProposal(@RequestBody ProposalDetails proposal, @PathVariable("requirementId") Integer requirementId, @PathVariable("supplierId") Integer supplierId,@RequestHeader("Authorization") String token) {
		service.createProposal(proposal, requirementId, supplierId,token);
		return ResponseEntity.status(HttpStatus.OK).body("Proposal Created Successfully");
	}
	
	@GetMapping(value = "/viewall")
	public ResponseEntity<List<ProposalDetails>> viewAllProposalsAdmin(@RequestHeader("Authorization") String token){
		return ResponseEntity.status(HttpStatus.OK).body(service.viewAllProposals(token));
	}
	
	@GetMapping(value="/viewbyid/{id}")
	public ResponseEntity<ProposalDetails> viewProposalById(@PathVariable("id") Integer id,@RequestHeader("Authorization") String token) {
		return ResponseEntity.status(HttpStatus.OK).body(service.viewProposalById(id,token));
	}
	
	@PutMapping(value="/editstatus/{id}")
	public ResponseEntity<String> editProposalStatus(@PathVariable("id") Integer id, @RequestParam("status") String status,@RequestHeader("Authorization") String token) {
		 service.editProposalStatus(id, status,token);
		 return ResponseEntity.status(HttpStatus.OK).body("Proposal Status Edited Successfully");
	}
	
	@GetMapping(value = "/viewbysupplier/{supplierId}")
	public ResponseEntity<List<ProposalDetails>> viewProposalsbySupplier(@PathVariable("supplierId") Integer supplierId,@RequestHeader("Authorization") String token){
		return ResponseEntity.status(HttpStatus.OK).body(service.viewProposalsBySupplier(supplierId,token));
	}
	
	@PutMapping(value="/editbysupplier/{id}")
	public ResponseEntity<String> editProposalBySupplier(@PathVariable("id") Integer id,@RequestParam("ProposalDate") String proposalDate, @RequestParam("quotation") String quotation,@RequestHeader("Authorization") String token) {
		 service.editProposalBySupplier(id, proposalDate, quotation,token);
		 return ResponseEntity.status(HttpStatus.OK).body("Proposal Edited Successfully");
	}
	
	@GetMapping(value = "/viewtorevisit")
	public ResponseEntity<List<ProposalDetails>> viewToBeRevisitedProposals(@RequestHeader("Authorization") String token){
		return ResponseEntity.status(HttpStatus.OK).body(service.viewToBeRevisitedProposals(token));
	}
}
	
	
	