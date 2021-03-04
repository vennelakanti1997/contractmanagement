package com.example.contractmanagement.repository;

import java.util.List;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.contractmanagement.model.Contract;
import com.example.contractmanagement.model.Supplier;
@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer> {

	List<Contract> findByStatusContains(String status);
	List<Contract> findByStatus(String status);
	
	@Query("SELECT c.supplier FROM Contract c WHERE c.status IN :status")
	Set<Supplier> findByStatuses(List<String> status);
	
	@Query("FROM Contract c WHERE c.supplier.id = :id AND c.status LIKE :status% ")
	List<Contract> findBySupplierIdAndStatusContains(@Param("id") Integer id, @Param("status") String status);
	
	@Query("FROM Contract c WHERE c.supplier.id = :id ")
	List<Contract> findBySupplierId(@Param("id") Integer id);
	
	//Returns Contract ids of the waiting for approval/closed contract ids of the given supplier
	@Query("SELECT c.id FROM Contract c WHERE c.supplier.id = :id AND c.status= :status")
	List<Integer> findBySupplierIdAndStatus(@Param("id") Integer id, @Param("status") String status);

	//Returns Contract ids and status of the active contract ids of the given supplier
	@Query("SELECT c.id, c.status FROM Contract c WHERE c.supplier.id = :id AND c.status LIKE :status% ")
	List<Object[]> getIdAndStatus(@Param("id") Integer id, @Param("status") String status);

	//Returns All Contract ids and status of the active contract ids
	@Query("SELECT c.id, c.status FROM Contract c WHERE c.status LIKE :status% ")
	List<Object[]> getIdAndStatus(@Param("status") String status);
	
	@Query("SELECT DISTINCT(c.supplier) FROM Contract c WHERE c.status LIKE :status%")
	List<Supplier> getSuppliers(@Param("status") String status);
	
}
