package com.example.contractmanagement.exceptionhandling;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.MissingRequestHeaderException;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {
	@InjectMocks
	GlobalExceptionHandler globalExceptionHandler;
	
	@Test
	public void handleListEmptyExceptionTest() {
		assertEquals(globalExceptionHandler.handleListEmptyException(new SupplierNotFoundException()).getStatusCodeValue(), 400);
	}
	@Test
	public void handleNoCreatedContractsExceptionTest() {
		assertEquals(globalExceptionHandler.handleNoCreatedContractsException(new NoCreatedContractsException()).getStatusCodeValue(), 200);
	}
	@Test
	public void unauthorizedExceptionTest() {
		assertEquals(globalExceptionHandler.handleUnauthorizedExceptions(new UnauthorizedException()).getStatusCodeValue(), 400);
	}
	@Test
	public void handleMissingRequestHeaderExceptionTest() {
		assertEquals(globalExceptionHandler.handleMissingRequestHeaderException(new MissingRequestHeaderException(null, null)).getStatusCodeValue(), 400);
	}
	@Test
	public void handleContractorNotFoundExceptionTest() {
		assertEquals(globalExceptionHandler.handleContractorNotFoundException(new ContractNotFoundException()).getStatusCodeValue(), 400);
	}
	@Test
	public void handlePasswordInvalidityExceptionTest() {
		assertEquals(globalExceptionHandler.handlePasswordInvalidityException(new PasswordInvalidException()).getStatusCodeValue(), 400);
	}
	@Test
	public void handleProposalEmptyExceptionTest() {
		assertEquals(globalExceptionHandler.handleProposalEmptyException(new ProposalsEmptyException()).getStatusCodeValue(), 400 );
	}
	@Test
	public void handleProposalNotFoundExceptionTest() {
		assertEquals(globalExceptionHandler.handleProposalNotFoundException(new ProposalNotFoundException()).getStatusCodeValue(), 400);
	}
	@Test
	public void handleRequirementEmptyExceptionTest() {
		assertEquals(globalExceptionHandler.handleRequirementEmptyException(new RequirementsEmptyException()).getStatusCodeValue(), 400);
	}
}
