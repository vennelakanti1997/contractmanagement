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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contracts")
@Getter
@Setter
@NoArgsConstructor
public class Contract {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Integer id;
	
	@NotNull(message = "Contract Type Cannot be null")
	@Column(name ="ContractType", length = 500)
	@Size(min = 1, max = 500, message = "Contract Type must not be empty and have more than 500 characters")
	private String contractType;
	
	@NotNull(message = "Duration cannot vbe null")
	@Column(name="ConractDuration")
	private Integer contractDuration; 
	

	@NotNull
	@Column(name ="TermsAndConditions", length = 500)
	@Size(min = 1, max = 500, message = "Contract Type must not be empty and not have more than 500 characters")
	private String termsAndConditions;
	
	@NotNull(message="SupplierId cannot be null")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="SuplierId")
	//@JsonIgnore
	private Supplier supplier;
   
	@NotNull(message="Status cannot be null")
	@Column(name="Status", length=50)
	private String status;
    
	@Column(name="Amenities", length=500)
	@Size(max=500, message="Amenities must not have more than 500 characters")
	private String amenities;

	@Override
	public String toString() {
		return "Contract [id=" + id + ", contractType=" + contractType + ", contractDuration=" + contractDuration
				+ ", termsAndConditions=" + termsAndConditions + ", supplier=" + supplier.getId() + ", status=" + status
				+ ", amenities=" + amenities + "]";
	}

	public Contract(
			@Size(min = 1, max = 500, message = "Contract Type must not be empty and have more than 500 characters") String contractType,
			Integer contractDuration,
			@Size(min = 1, max = 500, message = "Contract Type must not be empty and not have more than 500 characters") String termsAndConditions,
			Supplier supplier, String status,
			@Size(max = 500, message = "Amenities must not have more than 500 characters") String amenities) {
		super();
		this.contractType = contractType;
		this.contractDuration = contractDuration;
		this.termsAndConditions = termsAndConditions;
		this.supplier = supplier;
		this.status = status;
		this.amenities = amenities;
	}
	
	
}