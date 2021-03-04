package com.example.contractmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.contractmanagement.service.ContractService;
import com.example.contractmanagement.util.ContractDetails;
import com.example.contractmanagement.util.SupplierUtil;

@CrossOrigin
@RequestMapping("/contract")
@RestController
public class ContractController {

	@Autowired
	private ContractService contractService;
	
	/*Create a contract or Modify Contract
	 * @param - contractDetail - Wrapper Class for 
	 *                Id, ContractType, Duration, TermsAndConditions, SupplierId, Status, Amenities
	 * @return - update status along with the id of the contract 
	 * */
	@PostMapping("/supplier/createAndModify")
	public ResponseEntity<String> createOrModifyContract(@RequestBody ContractDetails contractDetails, @RequestHeader("Authorization") String token) {
		return ResponseEntity.ok(contractService.createOrModifyContract(contractDetails, token));
		}
	
	/*Get the Specific status contracts of the supplier
	 * @param - supplierId - Id of the supplier whose contracts had to be fetched
	 * @param - status - the status of the contract that are being fetched
	 * @return - List of contracts whose supplier id is supplierId, status is equal to the status parameter
	 * */
	@GetMapping("/both/contracts/{supplierId}/{status}")
	public ResponseEntity<List<Integer>> getCreatedContractsbyStatus(@PathVariable("supplierId") Integer supplierId, @PathVariable("status") String status, @RequestHeader("Authorization") String token){
		return ResponseEntity.ok(contractService.getCreatedContractsbyStatus(supplierId, status, token));
	}
	
	/*Get the contracts of the supplier
	 * @param - supplierId - Id of the supplier whose contracts had to be fetched
	 * @return - List of contracts whose supplier id is supplierId
	 * */
	@GetMapping("/supplier/contracts/{supplierId}")
	public  ResponseEntity<List<ContractDetails>> getContractsBySupplierId(@PathVariable("supplierId") Integer supplierId, @RequestHeader("Authorization") String token){
		return ResponseEntity.ok(contractService.getContractsBySupplierId(supplierId, token));
	}
	
	/*Get the contract
	 * @param - contractID - Id of the contract which needs to be fetched
	 * @return - Returns the Contract whose Id is contracrtId
	 * */
	@GetMapping("/both/{id}")
	public ResponseEntity<ContractDetails> getContract(@PathVariable("id") Integer contractId, @RequestHeader("Authorization") String token){
		return ResponseEntity.ok(contractService.getContract(contractId, token));
	}
	/*
	 * @return - List of contracts of all the suppliers
	 * */
	@GetMapping("/contractor/contracts")
	public ResponseEntity<List<ContractDetails>> getContractsOfAllSuppliers(@RequestHeader("Authorization") String token){
		return ResponseEntity.ok(contractService.getContractsOfAllSuppliers(token));
	}
	
	/*
	 * @return All the contractIds whose status is Active- Delivered or Active- Not Delivered
	 * */
	@GetMapping("/contractor/activeids")
	public ResponseEntity<List<Object[]>> getActiveContractsOfAllSuppliers(@RequestHeader("Authorization") String token){
		return ResponseEntity.ok(contractService.getActiveContractsOfAllSuppliers(token));
	}
	
	/*
	 * @return All the contractI whose status is Active- Delivered or Active- Not Delivered
	 * */
	@GetMapping("/contractor/active")
	public ResponseEntity<List<ContractDetails>> getAllActiveContracts(@RequestHeader("Authorization") String token){
		return ResponseEntity.ok(contractService.getAllActiveContracts(token));
	}
	
	/*
	 * Gets the Active Contract Ids of the given supplier
	 * @param - supplierId - Id of the supplier whose contracts had to be fetched
	 * @return - All the contracts whose status is Active- Delivered or Active- Not Delivered of the supplier with id equal to supplierId.
	 * */
	@GetMapping("/both/active/{supplierId}")
	public ResponseEntity<List<Object[]>> getActiveContractIds(@PathVariable("supplierId") Integer supplierId,@RequestHeader("Authorization") String token){
		return ResponseEntity.ok(contractService.getActiveContractIds(supplierId, token));
	}
	
	/*
	 *@return List of contracts whose status is Active (Active - Delivered, Active - Not Delivered)
	 *@return - List of all the suppliers who are in contract with the contractor
	 * */
	@GetMapping("/contractor/suppliers")
	public ResponseEntity<List<SupplierUtil>> getSuppliersofActiveContracts(@RequestHeader("Authorization") String token){
		//return ResponseEntity.ok(contractService.fetchAllSuppliersWithActiveContracts());
		return ResponseEntity.ok(contractService.getSuppliersofActiveContracts(false, token));
	}
}
