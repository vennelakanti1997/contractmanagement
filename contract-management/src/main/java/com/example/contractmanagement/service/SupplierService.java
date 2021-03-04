package com.example.contractmanagement.service;
/*
 * author : Madhurya
 * */
import java.util.Map;


import org.springframework.security.core.userdetails.UserDetailsService;
import com.example.contractmanagement.model.AuthResponse;
import com.example.contractmanagement.model.Supplier;
import com.example.contractmanagement.model.SupplierDetails;

public interface SupplierService extends UserDetailsService {
	public Map<String, String> login(Supplier supplierlogincredentials);
	public Supplier signup(SupplierDetails suppliersignupcredentials);
	public AuthResponse getValidity(final String token);
}
