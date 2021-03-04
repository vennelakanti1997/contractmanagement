package com.example.contractmanagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "feedback")
@Getter
@Setter
@NoArgsConstructor
public class Feedback {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Integer id;
	
	@Column(name = "FeedbackText", length = 500)
	@Size(max = 500, message = "Feedback must contain a maximum of 500 characters")
	private String feedbackText;
	
	@OneToOne
	@JoinColumn(name="ContractId")
	private Contract contract;

	@Override
	public String toString() {
		return "Feedback [id=" + id + ", feedbackText=" + feedbackText + ", contract=" + contract.getId() + "]";
	}
	
}
