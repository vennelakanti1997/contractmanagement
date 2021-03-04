package com.example.contractmanagement.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.contractmanagement.model.SecretQuestions;


/**
 * @author Vennelakanti
 *
 */

@AutoConfigureTestDatabase(replace=Replace.NONE)
@DataJpaTest
class SecretQueastionsRepositoryTest {

	@Autowired
	private SecretQuestionsRepository secretQuestionsRepo;

    @Test
	void testFindAllQuestions() {
		SecretQuestions question = new SecretQuestions();
		question.setId(1);
		question.setQuestion("Question1");
		secretQuestionsRepo.save(question);
		assertFalse(secretQuestionsRepo.findAll().isEmpty());
	}
}