package com.example.contractmanagement.service;
/*
 * author : Madhurya
 * */
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.contractmanagement.exceptionhandling.UnauthorizedException;
import com.example.contractmanagement.model.AuthResponse;
import com.example.contractmanagement.model.Contractor;
import com.example.contractmanagement.repository.ContractorRepository;
import com.example.contractmanagement.util.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ContractorSerivceImpl implements ContractorSerivce {

	@Autowired
	private ContractorRepository contractorRepo;
	@Autowired
	private JwtUtil jwtutil;
	@Override
	public UserDetails loadUserByUsername(String adminid) throws UsernameNotFoundException {
		log.info("START");
		Optional<Contractor> admin = contractorRepo.findById(Integer.parseInt(adminid));
		if (!admin.isPresent()) {
			log.error("Unauthorized exception from lbun");
			throw new UnauthorizedException();
		}
		Contractor ad = admin.get();
		ArrayList<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
		list.add(new SimpleGrantedAuthority("Admin"));
        log.info("END");
		return new User(String.valueOf(ad.getId()), ad.getPassword(), list);
	}
	
	@Override
	public Map<String, String> login(Contractor adminlogincredentials) {
		Optional<Contractor> admin = contractorRepo.findById(adminlogincredentials.getId());
		Contractor ad = admin.get();
		ArrayList<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
		list.add(new SimpleGrantedAuthority("Admin"));
		UserDetails userdetails = new User(String.valueOf(ad.getId()), ad.getPassword(), list);
//		final UserDetails userdetails = loadUserByUsername(String.valueOf(adminlogincredentials.getId()));
		String adminid = "";
		String generateToken = "";
		if (userdetails.getPassword().equals(adminlogincredentials.getPassword())) {
			adminid = String.valueOf(adminlogincredentials.getId());			
			Contractor admin2 = new Contractor();
			admin2.setId(Integer.parseInt(adminid));
			generateToken = jwtutil.generateToken(userdetails, "Contract Admin");
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", String.valueOf(adminid));
			map.put("token", generateToken);
			return map;
		} else {
			log.error("Unauthorized Exception from login");
			throw new UnauthorizedException();
		}
	}
	@Override
	public AuthResponse getValidity(String token) {
		AuthResponse res = new AuthResponse();
		if (jwtutil.validateToken(token)) {
			res.setUid(jwtutil.extractUsername(token));
			res.setValid(true);
			res.setName(contractorRepo.findById(Integer.parseInt( jwtutil.extractUsername(token))).get().getName());
			res.setRole("Admin");
		} else {
			res.setValid(false);
			log.info("At Validity : ");
			log.info("Token Has Expired");
		}
		return res;
	}

}
