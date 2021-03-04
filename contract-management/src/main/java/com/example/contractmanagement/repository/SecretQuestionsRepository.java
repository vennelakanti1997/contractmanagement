package com.example.contractmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.contractmanagement.model.SecretQuestions;

/**
 * @author Vennelakanti
 *
 */
@Repository
public interface SecretQuestionsRepository extends JpaRepository<SecretQuestions, Integer> {

}