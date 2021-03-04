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
import com.example.contractmanagement.model.Supplier;
import com.example.contractmanagement.model.SupplierDetails;
import com.example.contractmanagement.model.Types;
import com.example.contractmanagement.repository.SupplierRepository;
import com.example.contractmanagement.repository.TypesRepository;
import com.example.contractmanagement.util.JwtUtil;


@ExtendWith(MockitoExtension.class)
public class SupplierServiceImplTest {
	@InjectMocks
	private SupplierServiceImpl service;
	@Mock
	private SupplierRepository repo;
	@Mock
	private TypesRepository typerepo;
	@Mock
	private JwtUtil jwt;
	

	@Test
	public void testLoadUserByUsername() {

		Types type = new Types();
		type.setId(1);
		type.setType("type1");
		Supplier supplier = new Supplier();
		supplier.setId(1);
		supplier.setName("supplier");
		supplier.setPassword("supplier@supplier");
		supplier.setAddress("supplier");
		supplier.setType(type);
		
		List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
		list.add(new SimpleGrantedAuthority("Supplier"));
		UserDetails userdetails = new User("1", "supplier@supplier", list);
		Optional<Supplier> data = Optional.of(supplier);
		Mockito.lenient().when(repo.findById(1)).thenReturn(data);
		assertEquals(service.loadUserByUsername("1").toString(), userdetails.toString());
	}
	
	@Test
	public void testLoadUserByUsernameFailed() {
		Types type = new Types();
		type.setId(1);
		type.setType("type1");
		Supplier supplier = new Supplier();
		supplier.setId(1);
		supplier.setName("supplier");
		supplier.setPassword("supplier@supplier");
		supplier.setAddress("supplier");
		supplier.setType(type);
		Optional<Supplier> data = Optional.of(supplier);
		assertThrows(UnauthorizedException.class, ()->service.loadUserByUsername("1"));
		
		
	}
	
	@Test
	public void testLogin() {
		Types type = new Types();
		type.setId(1);
		type.setType("type1");
		Supplier supplier = new Supplier();
		supplier.setId(1);
		supplier.setName("supplier");
		supplier.setPassword("supplier@supplier");
		supplier.setAddress("supplier");
		supplier.setType(type);		
		Optional<Supplier> data = Optional.of(supplier);
		Mockito.lenient().when(repo.findById(1)).thenReturn(data);
		
		List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
		list.add(new SimpleGrantedAuthority("Supplier"));
		UserDetails userDetails = new User("supplier", "supplier@supplier", list);		
		Mockito.lenient().when(jwt.generateToken(userDetails, "Supplier")).thenReturn("token");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "1");
		map.put("token", "token");
		Mockito.lenient().when(service.login(supplier).get("id")).thenReturn("1");
		assertEquals(service.login(supplier).get("id"),"1");
	}
	
	@Test
	public void testLoginFailed() {
		
		Types type = new Types();
		type.setId(1);
		type.setType("type1");
		Supplier supplier = new Supplier();
		supplier.setId(1);
		supplier.setName("supplier");
		supplier.setPassword("supplier@supplier");
		supplier.setAddress("supplier");
		supplier.setType(type);		
		Optional<Supplier> data = Optional.of(supplier);
		Mockito.lenient().when(repo.findById(1)).thenReturn(data);
		
		
		Types type2 = new Types();
		type2.setId(1);
		type2.setType("type1");
		Supplier supplier2 = new Supplier();
		supplier2.setId(1);
		supplier2.setName("supplier");
		supplier2.setPassword("supplier@@supplier");
		supplier2.setAddress("supplier");
		supplier2.setType(type);
		
		assertThrows(UnauthorizedException.class, ()->service.login(supplier2));
	}
	
	@Test
	public void testValidity() {
		Types type = new Types();
		type.setId(1);
		type.setType("type1");
		Supplier supplier = new Supplier();
		supplier.setId(1);
		supplier.setName("supplier");
		supplier.setPassword("supplier@supplier");
		supplier.setAddress("supplier");
		supplier.setType(type);
		
		Optional<Supplier> data = Optional.of(supplier);
		AuthResponse res = new AuthResponse("supplier", "supplier", true, "Supplier");
		Mockito.lenient().when(jwt.validateToken("token")).thenReturn(true);
		AuthResponse auth = new AuthResponse("1","admin",true, "Admin");
		Mockito.lenient().when(jwt.validateToken("token")).thenReturn(auth.isValid());
		AuthResponse auth2 = new AuthResponse();
		auth2.setUid("1");
		auth2.setName("admin");
		auth2.setRole("Admin");
		auth2.setValid(true);
		Mockito.lenient().when(repo.findById(1)).thenReturn(data);
//		when(service.getValidity("1")).thenReturn(res);
	}
	@Test
	void testValidityFailed()
	{
		 when(jwt.validateToken("token")).thenReturn(false);
		 AuthResponse auth = new AuthResponse(null,null,false,null);
		 assertEquals(service.getValidity("token").isValid(),auth.isValid()); 
	}
	
	@Test
	void testSignUpSuccess() {
		Types type = new Types();
		type.setId(1);
		type.setType("type1");
		Supplier supplier = new Supplier();
		supplier.setId(1);
		supplier.setName("supplier");
		supplier.setPassword("supplier@supplier");
		supplier.setContactNumber("9876543212");
		supplier.setAddress("supplier");
		supplier.setType(type);
		SupplierDetails suppDetails = new SupplierDetails("supplier", "supplier@supplier", "9876543212",
				"supplier", 1);
		Optional<Supplier> dataSupp = Optional.of(supplier);
		Optional<Types> dataType = Optional.of(type);
		Mockito.lenient().when(typerepo.findById(1)).thenReturn(dataType);
		Mockito.lenient().when(repo.findById(1)).thenReturn(dataSupp);
		Mockito.lenient().when(service.signup(suppDetails)).thenReturn(supplier);
		assertEquals(service.signup(suppDetails).getName(), supplier.getName());
	}
	

}


