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
import com.example.contractmanagement.model.Supplier;
import com.example.contractmanagement.model.SupplierDetails;
import com.example.contractmanagement.model.Types;
import com.example.contractmanagement.repository.SupplierRepository;
import com.example.contractmanagement.repository.TypesRepository;
import com.example.contractmanagement.util.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	private SupplierRepository supplierRepo;
	@Autowired
	private TypesRepository typeRepo;
	@Autowired
	private JwtUtil jwtutil;
	@Override
	public UserDetails loadUserByUsername(String supplierid) throws UsernameNotFoundException {
		Optional<Supplier> supplier = supplierRepo.findById(Integer.parseInt(supplierid));
		if (!supplier.isPresent()) {
			log.error("Unauthorized exception");
			throw new UnauthorizedException();
		}
		Supplier supp = supplier.get();
		ArrayList<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
		list.add(new SimpleGrantedAuthority("Supplier"));

		return new User(String.valueOf(supp.getId()), supp.getPassword(), list);
	}

	@Override
	public Map<String, String> login(Supplier supplierlogincredentials) {
		Optional<Supplier> supplier = supplierRepo.findById(supplierlogincredentials.getId());
		Supplier supp = supplier.get();
		ArrayList<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
		list.add(new SimpleGrantedAuthority("Supplier"));
		UserDetails userdetails = new User(String.valueOf(supp.getId()), supp.getPassword(), list);

//		final UserDetails userdetails = loadUserByUsername(String.valueOf(supplierlogincredentials.getId()));
		String supplierid = "";
		String generateToken = "";
		if (userdetails.getPassword().equals(supplierlogincredentials.getPassword())) {
			supplierid = String.valueOf(supplierlogincredentials.getId());

			generateToken = jwtutil.generateToken(userdetails, "Supplier");
			Supplier supplier2 = new Supplier();
			supplier2.setId(Integer.parseInt(supplierid));
			Map<String, String> map = new HashMap<>();
			map.put("id", supplierid);
			map.put("token", generateToken);
			return map;
		} else {
			log.error("Unauthorized exception");
			throw new UnauthorizedException();
		}
	}

	@Override
	public Supplier signup(SupplierDetails suppliersignupcredentials) {
		String suppliername= suppliersignupcredentials.getName();
		String supplierpassword = suppliersignupcredentials.getPassword();
		String contactnumber = suppliersignupcredentials.getContactNumber();
		String address = suppliersignupcredentials.getAddress();
		int typeId = suppliersignupcredentials.getTypeId();
		Types type = typeRepo.findById(typeId).orElse(null);
		
		Supplier supplier = new Supplier();
//		supplier.setId(Integer.parseInt(supplierid));
		supplier.setName(suppliername);
		supplier.setPassword(supplierpassword);
		supplier.setContactNumber(contactnumber);
		supplier.setAddress(address);
		supplier.setType(type);
		
		supplierRepo.save(supplier);

		return supplier;
	}

	@Override
	public AuthResponse getValidity(String token) {
		AuthResponse res = new AuthResponse();
		if (jwtutil.validateToken(token)) {
//			res.setUid(jwtutil.extractUsername(token));
			res.setUid(jwtutil.extractId(token));
			res.setValid(true);
			res.setName(supplierRepo.findById(Integer.parseInt( jwtutil.extractUsername(token))).get().getName());
			res.setRole("Supplier");
		} else {
			res.setValid(false);
			log.info("At Validity : ");
			log.info("Token Has Expired");
		}
		return res;

	}

}
