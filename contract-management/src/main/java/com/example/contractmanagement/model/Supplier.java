package com.example.contractmanagement.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.example.contractmanagement.validator.ValidPassword;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "supplier")
@NoArgsConstructor
@Getter
@Setter
public class Supplier {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Integer id;
	
	@Column(name = "Name", length = 50)
	@Size(max = 50, min = 4, message = "Name should have a minimum of 4 characters and maximum of 50 characters")
	private String name;
	
	@Column(name="Password", length = 50)
	@Size(max = 50, min = 6, message = "Password should have a minimum of 6 characters and maximum of 50 characters")
	@ValidPassword
	private String password;
	
	@Column(name="ContactNumber", length = 10)
	@Size(max=10, min=10, message="Contact number should have only 10 numbers")
	private String contactNumber;
	
	@Column(name="Address", length = 500)
	@Size(min=3, max=500,message="Address should have a minimum of 3 characters and a maximum of 500 characters")
	private String address;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name="typeId")
	private Types type;
	
	@OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Contract> contracts = new ArrayList<Contract>();
	
	@Override
	public String toString() {
		return "Supplier [id=" + id + ", name=" + name + ", password=" + password + ", contactNumber=" + contactNumber
				+ ", address=" + address + ", type=" + type.getType() + "]";
	}
}
