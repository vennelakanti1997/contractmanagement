package com.example.contractmanagement.repository;
/*
 * author : Madhurya
 * */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.contractmanagement.model.Contractor;

@Repository
public interface ContractorRepository extends JpaRepository<Contractor, Integer> {

}
