
package com.example.contractmanagement.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.contractmanagement.model.Contract;
import com.example.contractmanagement.model.Supplier;
import com.example.contractmanagement.model.Types;



@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class SupplierRepositoryTest {
	@Autowired
	private ContractRepository contractRepo;
	@Autowired
	private TypesRepository typesrepo;
	@Autowired
	private SupplierRepository supplierrepo;
	
	@Test
	void createSupplierTest() {
		Types type= new Types();
		type.setId(1);
		type.setType("type1");
		Types savedType = typesrepo.save(type);
		
		Supplier supplier = new Supplier();
		supplier.setId(1);
		supplier.setName("supplier");
		supplier.setPassword("sup@pd@lier");
		supplier.setContactNumber("0123456789");
		supplier.setAddress("address");
		supplier.setType(savedType);
		Supplier savedSupplier = supplierrepo.save(supplier);
		
		assertNotNull(savedSupplier);
	}
	
	@Test
	void findByTypeIdTest() {
		Types type= new Types();
		type.setId(1);
		type.setType("type1");
		Types savedType = typesrepo.save(type);
		
		Supplier supplier = new Supplier();
		supplier.setId(1);
		supplier.setName("supplier");
		supplier.setPassword("suppl@@ier");
		supplier.setContactNumber("0123456789");
		supplier.setAddress("address");
		supplier.setType(savedType);
		supplierrepo.save(supplier);
		
		assertTrue(!supplierrepo.findByTypeId(1).isEmpty());
		
	}
	
	
	@Test
	void findAllContractsFromSupplierIdTest() {
		Types type= new Types();
		type.setId(1);
		type.setType("type1");
		Types savedType = typesrepo.save(type);
		
		Supplier supplier = new Supplier();
		supplier.setId(1);
		supplier.setName("supplier");
		supplier.setPassword("suppl@@ier");
		supplier.setContactNumber("0123456789");
		supplier.setAddress("address");
		supplier.setType(savedType);
		Supplier savedSupplier = supplierrepo.save(supplier);
		
		Contract contract = new Contract();
		contract.setId(1);
		contract.setContractType("contract type");
		contract.setContractDuration(1);
		contract.setTermsAndConditions("Termns and Conditions");
		contract.setStatus("Submitted");
		contract.setSupplier(savedSupplier);
		contract.setAmenities("Amenities");
		contractRepo.save(contract);
		
		assertTrue(!supplierrepo.findAllContractsFromSupplierId(1, 1).isEmpty());
	}
}