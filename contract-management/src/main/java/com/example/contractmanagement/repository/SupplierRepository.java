package com.example.contractmanagement.repository;
/*
 * author : Madhurya
 * */
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.contractmanagement.model.Contract;
import com.example.contractmanagement.model.Supplier;
@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
	@Query("from Supplier s where s.type.id=:id")
	public List<Supplier> findByTypeId(@Param("id") int id);
	
	@Query("from Contract c INNER JOIN c.supplier s WHERE s.id=?1 and c.id=?2")
	public List<Contract> findAllContractsFromSupplierId(int supplierId, int contractId);
}