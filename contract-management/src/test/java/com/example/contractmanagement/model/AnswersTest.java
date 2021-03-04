package com.example.contractmanagement.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AnswersTest {

	Answers ans = new Answers();
	SecretQuestions ques = new SecretQuestions();
	Supplier supp = new Supplier();
	Contractor admin= new Contractor();

	@Test
	void idTest() {
		ans.setId(1);
		assertEquals(ans.getId(), 1);
	}
	@Test
	void nameTest() {
		ans.setAnswer("Yes");
		assertEquals(ans.getAnswer(), "Yes");
	}

	@Test
	void questionTest() {
		ques.setId(1);
		ques.setQuestion("Question");
		ans.setQuestions(ques);
		assertEquals(ans.getQuestions().getId(), 1);
	}
	@Test
	void supplierTest() {
		supp.setId(1);
		supp.setName("supplier");
		ans.setSupplier(supp);
		assertEquals(ans.getSupplier().getId(),1);
	}
	@Test
	void contractorTest() {
		admin.setId(1);
		admin.setName("admin");
		ans.setContractor(admin);
		assertEquals(ans.getContractor().getId(), 1);
	}
	@Test
	void toStringTest() {
		ans.setId(1);
		ans.setAnswer("Yes");
		admin.setId(1);
		supp.setId(1);
		ques.setId(1);
		ans.setContractor(admin);
		ans.setSupplier(supp);
		ans.setQuestions(ques);
		assertTrue(ans.toString().contains("Yes"));
	}
}