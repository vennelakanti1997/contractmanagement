package com.example.contractmanagement.controller;
/*
 * author : Madhurya
 * */
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.contractmanagement.model.AuthResponse;
import com.example.contractmanagement.model.Contractor;
import com.example.contractmanagement.model.Supplier;
import com.example.contractmanagement.model.SupplierDetails;
import com.example.contractmanagement.service.ContractorSerivce;
import com.example.contractmanagement.service.SupplierService;

@RequestMapping("/auth")
@RestController
@CrossOrigin
public class AuthController {

	@Autowired
	private SupplierService supplierDetailsService;
	
	@Autowired
	private ContractorSerivce contractAdminDetailsService;


	@RequestMapping(value = "/supplierlogin", method = RequestMethod.POST)
	public Map<String, String> login(@Valid @RequestBody Supplier supplierlogincredentials) {
		return supplierDetailsService.login(supplierlogincredentials);
	}
	
	@RequestMapping(value = "/adminlogin", method = RequestMethod.POST)
	public Map<String, String> login(@Valid @RequestBody Contractor adminlogincredentials) {
		return contractAdminDetailsService.login(adminlogincredentials);
	}

	@RequestMapping(value = "/suppliervalidate", method = RequestMethod.GET)
	public AuthResponse getSupplierValidity(@Valid @RequestHeader("Authorization") final String token) {
		return supplierDetailsService.getValidity(token);
	}
	
	@RequestMapping(value = "/adminvalidate", method = RequestMethod.GET)
	public AuthResponse getAdminValidity(@RequestHeader("Authorization") final String token) {
		return contractAdminDetailsService.getValidity(token);
	}

	@RequestMapping(value="/signup", method = RequestMethod.POST)
	public Supplier signUp(@Valid @RequestBody SupplierDetails supplierlogincredentials) {
		return supplierDetailsService.signup(supplierlogincredentials);
	}
	

}
