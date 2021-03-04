package com.example.contractmanagement.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContractDetails {
	Integer id;
	String contractType;
	Integer duration;
	String tnc;
	Integer supplierId;
	String status;
	String amenities;
}
