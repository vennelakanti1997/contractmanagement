package com.example.contractmanagement.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FeedbackTest {
	private Validator validator;
	Feedback feedback = new Feedback();
	@Test
	void testId() {
		feedback.setId(1);
		assertEquals(feedback.getId(), 1);
	}
	@Test
	void testContract() {
		Contract contract = new Contract();
		contract.setId(1);
		contract.setContractType(" Type ");
		contract.setContractDuration(1);
		contract.setTermsAndConditions(" T&C ");
		contract.setStatus("Status");
		feedback.setContract(contract);
		
		assertEquals(feedback.getContract(), contract);
	}
	@Test
	void testFeedbackText() {
		feedback.setFeedbackText("FeedbackText");
		assertEquals(feedback.getFeedbackText(), "FeedbackText");
	}
	
	@Test
	void testToString() {
		feedback.setId(1);
		feedback.setFeedbackText("FeedbackText");
		Contract contract = new Contract();
		contract.setId(1);
		contract.setContractType(" Type ");
		contract.setContractDuration(1);
		contract.setTermsAndConditions(" T&C ");
		contract.setStatus("Status");
		feedback.setContract(contract);
		String tostring="Feedback [id=1, feedbackText=FeedbackText, contract=1]";
		assertEquals(feedback.toString(), tostring);
	}
	
	@BeforeEach
    public void tncsetUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
@Test
void testtncLength() {
	String ctype="";
	for (int i = 0; i<=600; i++)
		ctype = ctype+"a";
	feedback.setFeedbackText(ctype);
	Set<ConstraintViolation<Feedback>> violations = validator.validate(feedback);
	assertFalse(violations.isEmpty());
}

}
