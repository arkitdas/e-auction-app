package com.cognizant.seller.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seller_mst")
public class Seller extends Auditable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2601174294994353861L;

	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private String sellerId;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "pin")
	private String pin;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "email")
	private String email;
	
}
