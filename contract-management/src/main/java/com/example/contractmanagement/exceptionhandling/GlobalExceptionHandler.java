package com.example.contractmanagement.exceptionhandling;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.contractmanagement.model.MessageResponse;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
//	@Override
//	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//			HttpHeaders headers, HttpStatus status, WebRequest request) {
//		
//        Map<String, Object> body = new LinkedHashMap<String, Object>();
//        body.put("timestamp", new Date());
//        body.put("status", status.value());
//        //Get all errors
//        List<String> errors = ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(x -> x.getDefaultMessage())
//                .collect(Collectors.toList());
//        
//        body.put("errors", errors);
//       
//        return new ResponseEntity<>(body, headers, status);
//	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(SupplierNotFoundException.class)
	public ResponseEntity<?> handleListEmptyException(SupplierNotFoundException ex){
		return ResponseEntity.badRequest().body(new MessageResponse("Supplier Not Found.","SupplierNotFound"));
	}
	

	@ExceptionHandler(NoCreatedContractsException.class)
	public ResponseEntity<?> handleNoCreatedContractsException(NoCreatedContractsException ex){
		return ResponseEntity.ok().body(new MessageResponse("No Contracts.", "NoSuchContracts"));
	}
	
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<?> handleUnauthorizedExceptions(UnauthorizedException ex) {
		return ResponseEntity.badRequest().body(new MessageResponse("Unauthorized request. Login again...","Unauthorized"));
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingRequestHeaderException.class)
	public ResponseEntity<?> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {

		log.error("Require Bearer token");
		return ResponseEntity.badRequest().body(new MessageResponse("Required Bearer token", "Bad Request"));
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ContractNotFoundException.class)
	public ResponseEntity<?> handleContractorNotFoundException(ContractNotFoundException ex) {
		return ResponseEntity.badRequest().body(new MessageResponse("Contract Not Found", "ContractNotFound"));
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(PasswordInvalidException.class)
	public ResponseEntity<?> handlePasswordInvalidityException(PasswordInvalidException ex) {

		log.error("Password is Invalid");
		return ResponseEntity.badRequest().body(new MessageResponse("Password must include atleast one special character", "Invalid Password"));
	}
	
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ProposalsEmptyException.class)
	public ResponseEntity<?> handleProposalEmptyException(ProposalsEmptyException ex) {
		return ResponseEntity.badRequest().body(new MessageResponse("Proposals Not Found", "ProposalsNotFound"));
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ProposalNotFoundException.class)
	public ResponseEntity<?> handleProposalNotFoundException(ProposalNotFoundException ex) {
		return ResponseEntity.badRequest().body(new MessageResponse("Proposals Not Found", "ProposalsNotFound"));
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(RequirementsEmptyException.class)
	public ResponseEntity<?> handleRequirementEmptyException(RequirementsEmptyException ex) {
		return ResponseEntity.badRequest().body(new MessageResponse("Requirements Not Found", "RequirementsNotFound"));
	}
	
	
	
}
