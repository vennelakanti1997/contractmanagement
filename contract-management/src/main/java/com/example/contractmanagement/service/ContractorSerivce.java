package com.example.contractmanagement.service;

/*
 * author : Madhurya
 * */
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetailsService;
import com.example.contractmanagement.model.AuthResponse;
import com.example.contractmanagement.model.Contractor;

public interface ContractorSerivce extends UserDetailsService{
	public Map<String, String> login(Contractor adminlogincredentials);
	public AuthResponse getValidity( final String token);
}
