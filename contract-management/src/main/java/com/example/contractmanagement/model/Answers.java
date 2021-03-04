package com.example.contractmanagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "answers")
@Getter
@Setter
@NoArgsConstructor
public class Answers {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Integer id;
	
	@Column(name = "answer", length = 50)
	private String answer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "QuestionId")
	private SecretQuestions questions;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SupplierId")
	private Supplier supplier;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ContractorId")
	private Contractor contractor;

	@Override
	public String toString() {
		return "Answers [id=" + id + ", answer=" + answer + ", supplier=" + supplier.getId() + ", contractor=" + contractor.getId()
				+ "]";
	}
}
