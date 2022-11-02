package com.cognizant.product.model;

import java.util.Date;

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
@Table(name = "product_mst")
public class Product extends Auditable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2310437731988697720L;

	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private String productId;
	
	@Column(name = "product_name")
	private String productName;
	
	@Column(name = "short_description")
	private String shortDescription;
	
	@Column(name = "detailed_description")
	private String detailedDescription;
	
	@Column(name = "categopry")
	private String categopry;
	
	@Column(name = "starting_price")
	private double startingPrice;
	
	@Column(name = "bid_end_date")
	private Date bidEndDate;
	
	@Column(name = "is_active")
    private boolean active;
	
	
}
