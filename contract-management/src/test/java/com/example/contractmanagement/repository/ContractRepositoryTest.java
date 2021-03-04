package com.example.contractmanagement.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.example.contractmanagement.model.Contract;
import com.example.contractmanagement.model.Supplier;
import com.example.contractmanagement.model.Types;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Vennelakanti
 *
 */
@TestInstance(Lifecycle.PER_CLASS)
@Slf4j
@AutoConfigureTestDatabase(replace=Replace.NONE)
@DataJpaTest
class ContractRepositoryTest {

	@Autowired
	private ContractRepository contractRepo;
	@Autowired
	private TypesRepository typesrepo;
	@Autowired
	private SupplierRepository supplierrepo;
	
	private Supplier savedSupplier;
	
	

	@BeforeAll
	@Rollback(false)
	public void setTypeAndSupplier() {
		Types type= new Types();
		type.setType("type1");
		Types savedType = typesrepo.save(type);
		
		Supplier supplier = new Supplier();
		supplier.setName("supplier");
		supplier.setPassword("supplier!");
		supplier.setContactNumber("0123456789");
		supplier.setAddress("address");
		supplier.setType(savedType);
		savedSupplier = supplierrepo.save(supplier);
		
		Contract contract = new Contract("contract type",1,"Termns and Conditions",savedSupplier,"Dummy","Amenities");
		Contract  savedContract= contractRepo.save(contract);
		assertNotNull(savedContract);
		
		Contract contract1 = new Contract("contract type",1,"Termns and Conditions",savedSupplier,"Waiting For Approval","Amenities");
		assertNotNull(contractRepo.save(contract1));
		
		Contract contract2 = new Contract("contract type",1,"Termns and Conditions",savedSupplier,"Saved","Amenities");
		assertNotNull(contractRepo.save(contract2));
		
		Contract contract3 = new Contract("contract type",1,"Termns and Conditions",savedSupplier,"Closed","Amenities");
		assertNotNull(contractRepo.save(contract3));
		
		Contract contract4 = new Contract("contract type",1,"Termns and Conditions",savedSupplier,"Active - Delivered","Amenities");
		assertNotNull(contractRepo.save(contract4));
		
		Contract contract5 = new Contract("contract type",1,"Termns and Conditions",savedSupplier,"Active - Not Delivered","Amenities");
		assertNotNull(contractRepo.save(contract5));
		
		Contract mcontract = contractRepo.findById(1).orElse(null);
		mcontract.setStatus("Active");
		Contract msavedContract = contractRepo.save(mcontract);
		assertEquals(mcontract.getId(), msavedContract.getId());
		assertEquals(msavedContract.getStatus(),"Active");
			}
	

	@Rollback(false)
	@Test
	void testFindByStatus() {
		
		
		List<Contract> contracts = contractRepo.findByStatus("Saved");
		assertFalse(contracts.isEmpty());
	}
	
	@Rollback(false)
	@Test
	void testFindBySupplierIdAndStatusContains() {
				
		List<Contract> contracts = contractRepo.findBySupplierIdAndStatusContains(savedSupplier.getId(), "Active");
		for(Contract c: contracts) {
			log.info(c.toString());
		    assertEquals(c.getSupplier().getId(), savedSupplier.getId());
		    assertTrue(c.getStatus().contains("Active"));    
		}
	}
	
	@Rollback(false)
	@Test
	void testFindBySupplierId() {
		List<Contract> contracts = contractRepo.findBySupplierId(savedSupplier.getId());
		for(Contract c: contracts) {
			log.info(c.toString());
		    assertEquals(c.getSupplier().getId(), savedSupplier.getId());
		}
	}
	
	@Rollback(false)
	@Test
	void testFindById() {
		
		assertTrue(contractRepo.findById(1).isPresent());
		
		assertFalse(contractRepo.findById(20).isPresent());
	}
	
	@Rollback(false)
	@Test
	void testFindAll() {
		
		List<Contract> contracts = contractRepo.findAll();
		assertFalse(contracts.isEmpty());
		log.info("Length is: {}",contracts.size() );
	}
//	@Test
//	@Rollback(value = false)
//	void testFindBySupplierIdAndStatus() {
//				
//		List<Integer> contracts = contractRepo.findBySupplierIdAndStatus(1, "Saved");
//		for(Integer i : contracts) {
//			log.info(i.toString());
//		}
//		assertFalse(contracts.isEmpty());
//		
//		List<Integer> contracts1 = contractRepo.findBySupplierIdAndStatus(1, "Submitte");
//		assertTrue(contracts1.isEmpty());
//	}
	
	@Rollback(false)
	@Test
	void testFindByStatuses() {
		
		List<String> statuses = new ArrayList<String>();
		statuses.add("Waiting For Approval");
		statuses.add("Active");
		statuses.add("Active - Delivered");
		statuses.add("Active - Not Delivered");
		
		Set<Supplier> suppliers = contractRepo.findByStatuses(statuses);
		
		
			
		log.info("{}",suppliers.size());	
		assertFalse(suppliers.isEmpty());
		assertEquals(suppliers.size(), 2);
	}
	
	@Rollback(false)
	@Test
	void testFindByStatusContains() {
		List<Contract> contracts = contractRepo.findByStatusContains("Active");
		assertFalse(contracts.isEmpty());
		//assertEquals(contracts.size(), 3);
	}
	
	@Rollback(false)
	@Test
	void testGetIdAndStatus() {
		List<Object[]> objects = contractRepo.getIdAndStatus(savedSupplier.getId(), "Active");
		//assertEquals(objects.size(), 3);
		assertFalse(objects.isEmpty());
		for(Object[] array: objects) {
			log.info(array[0].toString() + " "+array[1].toString());
		}
	}
	
	@Rollback(false)
	@Test
	void testAnotherGetIdAndStatus() {
		List<Object[]> objects = contractRepo.getIdAndStatus("Active");
		//assertEquals(objects.size(), 3);
		assertFalse(objects.isEmpty());
		for(Object[] array: objects) {
			log.info(array[0].toString() + " "+array[1].toString());
		}
	}
	
	@Rollback(false)
	@Test
	void testGetSuppliers() {
		List<Supplier> suppliers = contractRepo.getSuppliers("Active");
		//assertEquals(suppliers.size(), 1);
		assertFalse(suppliers.isEmpty());
		for(Supplier s : suppliers)
			log.info(s.toString());
	}
	
	
	
}
