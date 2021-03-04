package com.example.contractmanagement.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.contractmanagement.model.Proposal;
import com.example.contractmanagement.model.Supplier;
@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Integer> {
	List<Proposal> findAllByStatus(String status);
	List<Proposal> findAllBySupplier(Supplier supplier);
}
