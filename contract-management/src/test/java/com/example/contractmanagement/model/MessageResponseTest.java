package com.example.contractmanagement.model;
/*
 * author : Madhurya
 * */
import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;



public class MessageResponseTest {

	MessageResponse messageResponse = new MessageResponse();

	MessageResponse message1 = new MessageResponse(new Date(), "message", "status");
	
	MessageResponse message2 = new MessageResponse("message2", "status2");
	
	@Test
	public void testTimeStamp() {
		messageResponse.setTimeStamp(new Date());
		assertEquals(messageResponse.getTimeStamp(), new Date());
	}
	
	@Test
	public void testMessage() {
		messageResponse.setMessage("Yes");
		assertEquals(messageResponse.getMessage(), "Yes");
	}
	
	@Test
	public void testStatus() {
		messageResponse.setStatus("OK");
		assertEquals(messageResponse.getStatus(), "OK");
	}
	
	
	@Test
	public void testMessageResponseConstructor() {
		
	}
	
	

	
}

