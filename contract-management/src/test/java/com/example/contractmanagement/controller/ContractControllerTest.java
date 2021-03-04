/**
 * 
 */
package com.example.contractmanagement.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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
import com.example.contractmanagement.exceptionhandling.UnauthorizedException;
import com.example.contractmanagement.model.Contract;
import com.example.contractmanagement.model.Supplier;
import com.example.contractmanagement.service.ContractServiceImpl;
import com.example.contractmanagement.util.ContractDetails;
import com.example.contractmanagement.util.SupplierUtil;



@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class ContractControllerTest {
	private String contractType ="Some Text";
	private Integer duration = 1;
	String tnc = "Some Text";
	private Integer supplierId = 1;
	private String status="Submitted";
	private String amenities="Some Text";
	
	private Contract contract;
	private List<Contract> contracts;
	
	private Supplier supplier;
	
	private ContractDetails contractDetails = new  ContractDetails(1,"contract type", 1, "Terms and COnditions", 1, "status", "amenities");
	private ContractDetails anotherContractDetails = new  ContractDetails(null,"contract type", 1, "Terms and COnditions", 1, "status", "amenities");
	
	@BeforeAll
	public void commonFunctionality() {
        Contract contract = new Contract();
		
		Supplier supplier = new Supplier();
		supplier.setId(supplierId);
		supplier.setAddress("Address");
		supplier.setContactNumber("contact");
		supplier.setName("name");
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
	}
	
	@InjectMocks
	private ContractController contractController;
	@Mock
	private ContractServiceImpl contractServiceImpl;
	/**
	 * Test method for {@link com.example.contractmanagement.controller.ContractController#createOrModifyContract(com.example.contractmanagement.util.ContractDetails)}.
	 */
	@Test
	void testCreateOrModifyContract() {
        when(contractServiceImpl.createOrModifyContract(contractDetails, "token")).thenReturn("Contract saved Successfully. ContractId is: 1" );
        assertEquals(contractController.createOrModifyContract(contractDetails, "token").getBody(), "Contract saved Successfully. ContractId is: 1" );
        
        when(contractServiceImpl.createOrModifyContract(anotherContractDetails, "token")).thenThrow(ContractCreationFailedException.class);
        assertThrows(ContractCreationFailedException.class, ()->contractController.createOrModifyContract(anotherContractDetails, "token"));
        when(contractServiceImpl.createOrModifyContract(anotherContractDetails, "token1")).thenThrow(UnauthorizedException.class);
        assertThrows(UnauthorizedException.class, ()->contractController.createOrModifyContract(anotherContractDetails, "token1"));
	}

	/**
	 * Test method for {@link com.example.contractmanagement.controller.ContractController#getCreatedContractsbyStatus(java.lang.Integer, java.lang.String)}.
	 */
	@Test
	void testGetCreatedContractsbyStatus() {
		List<Integer> contractIds = new ArrayList<Integer>();
		for(Contract c : contracts)
			contractIds.add(c.getId());
		when(contractServiceImpl.getCreatedContractsbyStatus(supplierId, status,"token")).thenReturn(contractIds);
		assertEquals(contractController.getCreatedContractsbyStatus(supplierId, status,"token").getBody(), contractIds);
		
		when(contractServiceImpl.getCreatedContractsbyStatus(supplierId, status,"token")).thenThrow(NoCreatedContractsException.class);
		assertThrows(NoCreatedContractsException.class, ()->contractController.getCreatedContractsbyStatus(supplierId, status,"token"));
		
		when(contractServiceImpl.getCreatedContractsbyStatus(supplierId, status,"token1")).thenThrow(UnauthorizedException.class);
		assertThrows(UnauthorizedException.class, ()->contractController.getCreatedContractsbyStatus(supplierId, status,"token1"));
		
	}

	/**
	 * Test method for {@link com.example.contractmanagement.controller.ContractController#getContractsBySupplierId(java.lang.Integer)}.
	 */
	@Test
	void testGetContractsBySupplierId() {
		List<ContractDetails> contractDetails = new ArrayList<>();
		for(Contract c : contracts) {
			ContractDetails details = new ContractDetails(c.getId(), c.getContractType(), c.getContractDuration(), c.getTermsAndConditions(), supplierId, c.getStatus(), c.getAmenities());
			contractDetails.add(details);
		}
		when(contractServiceImpl.getContractsBySupplierId(supplierId, "token")).thenReturn(contractDetails);
		assertEquals(contractController.getContractsBySupplierId(supplierId,"token").getBody(), contractDetails);
		
		when(contractServiceImpl.getContractsBySupplierId(supplierId,"token")).thenThrow(NoCreatedContractsException.class);
		assertThrows(NoCreatedContractsException.class, ()->contractController.getContractsBySupplierId(supplierId,"token"));
		
		when(contractServiceImpl.getContractsBySupplierId(supplierId,"token1")).thenThrow(UnauthorizedException.class);
		assertThrows(UnauthorizedException.class, ()->contractController.getContractsBySupplierId(supplierId,"token1"));
	}

	/**
	 * Test method for {@link com.example.contractmanagement.controller.ContractController#getContract(java.lang.Integer)}.
	 */
	@Test
	void testGetContract() {
		ContractDetails details = new ContractDetails(contract.getId(), contract.getContractType(),contract.getContractDuration(), contract.getTermsAndConditions(), contract.getSupplier().getId(), contract.getStatus(), contract.getAmenities());
		when(contractServiceImpl.getContract(1,"token")).thenReturn(details);
		assertEquals(contractController.getContract(1,"token").getBody(), details);
		
		when(contractServiceImpl.getContract(1,"token")).thenThrow(ContractNotFoundException.class);
		assertThrows(ContractNotFoundException.class, ()->contractController.getContract(1,"token"));
		
		when(contractServiceImpl.getContract(1,"token1")).thenThrow(UnauthorizedException.class);
		assertThrows(UnauthorizedException.class, ()->contractController.getContract(1,"token1"));
	}

	/**
	 * Test method for {@link com.example.contractmanagement.controller.ContractController#getContractsOfAllSuppliers()}.
	 */
	@Test
	void testGetContractsOfAllSuppliers() {
		List<ContractDetails> contractDetails = new ArrayList<>();
		for(Contract c : contracts) {
			ContractDetails details = new ContractDetails(c.getId(), c.getContractType(), c.getContractDuration(), c.getTermsAndConditions(), c.getSupplier().getId(), c.getStatus(), c.getAmenities());
			contractDetails.add(details);
		}
		when(contractServiceImpl.getContractsOfAllSuppliers("token")).thenReturn(contractDetails);
		assertEquals(contractController.getContractsOfAllSuppliers("token").getBody(), contractDetails);
		
		when(contractServiceImpl.getContractsOfAllSuppliers("token")).thenThrow(ContractNotFoundException.class);
		assertThrows(ContractNotFoundException.class, ()->contractController.getContractsOfAllSuppliers("token"));
		
		when(contractServiceImpl.getContractsOfAllSuppliers("token1")).thenThrow(UnauthorizedException.class);
		assertThrows(UnauthorizedException.class, ()->contractController.getContractsOfAllSuppliers("token1"));
	}

	/**
	 * Test method for {@link com.example.contractmanagement.controller.ContractController#getActiveContractsOfAllSuppliers()}.
	 */
	@Test
	void testGetActiveContractsOfAllSuppliers() {
		List<Integer>idList = new ArrayList<Integer>();
		
		List<Object[]>ids = new ArrayList<Object[]>();
		for (Contract c : contracts) {
			idList.add(c.getId());
			Object[] objects = new Object[2];
			objects[0] = c.getId();
			objects[1] = c.getStatus();
			ids.add(objects);
			
		}
		when(contractServiceImpl.getActiveContractsOfAllSuppliers("token")).thenReturn(ids);
		assertEquals(contractController.getActiveContractsOfAllSuppliers("token").getBody(), ids);
		
		when(contractServiceImpl.getActiveContractsOfAllSuppliers("token")).thenThrow(NoCreatedContractsException.class);
		assertThrows(NoCreatedContractsException.class, ()->contractController.getActiveContractsOfAllSuppliers("token"));
		
		when(contractServiceImpl.getActiveContractsOfAllSuppliers("token1")).thenThrow(UnauthorizedException.class);
		assertThrows(UnauthorizedException.class, ()->contractController.getActiveContractsOfAllSuppliers("token1"));
		
	}

	/**
	 * Test method for {@link com.example.contractmanagement.controller.ContractController#getAllActiveContracts()}.
	 */
	@Test
	void testGetAllActiveContracts() {
		List<ContractDetails> contractDetails = new ArrayList<>();
		for(Contract c : contracts) {
			ContractDetails details = new ContractDetails(c.getId(), c.getContractType(), c.getContractDuration(), c.getTermsAndConditions(), c.getSupplier().getId(), c.getStatus(), c.getAmenities());
			contractDetails.add(details);
		}
		when(contractServiceImpl.getAllActiveContracts("token")).thenReturn(contractDetails);
		assertEquals(contractController.getAllActiveContracts("token").getBody(), contractDetails);
		
		when(contractServiceImpl.getAllActiveContracts("token")).thenThrow(NoCreatedContractsException.class);
	    assertThrows(NoCreatedContractsException.class, ()->contractController.getAllActiveContracts("token"));
	
	when(contractServiceImpl.getAllActiveContracts("token1")).thenThrow(UnauthorizedException.class);
	assertThrows(UnauthorizedException.class, ()->contractController.getAllActiveContracts("token1"));
	}

	/**
	 * Test method for {@link com.example.contractmanagement.controller.ContractController#getActiveContractIds(java.lang.Integer)}.
	 */
	@Test
	void testGetActiveContractIds() {
		List<Object[]>ids = new ArrayList<Object[]>();
		for (Contract c : contracts) {
			Object[] objects = new Object[2];
			objects[0] = c.getId();
			objects[1] = c.getStatus();
			ids.add(objects);
			
		}
		
		when(contractServiceImpl.getActiveContractIds(supplierId,"token")).thenReturn(ids);
		assertEquals(contractController.getActiveContractIds(supplierId,"token").getBody(),ids);
		
		when(contractServiceImpl.getActiveContractIds(supplierId,"token")).thenThrow(NoCreatedContractsException.class);
		assertThrows(NoCreatedContractsException.class,()->contractController.getActiveContractIds(supplierId,"token"));
		
		when(contractServiceImpl.getActiveContractIds(supplierId,"token1")).thenThrow(UnauthorizedException.class);
		assertThrows(UnauthorizedException.class,()->contractController.getActiveContractIds(supplierId,"token1"));
	}

	/**
	 * Test method for {@link com.example.contractmanagement.controller.ContractController#getSuppliersofActiveContracts()}.
	 */
	@Test
	void testGetSuppliersofActiveContracts() {
	List<SupplierUtil> suppliers = new ArrayList<SupplierUtil>();
	
		SupplierUtil detail = new SupplierUtil(supplier.getId(), supplier.getName(),"type", supplier.getContactNumber(), supplier.getAddress());
		suppliers.add(detail);
	
	when(contractServiceImpl.getSuppliersofActiveContracts(false,"token")).thenReturn(suppliers);
	assertFalse(contractController.getSuppliersofActiveContracts("token").getBody().isEmpty());

	when(contractServiceImpl.getSuppliersofActiveContracts(false,"token")).thenThrow(NoCreatedContractsException.class);
	assertThrows(NoCreatedContractsException.class, ()->contractServiceImpl.getSuppliersofActiveContracts(false,"token"));
	
	when(contractServiceImpl.getSuppliersofActiveContracts(false,"token1")).thenThrow(UnauthorizedException.class);
	assertThrows(UnauthorizedException.class, ()->contractServiceImpl.getSuppliersofActiveContracts(false,"token1"));
	}

}
