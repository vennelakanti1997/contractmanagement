package com.example.contractmanagement.service;

import static org.junit.jupiter.api.Assertions.*;



import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.contractmanagement.exceptionhandling.ContractCreationFailedException;
import com.example.contractmanagement.exceptionhandling.ContractNotFoundException;
import com.example.contractmanagement.exceptionhandling.NoCreatedContractsException;
import com.example.contractmanagement.exceptionhandling.SupplierNotFoundException;
import com.example.contractmanagement.exceptionhandling.UnauthorizedException;
import com.example.contractmanagement.model.AuthResponse;
import com.example.contractmanagement.model.Contract;
import com.example.contractmanagement.model.Supplier;
import com.example.contractmanagement.model.Types;
import com.example.contractmanagement.repository.ContractRepository;
import com.example.contractmanagement.repository.SupplierRepository;
import com.example.contractmanagement.util.ContractDetails;
import com.example.contractmanagement.util.SupplierUtil;



@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class ContractServiceImplTest {
	
	private String contractType ="Some Text";
	private Integer duration = 1;
	String tnc = "Some Text";
	private Integer supplierId = 1;
	private String status="Submitted";
	private String amenities="Some Text";
	
	@InjectMocks
	ContractServiceImpl contractService;
	@Mock
	ContractRepository contractRepo;
	@Mock
	SupplierRepository supplierRepo;
	@Mock
	SupplierService supplierService;
	@Mock
	ContractorSerivce contractorService;
	private Contract contract;
	private List<Contract> contracts;
	
	private Supplier supplier;
	private AuthResponse sauthResponse;
	private AuthResponse anothersauthResponse;
	private AuthResponse cauthResponse;
	private AuthResponse anothercauthResponse;
	private ContractDetails contractDetails = new  ContractDetails(1,"contract type", 1, "Terms and COnditions", 1, "status", "amenities");
	private ContractDetails anotherContractDetails = new  ContractDetails(null,"contract type", 1, "Terms and COnditions", 1, "status", "amenities");
	
	@BeforeAll
	public void commonFunctionality() {
		
		Types type = new Types();
		type.setId(1);
		type.setType("type1");
        Contract contract = new Contract();
		
		Supplier supplier = new Supplier();
		supplier.setId(supplierId);
		supplier.setType(type);
		this.supplier = supplier;
		//contract.setSupplier(supplier);
		contract.setContractType(contractType);
		contract.setContractDuration(duration);
		contract.setTermsAndConditions(tnc);
		contract.setStatus(status);
		contract.setAmenities(amenities);
		contract.setId(1);
		contract.setSupplier(supplier);
		this.contract = contract;
		
		List<Contract> contracts = new ArrayList<Contract>();
		contracts.add(contract);
		this.contracts = contracts;
		
		AuthResponse authResponse = new AuthResponse(supplierId.toString(), "supplier1", true, "supplier");
		this.sauthResponse = authResponse;
		this.anothersauthResponse = new AuthResponse(supplierId.toString(), "supplier1", false, "supplier");
		this.cauthResponse = new AuthResponse("1", "admin", true, "contractor");
		this.anothercauthResponse = new AuthResponse("1", "admin", false, "contractor");
	}
	
	@Test
	void testCreateOrModifyContract() {
		when(supplierService.getValidity("token")).thenReturn(sauthResponse);
		when(contractRepo.findById(1)).thenReturn(Optional.ofNullable(null));
		assertThrows(ContractNotFoundException.class, ()->contractService.createOrModifyContract(contractDetails, "token"));
		
		when(contractRepo.findById(1)).thenReturn(Optional.ofNullable(contract));
		
		when(supplierRepo.findById(1)).thenReturn(Optional.ofNullable(null));
		assertThrows(SupplierNotFoundException.class, ()->contractService.createOrModifyContract(contractDetails, "token"));
		
		
		when(supplierRepo.findById(1)).thenReturn(Optional.ofNullable(supplier));
		when(contractRepo.save(contract)).thenReturn(contract);
		assertEquals(contractService.createOrModifyContract(contractDetails, "token"), "Contract saved Successfully. ContractId is: 1" );
			
		assertThrows(ContractCreationFailedException.class, ()->contractService.createOrModifyContract(anotherContractDetails, "token"));
		
		when(supplierService.getValidity("token1")).thenReturn(anothersauthResponse);
		assertThrows(UnauthorizedException.class, ()->contractService.createOrModifyContract(anotherContractDetails, "token1"));
		
	}
	
	@Test
	void testGetCreatedContractsbyStatus() {
		when(supplierService.getValidity("token")).thenReturn(sauthResponse);
		//when(contractorService.getValidity("token")).thenReturn(anothercauthResponse);
		List<Integer> contractIds = new ArrayList<Integer>();
		for(Contract c : contracts)
			contractIds.add(c.getId());
		when(contractRepo.findBySupplierIdAndStatus(supplierId, status)).thenReturn(contractIds);
		assertEquals(contractService.getCreatedContractsbyStatus(supplierId, status,"token"), contractIds);
		
		when(contractRepo.findBySupplierIdAndStatus(supplierId, status)).thenReturn(new ArrayList<Integer>());
		assertThrows(NoCreatedContractsException.class, ()->contractService.getCreatedContractsbyStatus(supplierId , status,"token"));
	}
	
	@Test
	void anotherTestGetCreatedContractsbyStatus() {
		when(supplierService.getValidity("token")).thenReturn(anothercauthResponse);
		when(contractorService.getValidity("token")).thenReturn(anothercauthResponse);
		List<Integer> contractIds = new ArrayList<Integer>();
		for(Contract c : contracts)
			contractIds.add(c.getId());
		
		assertThrows(UnauthorizedException.class, ()->contractService.getCreatedContractsbyStatus(supplierId , status,"token"));
	}
	
	@Test
	void YetAnotherTestGetCreatedContractsbyStatus() {
		when(supplierService.getValidity("token")).thenReturn(anothersauthResponse);
		when(contractorService.getValidity("token")).thenReturn(cauthResponse);
		List<Integer> contractIds = new ArrayList<Integer>();
		for(Contract c : contracts)
			contractIds.add(c.getId());
		when(contractRepo.findBySupplierIdAndStatus(supplierId, status)).thenReturn(contractIds);
		assertEquals(contractService.getCreatedContractsbyStatus(supplierId, status,"token"), contractIds);
		
		when(contractRepo.findBySupplierIdAndStatus(supplierId, status)).thenReturn(new ArrayList<Integer>());
		assertThrows(NoCreatedContractsException.class, ()->contractService.getCreatedContractsbyStatus(supplierId , status,"token"));
	}
	
	@Test
	void testGetContractsBySupplierId(){
		Contract contract = new Contract();
		
		Supplier supplier = new Supplier();
		supplier.setId(supplierId);
		contract.setContractType(contractType);
		contract.setContractDuration(duration);
		contract.setTermsAndConditions(tnc);
		contract.setStatus(status);
		contract.setAmenities(amenities);
		List<Contract> contracts = new ArrayList<Contract>();
		contracts.add(contract);
		when(supplierService.getValidity("token")).thenReturn(sauthResponse);
		when(contractRepo.findBySupplierId(supplierId)).thenReturn(contracts);
		List<ContractDetails> contractDetails = new ArrayList<>();
		for(Contract c : contracts) {
			ContractDetails details = new ContractDetails(c.getId(), c.getContractType(), c.getContractDuration(), c.getTermsAndConditions(), supplierId, c.getStatus(), c.getAmenities());
			contractDetails.add(details);
		}
		
		assertFalse(contractService.getContractsBySupplierId(supplierId, "token").isEmpty());
		
		when(contractRepo.findBySupplierId(supplierId)).thenReturn(new ArrayList<Contract>());
		assertThrows(NoCreatedContractsException.class, ()->contractService.getContractsBySupplierId(supplierId, "token"));
		
		when(supplierService.getValidity("token1")).thenReturn(anothersauthResponse);
		assertThrows(UnauthorizedException.class, ()->contractService.getContractsBySupplierId(supplierId, "token1"));
	}
	@Test
	void testGetContract() {
		when(supplierService.getValidity("token")).thenReturn(sauthResponse);
		ContractDetails details = new ContractDetails(contract.getId(), contract.getContractType(),contract.getContractDuration(), contract.getTermsAndConditions(), contract.getSupplier().getId(), contract.getStatus(), contract.getAmenities());
		when(contractRepo.findById(1)).thenReturn(Optional.ofNullable(contract));
		assertEquals(contractService.getContract(1,"token").toString(), details.toString());
		
		when(contractRepo.findById(1)).thenReturn(Optional.ofNullable(null));
		assertThrows(ContractNotFoundException.class, ()->contractService.getContract(1,"token"));
	}
	
	@Test
	void anotherTestGetContract() {
		when(supplierService.getValidity("token")).thenReturn(anothersauthResponse);
		when(contractorService.getValidity("token")).thenReturn(cauthResponse);

		ContractDetails details = new ContractDetails(contract.getId(), contract.getContractType(),contract.getContractDuration(), contract.getTermsAndConditions(), contract.getSupplier().getId(), contract.getStatus(), contract.getAmenities());
		when(contractRepo.findById(1)).thenReturn(Optional.ofNullable(contract));
		assertEquals(contractService.getContract(1,"token").toString(), details.toString());
		
		when(contractRepo.findById(1)).thenReturn(Optional.ofNullable(null));
		assertThrows(ContractNotFoundException.class, ()->contractService.getContract(1,"token"));
	}
	@Test
	void yetAnotherTestGetContract() {
		when(supplierService.getValidity("token")).thenReturn(anothersauthResponse);
		when(contractorService.getValidity("token")).thenReturn(anothercauthResponse);

		;
		assertThrows(UnauthorizedException.class, ()->contractService.getContract(1,"token"));
	}

	@Test
	void testGetContractsOfAllSuppliers() {
		when(contractorService.getValidity("token")).thenReturn(cauthResponse);
		when(contractRepo.findAll()).thenReturn(contracts);
		List<ContractDetails> contractDetails = new ArrayList<>();
		for(Contract c : contracts) {
			ContractDetails details = new ContractDetails(c.getId(), c.getContractType(), c.getContractDuration(), c.getTermsAndConditions(), c.getSupplier().getId(), c.getStatus(), c.getAmenities());
			contractDetails.add(details);
		}
		assertEquals(contractService.getContractsOfAllSuppliers("token").get(0).toString(),contractDetails.get(0).toString());
		
		when(contractRepo.findAll()).thenReturn(new ArrayList<Contract>());
		assertThrows(NoCreatedContractsException.class, ()->contractService.getContractsOfAllSuppliers("token"));
		
		when(contractorService.getValidity("token1")).thenReturn(anothercauthResponse);
		assertThrows(UnauthorizedException.class, ()->contractService.getContractsOfAllSuppliers("token1"));
	}
	
	@Test
	void testGetActiveContractIds() {
		List<Object[]>ids = new ArrayList<Object[]>();
		for (Contract c : contracts) {
			Object[] objects = new Object[2];
			objects[0] = c.getId();
			objects[1] = c.getStatus();
			ids.add(objects);
			
		}
		when(supplierService.getValidity("token")).thenReturn(sauthResponse);
		when(contractRepo.getIdAndStatus(supplierId, "Active")).thenReturn(ids);
		assertEquals(contractService.getActiveContractIds(supplierId, "token"), ids);
		
		when(supplierService.getValidity("token1")).thenReturn(anothersauthResponse);
		when(contractorService.getValidity("token1")).thenReturn(cauthResponse);
		assertEquals(contractService.getActiveContractIds(supplierId, "token1"), ids);
		
		when(contractRepo.getIdAndStatus(supplierId, "Active")).thenReturn(new ArrayList<Object[]>());
		assertThrows(NoCreatedContractsException.class, ()->contractService.getActiveContractIds(supplierId, "token"));
		
		}
	
	@Test
	void anotherTestGetActiveContractIds() {
		
		
		when(supplierService.getValidity("token1")).thenReturn(anothersauthResponse);
		when(contractorService.getValidity("token1")).thenReturn(anothercauthResponse);

		
		assertThrows(UnauthorizedException.class, ()->contractService.getActiveContractIds(supplierId, "token1"));
		
		}
	@Test
	void testGetActiveContractsOfALlSuppliers() {
		List<Integer>idList = new ArrayList<Integer>();
			
		List<Object[]>ids = new ArrayList<Object[]>();
		for (Contract c : contracts) {
			idList.add(c.getId());
			Object[] objects = new Object[2];
			objects[0] = c.getId();
			objects[1] = c.getStatus();
			ids.add(objects);
			
		}
		when(contractorService.getValidity("token")).thenReturn(cauthResponse);
		when(contractRepo.getIdAndStatus("Active")).thenReturn(ids);
		assertEquals(contractService.getActiveContractsOfAllSuppliers("token"), ids);
		
		when(contractRepo.getIdAndStatus("Active")).thenReturn(new ArrayList<Object[]>());
		assertThrows(NoCreatedContractsException.class, ()->contractService.getActiveContractsOfAllSuppliers("token"));
		
		when(contractorService.getValidity("token1")).thenReturn(anothercauthResponse);
		assertThrows(UnauthorizedException.class, ()->contractService.getActiveContractsOfAllSuppliers("token1"));
		
	}
	@Test
	void testGetSuppliersofActiveContracts() {
		List<String> statuses = new ArrayList<String>();
		statuses.add("Waiting For Approval");
		statuses.add("Active");
		statuses.add("Active - Delivered");
		statuses.add("Active - Not Delivered");
		Set<Supplier> suppliers = new HashSet<>();
		suppliers.add(supplier);
		
		List<SupplierUtil> supplierUtils = new ArrayList<SupplierUtil>();
		
		SupplierUtil detail = new SupplierUtil(supplier.getId(), supplier.getName(),"type", supplier.getContactNumber(), supplier.getAddress());
		supplierUtils.add(detail);
		when(contractorService.getValidity("token")).thenReturn(cauthResponse);
		when(contractRepo.findByStatuses(statuses)).thenReturn(suppliers);
		assertFalse(contractService.getSuppliersofActiveContracts(true,"token").isEmpty());
		
		when(contractRepo.findByStatuses(statuses)).thenReturn(new HashSet<>());
		assertThrows(NoCreatedContractsException.class, ()->contractService.getSuppliersofActiveContracts(true, "token"));
		
		List<String> anotherStatuses = new ArrayList<String>();
		anotherStatuses.add("Active");
		anotherStatuses.add("Active - Delivered");
		anotherStatuses.add("Active - Not Delivered");
		
		when(contractRepo.findByStatuses(anotherStatuses)).thenReturn(suppliers);
		assertFalse(contractService.getSuppliersofActiveContracts(false, "token").isEmpty());
		
		when(contractorService.getValidity("token1")).thenReturn(anothercauthResponse);
		assertThrows(UnauthorizedException.class, ()->contractService.getSuppliersofActiveContracts(true, "token1"));
		assertThrows(UnauthorizedException.class, ()->contractService.getSuppliersofActiveContracts(false, "token1"));
	}

	@Test
	void testGetAllActiveContracts() {
		List<ContractDetails> contractDetails = new ArrayList<>();
		for(Contract c : contracts) {
			ContractDetails details = new ContractDetails(c.getId(), c.getContractType(), c.getContractDuration(), c.getTermsAndConditions(), c.getSupplier().getId(), c.getStatus(), c.getAmenities());
			contractDetails.add(details);
		}
		when(contractorService.getValidity("token")).thenReturn(cauthResponse);
		when(contractRepo.findByStatusContains("Active")).thenReturn(contracts);
		assertEquals(contractService.getAllActiveContracts("token").get(0).toString(), contractDetails.get(0).toString());
		
		when(contractRepo.findByStatusContains("Active")).thenReturn(new ArrayList<Contract>());
		assertThrows(NoCreatedContractsException.class, ()->contractService.getAllActiveContracts("token"));
		
		when(contractorService.getValidity("token1")).thenReturn(anothercauthResponse);
		assertThrows(UnauthorizedException.class, ()->contractService.getAllActiveContracts("token1"));
	}

}