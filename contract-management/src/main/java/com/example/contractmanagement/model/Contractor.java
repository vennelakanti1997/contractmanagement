package com.example.contractmanagement.model;
/*
 * author : Madhurya
 * */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "contractor")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Contractor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Integer id;
	
	@Column(name = "Name", length = 50)
	@Size(max = 50, min = 4, message = "Name should have a minimum of 4 characters and maximum of 50 characters")
	private String name;
	
	@Column(name="Password", length = 50)
	@Size(max = 50, min = 6, message = "Password should have a minimum of 6 characters and maximum of 50 characters")
	private String password;

	public Contractor(
			@Size(max = 50, min = 4, message = "Name should have a minimum of 4 characters and maximum of 50 characters") String name,
			@Size(max = 50, min = 6, message = "Password should have a minimum of 6 characters and maximum of 50 characters") String password) {
		super();
		this.name = name;
		this.password = password;
	}
	
	

}
