package com.example.contractmanagement.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="proposals")
public class Proposal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Integer id;
	
	@Column(name = "ExpectedDeliveryDate")
	@Temporal(TemporalType.DATE)
	private Date proposalDate;
	
	@Column(name = "Quotation")
	BigDecimal quotation;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="RequirementId")
	private Requirement requirement;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="SupplierId")
	private Supplier supplier;

	@Column(name = "Status", length=50)
	private String status;

	@Override
	public String toString() {
		return "Proposal [id=" + id + ", proposalDate=" + proposalDate + ", quotation=" + quotation + ", requirement="
				+ requirement.getId() + ", supplier=" + supplier.getId() + ", status=" + status + "]";
	}
	
	
	
	
}
