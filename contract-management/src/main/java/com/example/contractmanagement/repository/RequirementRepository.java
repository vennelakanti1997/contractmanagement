package com.example.contractmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.contractmanagement.model.Requirement;
import com.example.contractmanagement.model.Types;
@Repository
public interface RequirementRepository extends JpaRepository<Requirement, Integer> {
	List<Requirement> findAllByType(Types type);
}
