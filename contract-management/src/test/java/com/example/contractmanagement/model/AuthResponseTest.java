package com.example.contractmanagement.model;
/*
 * author : Madhurya
 * */
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthResponseTest {

	AuthResponse auth = new AuthResponse();
	AuthResponse auth1 = new AuthResponse("ad", "ad", true, "ad");

	@Test
	void testUid() {
		auth.setUid("Uid");
		assertEquals(auth.getUid(), "Uid");
	}

	@Test
	void testName() {
		auth.setName("Name");
		assertEquals(auth.getName(), "Name");
	}

	@Test
	void testIsValid() {
		auth.setValid(true);
		assertEquals(auth.isValid(), true);
	}

	@Test
	void testToString() {
		assertTrue(auth1.toString().contains("ad"));
	}
	
	
	@Test
	void testRole() {
		auth.setRole("user");
		assertEquals(auth.getRole(), "user");
	}

}
