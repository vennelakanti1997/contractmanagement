package com.example.contractmanagement.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.contractmanagement.model.AuthResponse;
import com.example.contractmanagement.model.Contractor;
import com.example.contractmanagement.model.Supplier;
import com.example.contractmanagement.model.SupplierDetails;
import com.example.contractmanagement.service.ContractorSerivce;
import com.example.contractmanagement.service.SupplierService;
import com.example.contractmanagement.util.JwtUtil;

@ExtendWith(MockitoExtension.class)

public class AuthControllerTest {
	@InjectMocks
	AuthController authController;
	@Mock
	ContractorSerivce contractorSerivce;
	@Mock
	SupplierService supplierService;
	@Autowired
	JwtUtil jwt;

	
	@Test
	void supplierloginTest() throws Exception
	{
		Supplier supplier = new Supplier();
		supplier.setId(1);
		supplier.setPassword("supplier");
		Map<String, String> map = new HashMap<>();
		map.put("id", "1");
		map.put("token", "token");
		Mockito.when(supplierService.login(supplier)).thenReturn(map);
		assertEquals(authController.login(supplier),map);
	}
	@Test
	void adminloginTest() throws Exception {
		Contractor admin= new Contractor();
		admin.setId(1);
		admin.setPassword("adminPwd");
		Map<String, String> map = new HashMap<>();
		map.put("id", "1");
		map.put("token", "token");
		Mockito.when(contractorSerivce.login(admin)).thenReturn(map);
		assertEquals(authController.login(admin),map);
	}
	@Test
	void getValidityAdminTest() throws Exception
	{
		 AuthResponse auth = new AuthResponse("admin","admin",true, "admin");
		 when(contractorSerivce.getValidity("token")).thenReturn(auth);
		 assertEquals(authController.getAdminValidity("token"),auth);
	}
	@Test
	void getValiditySupplierTest() throws Exception
	{
		 AuthResponse auth = new AuthResponse("admin","admin",true, "admin");
		 Mockito.when(supplierService.getValidity("token")).thenReturn(auth);
		 assertEquals(authController.getSupplierValidity("token"),auth);
	}
	@Test
	void signUpTest() throws Exception {
		SupplierDetails supplierDetails = new SupplierDetails();
		supplierDetails.setName("supplier");
		supplierDetails.setPassword("supplier@Pwd");
		supplierDetails.setAddress("supplierAddress");
		supplierDetails.setContactNumber("9876543212");
		
		Supplier supplier = new Supplier();
		supplier.setId(1);
		supplier.setName("supplier");
		supplier.setPassword("supplier@Pwd");
		supplier.setAddress("supplierAddress");
		supplier.setContactNumber("9876543212");
		Mockito.when(supplierService.signup(supplierDetails)).thenReturn(supplier);
		assertEquals(authController.signUp(supplierDetails), supplier);
	}
	
}

