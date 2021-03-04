package com.example.contractmanagement.model;

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
import javax.validation.constraints.Size;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="requirements")
public class Requirement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Integer id;
	
	@Column(name="Description", length = 500)
	@Size(max=500,message="Description should have a maximum of 500 characters")
	private String description;
	
	@Column(name="DeliveryDate")
	@Temporal(TemporalType.DATE)
	private Date deliveryDate;
	
	@ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name="typeId")
	private Types type;


	@Override
	public String toString() {
		return "Requirements [id=" + id + ", description=" + description + ", deliveryDate=" + deliveryDate + ", type="
				+ type.getType() + "]";
	}
}
