package com.example.contractmanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.contractmanagement.exceptionhandling.UnauthorizedException;
import com.example.contractmanagement.model.AuthResponse;
import com.example.contractmanagement.model.Contractor;
import com.example.contractmanagement.repository.ContractorRepository;
import com.example.contractmanagement.util.JwtUtil;


@ExtendWith(MockitoExtension.class)
public class ContractorServiceImplTest {
	@InjectMocks
	private ContractorSerivceImpl contractorSerivceImpl;
	@Mock
	private ContractorRepository repo;
	@Mock
	private JwtUtil jwtutil;
	
	
	Map<String , String> map = new HashMap<String, String>();

	@Test
	void testGetValidity()
	{
		Contractor admin = new Contractor();
		admin.setId(1);
		admin.setName("admin");
		admin.setPassword("admin123");
		
		Optional<Contractor> data = Optional.of(admin);
		Mockito.lenient().when(jwtutil.validateToken("token")).thenReturn(true);
		
		AuthResponse auth = new AuthResponse("1","admin",true, "Admin");
		Mockito.lenient().when(jwtutil.validateToken("token")).thenReturn(auth.isValid());
		List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
		list.add(new SimpleGrantedAuthority("Admin"));
		UserDetails userDetails = new User("admin", "admin", list);
		String token = jwtutil.generateToken(userDetails, "Admin");
		AuthResponse auth2 = new AuthResponse();
		auth2.setUid(jwtutil.extractUsername(token));
		auth2.setName(data.get().getName());
		auth2.setRole("Admin");
		auth2.setValid(true);
	}
	@Test
	void getValidityFailedTest()
	{
		 when(jwtutil.validateToken("token")).thenReturn(false);
		 AuthResponse auth = new AuthResponse(null,null,false,null);
		 assertEquals(contractorSerivceImpl.getValidity("token").isValid(),auth.isValid()); 
	}
	
	@Test
	public void testLoadUserByUsername() {
		
		Contractor admin = new Contractor();
		admin.setId(1);
		admin.setName("admin");
		admin.setPassword("admin123");
		
		List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
		list.add(new SimpleGrantedAuthority("Admin"));
		UserDetails userdetails = new User("1", "admin123", list);
		Optional<Contractor> data = Optional.of(admin);
		Mockito.lenient().when(repo.findById(1)).thenReturn(data);
		assertEquals(contractorSerivceImpl.loadUserByUsername("1").toString(), userdetails.toString());
	}
	@Test
	void loadUserByUsernameFailedTest()
	{
		assertThrows(UnauthorizedException.class,()->contractorSerivceImpl.loadUserByUsername("1"));
	}
	
	@Test
	void loginTest()
	{
		Contractor admin = new Contractor();
		admin.setId(1);
		admin.setName("admin");
		admin.setPassword("admin123");		
		Optional<Contractor> data = Optional.of(admin);
		Mockito.lenient().when(repo.findById(1)).thenReturn(data);
		
		List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
		list.add(new SimpleGrantedAuthority("Admin"));
		UserDetails userDetails = new User("admin", "admin123", list);		
		Mockito.lenient().when(jwtutil.generateToken(userDetails, "Admin")).thenReturn("token");
		
		map.put("id", "1");
		map.put("token", "token");
		Mockito.lenient().when(contractorSerivceImpl.login(admin).get("id")).thenReturn("1");
		assertEquals(contractorSerivceImpl.login(admin).get("id"),"1");
	}
	@Test
	void loginFailedTest()
	{
		Contractor admin = new Contractor();
		admin.setId(1);
		admin.setName("admin");
		admin.setPassword("admin123");
		Optional<Contractor> data = Optional.of(admin);
		Mockito.lenient().when(repo.findById(1)).thenReturn(data);
		
		Contractor admin2 = new Contractor();
		admin2.setId(1);
		admin2.setName("admin");
		admin2.setPassword("admin12345");
		assertThrows(UnauthorizedException.class, ()->contractorSerivceImpl.login(admin2));
		
	}
}
