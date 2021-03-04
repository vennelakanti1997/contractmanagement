package com.example.contractmanagement.repository;

/*
 * author : Madhurya
 * */
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.example.contractmanagement.model.Supplier;
import com.example.contractmanagement.model.Types;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class TypesRepositoryTest {
	@Autowired
	private TypesRepository typesrepo;
	@Autowired
	private SupplierRepository supplierrepo;
	@Test
	@Rollback(false)
	void createTypesTest() {
		Types type= new Types();
		type.setId(1);
		type.setType("type1");
		
		Types savedType = typesrepo.save(type);
		
		assertNotNull(savedType);
	}
	
	@Test 
	void findSuppliersByTypeIdTest() {
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
		
		assertTrue(!typesrepo.findSuppliersByTypeId(type.getId(), supplier.getId()).isEmpty());
	}
}