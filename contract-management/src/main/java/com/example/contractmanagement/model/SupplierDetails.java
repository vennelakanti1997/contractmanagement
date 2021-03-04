package com.example.contractmanagement.model;
/*
 * author : Madhurya
 * */
import lombok.AllArgsConstructor;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SupplierDetails {
	private String name;
	private String password;
	private String contactNumber;
	private String address;
	private int typeId;
}
