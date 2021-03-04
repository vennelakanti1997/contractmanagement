package com.example.contractmanagement.service;

import java.util.ArrayList;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.contractmanagement.exceptionhandling.ContractCreationFailedException;
import com.example.contractmanagement.exceptionhandling.ContractNotFoundException;
import com.example.contractmanagement.exceptionhandling.NoCreatedContractsException;
import com.example.contractmanagement.exceptionhandling.SupplierNotFoundException;
import com.example.contractmanagement.exceptionhandling.UnauthorizedException;
import com.example.contractmanagement.model.Contract;
import com.example.contractmanagement.model.Supplier;
import com.example.contractmanagement.repository.ContractRepository;
import com.example.contractmanagement.repository.SupplierRepository;
import com.example.contractmanagement.util.ContractDetails;
import com.example.contractmanagement.util.SupplierUtil;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
@Transactional
public class ContractServiceImpl implements ContractService {

	@Autowired
	private ContractRepository contractRepo;
    @Autowired
	private SupplierRepository supplierRepo;
    
    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ContractorSerivce contractorSerivce;
    /*Save or modify contracts*/
    @Override
	public String createOrModifyContract(ContractDetails contractDetails, String token) {
		log.info("START");
    	if(!supplierService.getValidity(token).isValid()) {
            log.error("Unauthorized access. Throwing Unauthorized Exception");
    		throw new UnauthorizedException();
    	}
		Contract contract;

		if (contractDetails.getId() != null) {
			contract = contractRepo.findById(contractDetails.getId()).orElse(null);
			if(contract == null) {
				log.error("Contract with the given id is not found. Throwing ContractNotFound Exception");
				throw new ContractNotFoundException();
			}
		}
		else {
			contract = new Contract();
		}
		
		if(contractDetails.getContractType() != null) {
				contract.setContractType(contractDetails.getContractType());
		}
		
		if(contractDetails.getDuration() != null) {
			contract.setContractDuration(contractDetails.getDuration());
		}
		if(contractDetails.getTnc() != null) {
			contract.setTermsAndConditions(contractDetails.getTnc() );
		}
		if(contractDetails.getSupplierId() != null) {
			Supplier supplier = supplierRepo.findById(contractDetails.getSupplierId()).orElse(null);
			if(supplier == null) {
				log.error("Supplier with the given id not found. Throwing SupplierNotFound Exception");
				throw new SupplierNotFoundException();
						
			}
			contract.setSupplier(supplier);
		}
		if(contractDetails.getStatus() != null) {
			contract.setStatus(contractDetails.getStatus());
		}
		if(contractDetails.getAmenities() != null) {
			contract.setAmenities(contractDetails.getAmenities());
		}
		
		try {
		Integer returnId = contractRepo.save(contract).getId();
		log.info("END");
		return "Contract saved Successfully. ContractId is: "+ returnId;
		} catch(NullPointerException ex) {
			log.error("Contract cannot be created. Throwing ContractCreationFailed Exception");
			throw new ContractCreationFailedException();
		}
		
	}
    
    /*Gets either Waiting For Approval Contracts or closed contracts of a supplier, which ever is mentioned*/
	@Override
	public List<Integer> getCreatedContractsbyStatus(Integer supplierId, String status, String token) {
		log.info("START");
		if(!(supplierService.getValidity(token).isValid() || contractorSerivce.getValidity(token).isValid())) {
            log.error("Unauthorized access. Throwing Unauthorized Exception");
    		throw new UnauthorizedException();
    	}
		List<Integer> contracts = contractRepo.findBySupplierIdAndStatus(supplierId, status);
		if(contracts.isEmpty()) {
			log.error("Found no active contracts. Throwing NoContractorsException");
			throw new NoCreatedContractsException();
		}
		log.info("END");
		return contracts;
	}

	/*Get All contracts of a supplier*/
	@Override
	public List<ContractDetails> getContractsBySupplierId(Integer supplierId, String token) {
		log.info("START");
		if(!supplierService.getValidity(token).isValid()) {
            log.error("Unauthorized access. Throwing Unauthorized Exception");
    		throw new UnauthorizedException();
    	}
		List<Contract> contracts = contractRepo.findBySupplierId(supplierId);
		if(contracts.isEmpty()) {
			log.error("No Contracts with the given SpplierId. Throwing NoCreatedContractsException.");
			throw new NoCreatedContractsException();
		}
		List<ContractDetails> contractDetails = new ArrayList<>();
		for(Contract c : contracts) {
			ContractDetails details = new ContractDetails(c.getId(), c.getContractType(), c.getContractDuration(), c.getTermsAndConditions(), supplierId, c.getStatus(), c.getAmenities());
			contractDetails.add(details);
		}
		log.info("END");
		return contractDetails;
	}

	
	/*Get contract based on Contract ID */
	@Override
	public ContractDetails getContract(Integer contractId, String token) {
		log.info("START");
		if(!(supplierService.getValidity(token).isValid() || contractorSerivce.getValidity(token).isValid())) {
            log.error("Unauthorized access. Throwing Unauthorized Exception");
    		throw new UnauthorizedException();
    	}
		Contract contract = contractRepo.findById(contractId).orElse(null);
		if (contract == null) {
			log.error("Cannot find the contract. Throwing ContractNotFoundException");
			throw new ContractNotFoundException();
		}
		log.info("END");
		return new ContractDetails(contractId, contract.getContractType(),contract.getContractDuration(), contract.getTermsAndConditions(), contract.getSupplier().getId(), contract.getStatus(), contract.getAmenities());
	}

	
	@Override
	public List<ContractDetails> getContractsOfAllSuppliers(String token) {
		log.info("START");
		if(!contractorSerivce.getValidity(token).isValid()) {
            log.error("Unauthorized access. Throwing Unauthorized Exception");
    		throw new UnauthorizedException();
    	}
		List<Contract> contracts = contractRepo.findAll();
		if(contracts.isEmpty()) {
			log.error("No such contracts found. Throwing NoCreatedContractsException");
			throw new NoCreatedContractsException();
		}
		List<ContractDetails> contractDetails = new ArrayList<>();
		for(Contract c : contracts) {
			ContractDetails details = new ContractDetails(c.getId(), c.getContractType(), c.getContractDuration(), c.getTermsAndConditions(), c.getSupplier().getId(), c.getStatus(), c.getAmenities());
			contractDetails.add(details);
		}
		log.info("END");
		return contractDetails;
	}

	@Override
	public List<Object[]> getActiveContractIds(Integer supplierId, String token) {
		log.info("START");
		if(!(supplierService.getValidity(token).isValid() || contractorSerivce.getValidity(token).isValid())) {
            log.error("Unauthorized access. Throwing Unauthorized Exception");
    		throw new UnauthorizedException();
    	}
		List<Object[]> ids = contractRepo.getIdAndStatus(supplierId,"Active");
		if(ids.isEmpty()) {
			log.error("No such contracts found. Throwing NoCreatedContractsException");
			throw new NoCreatedContractsException();
		}
		log.info("END");
		return ids;
	}

	@Override
	public List<Object[]> getActiveContractsOfAllSuppliers(String token) {
		log.info("START");
		if(!contractorSerivce.getValidity(token).isValid()) {
            log.error("Unauthorized access. Throwing Unauthorized Exception");
    		throw new UnauthorizedException();
    	}
		List<Object[]> ids = contractRepo.getIdAndStatus("Active");
		if(ids.isEmpty()) {
			log.error("No such contracts found. Throwing NoCreatedContractsException");
			throw new NoCreatedContractsException();
		}
		return ids;
	}

	
	@Override
	public List<SupplierUtil> getSuppliersofActiveContracts(boolean supplierFlag, String token) {
		log.info("START");
		if(!contractorSerivce.getValidity(token).isValid()) {
            log.error("Unauthorized access. Throwing Unauthorized Exception");
    		throw new UnauthorizedException();
    	}
		List<String> statuses = new ArrayList<String>();
		
		if (supplierFlag == true) {
		  statuses.add("Waiting For Approval");
		}
		statuses.add("Active");
		statuses.add("Active - Delivered");
		statuses.add("Active - Not Delivered");
		
		Set<Supplier> suppliers = contractRepo.findByStatuses(statuses);
		if(suppliers.isEmpty()) {
			log.error("No such contracts found. Throwing NoCreatedContractsException");
			throw new NoCreatedContractsException();
		}
		List<SupplierUtil> supplierUtils = new ArrayList<>();
		for (Supplier s :suppliers) {
			SupplierUtil detail = new SupplierUtil(s.getId(), s.getName(),s.getType().getType(), s.getContactNumber(), s.getAddress());
			supplierUtils.add(detail);
		}
	    log.info("END");
		return supplierUtils;
	}

	@Override
	public List<ContractDetails> getAllActiveContracts(String token) {
		log.info("START");
		if(!contractorSerivce.getValidity(token).isValid()) {
            log.error("Unauthorized access. Throwing Unauthorized Exception");
    		throw new UnauthorizedException();
    	}
		List<Contract> contracts= contractRepo.findByStatusContains("Active");
		if(contracts.isEmpty()) {
			log.error("No Active Contracts. Throwing NoCreatedContractsException");
			throw new NoCreatedContractsException();
		}
		List<ContractDetails> contractDetails = new ArrayList<>();
		for(Contract c : contracts) {
			ContractDetails details = new ContractDetails(c.getId(), c.getContractType(), c.getContractDuration(), c.getTermsAndConditions(), c.getSupplier().getId(), c.getStatus(), c.getAmenities());
			contractDetails.add(details);
		}
		log.info("END");
		return contractDetails;
	}
	
//	@Override
//	public Set<Supplier> fetchAllSuppliersWithActiveContracts(String token){
//		List<Contract> contracts = contractRepo.findAll();
//		List<Contract> activeContracts = contracts.stream()
//				 .filter( ct -> ct.getStatus().startsWith("Active")).collect(Collectors.toList());
//		Set<Supplier> suppliers = new HashSet<Supplier>();
//		activeContracts.forEach(ct->suppliers.add(ct.getSupplier()));
//		
//		return suppliers;
//	}
}

	

	


	
