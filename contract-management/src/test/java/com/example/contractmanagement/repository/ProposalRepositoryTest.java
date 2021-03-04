package com.example.contractmanagement.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.example.contractmanagement.model.Proposal;
import com.example.contractmanagement.model.Requirement;
import com.example.contractmanagement.model.Supplier;
import com.example.contractmanagement.util.DateUtil;

@DataJpaTest
@AutoConfigureTestDatabase(replace= Replace.NONE)
class ProposalRepositoryTest {

	
	@Autowired
	private ProposalRepository repo;
	
	
	private Proposal getProposal() {
		Proposal proposal =new Proposal();
		proposal.setStatus("Submitted");
		proposal.setProposalDate(DateUtil.convertToDate("2022-11-11"));
		Requirement requirement =new Requirement();
		proposal.setRequirement(requirement);
		Supplier supplier =new Supplier();
		supplier.setName("supplier1");
		supplier.setPassword("Password@0");
		proposal.setSupplier(supplier);
		BigDecimal bd =  new BigDecimal("17890.091");
		proposal.setQuotation(bd);
		return proposal;
	}
	
	@Test
	void testCreateProposal() {
		Proposal proposal =getProposal();
		Proposal savedProposal = repo.save(proposal);
		assertNotNull(savedProposal);
	}
	
	@Test
	void testViewProposalById() {
		Proposal proposal =getProposal();
		Proposal savedProposal =repo.save(proposal);
		Proposal retrievedProposal = repo.findById(proposal.getId()).orElse(null);
		assertThat(retrievedProposal).isEqualTo(savedProposal);
	}
	
	
	@Test
	void testViewProposalsByStatus() {
		Proposal proposal=getProposal();
		repo.save(proposal);
		List<Proposal> retrievedProposals = repo.findAllByStatus(proposal.getStatus());
		assertThat(retrievedProposals).contains(proposal);
	}
	
	@Test
	void testViewAllProposals() {
		Proposal proposal=getProposal();
		repo.save(proposal);
		List<Proposal> retrievedProposals = repo.findAll();
		assertThat(retrievedProposals).contains(proposal);
	}
	
	
	@Test
	void testUpdateProposal() {
		Proposal proposal=getProposal();
		repo.save(proposal);
		Proposal proposal0=new Proposal();
		proposal0 = proposal;
		proposal0.setStatus("To be revisited");
		Proposal updatedProposal=repo.save(proposal0);
		Proposal retrievedProposal = repo.findById(proposal.getId()).orElse(null);
		assertThat(retrievedProposal).isEqualTo(updatedProposal);
		
		}
	
	@Test
	void testDeleteProposal() {
		Proposal proposal =getProposal();
		repo.save(proposal);
		boolean existsBeforeDel =repo.findById(proposal.getId()).isPresent();
		repo.delete(proposal);
		boolean existAfterDel =repo.findById(proposal.getId()).isPresent();
		assertTrue(existsBeforeDel);
		assertFalse(existAfterDel);
	}
	
	@Test
	void testViewProposalsBySupplier() {
		Proposal proposal =getProposal();
		Proposal savedProposal =repo.save(proposal);
		List<Proposal> retrievedProposals = repo.findAllBySupplier(proposal.getSupplier());
		assertThat(retrievedProposals).contains(savedProposal);
		
	}
}