package com.example.contractmanagement.service;

import java.util.List;

import com.example.contractmanagement.util.ContractDetails;
import com.example.contractmanagement.util.SupplierUtil;

public interface ContractService {
    /*Save / modify contracts*/
	public String createOrModifyContract(ContractDetails contractDetails, String token);
	
	/*Gets either Waiting For Approval Contracts or closed contracts of a supplier, which ever is mentioned
	 * Waiting For Approval Contract Ids are fetched to set Amenities or edit the contract.
	 * */
	public List<Integer> getCreatedContractsbyStatus(Integer supplierId, String status, String token);
	
	/*Get All contracts of a supplier*/
	public List<ContractDetails> getContractsBySupplierId(Integer supplierId, String token);
	
	/*Get contract based on Contract ID */
	public ContractDetails getContract(Integer contractId, String token);
	
	
	/*Get ALl contracts in the COntracts table*/
	public List<ContractDetails>getContractsOfAllSuppliers(String token);
	
	/*Get All the active contract ids and statuses of a supplier*/
	public List<Object[]> getActiveContractIds(Integer supplierId, String token);
	
	/*Get the active contracts of all suppliers*/
	public List<Object[]> getActiveContractsOfAllSuppliers(String token);
	
	/*Lists All the suppliers in Contract with the Admin
	 * If flag is true-get submitted contracts along with active contracts
	 * (Used to display  the submitted and active contracts for supplier when clicked on Contracts button (US_22))
	 * */
	public List<SupplierUtil> getSuppliersofActiveContracts(boolean supplierFlag, String token);
	
	public List<ContractDetails> getAllActiveContracts(String token);
	
	
//	public Set<Supplier> fetchAllSuppliersWithActiveContracts(String token);
}

