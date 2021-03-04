package com.example.contractmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.contractmanagement.service.RequirementService;
import com.example.contractmanagement.util.RequirementDetails;

@RequestMapping("/requirement")
@RestController
@CrossOrigin
public class RequirementController {
	
	@Autowired
	RequirementService reqService;
	
	@PostMapping(value ="/create/{typeId}")
	public ResponseEntity<String> createRequirement(@RequestBody RequirementDetails requirementD, @PathVariable("typeId") Integer typeId,@RequestHeader("Authorization") String token)  {
		reqService.createRequirement(requirementD, typeId, token);
		return ResponseEntity.status(HttpStatus.OK).body("Requirement Created Successfully");
	}
	
	@GetMapping(value = "/viewall")
	public ResponseEntity<List<RequirementDetails>> viewAllRequirementAdmin(@RequestHeader("Authorization") String token){
		return ResponseEntity.status(HttpStatus.OK).body(reqService.viewAllRequirementsAdmin(token));
	}
	
	@GetMapping(value = "/viewbysupplier/{supplierId}")
	public ResponseEntity<List<Integer>> viewAllBySupplier(@PathVariable("supplierId") Integer supplierId,@RequestHeader("Authorization") String token){
		return ResponseEntity.status(HttpStatus.OK).body(reqService.viewAllRequirementByTypeSupplier(supplierId,token));
	}
	
	@GetMapping(value="/viewbyid/{id}")
	public ResponseEntity<RequirementDetails> viewById(@PathVariable("id") Integer id,@RequestHeader("Authorization") String token) {
		return ResponseEntity.status(HttpStatus.OK).body(reqService.viewRequirementById(id,token));
	}
	
	@PutMapping(value="/editbyadmin/{id}")
	public ResponseEntity<String> editRequirement(@RequestParam("Date")String deliveryDate,@RequestParam("Description") String description,@RequestParam("Type")Integer typeId,@PathVariable("id") Integer id,@RequestHeader("Authorization") String token) {
		reqService.editRequirement(deliveryDate, description, typeId, id,token);
		return ResponseEntity.status(HttpStatus.OK).body("Requirement Edited Successfully");
	}
	
	
	@DeleteMapping(value="/delete/{id}")
	public ResponseEntity<String> deleteRequirement(@PathVariable("id") Integer id,@RequestHeader("Authorization") String token) {
		 reqService.deleteRequirementById(id,token);
		 return ResponseEntity.status(HttpStatus.OK).body("Requirement Deleted Successfully");
	}

}













