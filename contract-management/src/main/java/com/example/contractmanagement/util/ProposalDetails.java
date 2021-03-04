package com.example.contractmanagement.util;



import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProposalDetails {
	private Integer id;
	private String proposalDate;
	private Integer requirementId;
	private String supplierName;
	private String quotation;
	private String status;
}

