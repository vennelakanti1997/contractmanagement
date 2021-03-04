package com.example.contractmanagement.repository;
/*
 * author : Madhurya
 * */
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.contractmanagement.model.Supplier;
import com.example.contractmanagement.model.Types;
@Repository
public interface TypesRepository extends JpaRepository<Types, Integer> {
	@Query("from Supplier s INNER JOIN s.type t WHERE t.id=?1 and s.id=?2")
	public List<Supplier> findSuppliersByTypeId(int typeId, int supplierId);
}
