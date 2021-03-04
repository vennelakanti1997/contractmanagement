package com.example.contractmanagement.model;
/*
 * author : Madhurya
 * */
import java.util.List;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "types")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Types {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Integer id;
	
	@Column(name = "Type", length = 50)
	@Size(max = 50, message = "Type should have a maximum of 50 characters")
	private String type;
	
	@OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
	private List<Supplier> suppliers;
}