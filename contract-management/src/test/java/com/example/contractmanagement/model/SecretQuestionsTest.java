package com.example.contractmanagement.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Vennelakanti
 *
 */
class SecretQuestionsTest {

	SecretQuestions secretQuestions = new SecretQuestions();
	private Validator validator;
	@Test
	void testId() {
		secretQuestions.setId(1);
		assertEquals(secretQuestions.getId(), 1);
	}

	@Test
	void TestQuestion(){
		secretQuestions.setQuestion("Question");
		assertEquals(secretQuestions.getQuestion(), "Question");
	}

	@BeforeEach
    public void tncsetUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

	void testQuestionLength() {
		String ctype="";
		for (int i = 0; i<=600; i++)
			ctype = ctype+"a";
		secretQuestions.setQuestion(ctype);
		Set<ConstraintViolation<SecretQuestions>> violations = validator.validate(secretQuestions);
		assertFalse(violations.isEmpty());
	}
	@Test
	void testToString() {
		secretQuestions.setId(1);
		secretQuestions.setQuestion("Question");
		assertEquals(secretQuestions.toString(), "SecretQuestions(id=1, question=Question)");
	}
}