package com.cognizant.bid.model;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "bid_details")
public class BidDetails extends Auditable {

	@Id
	private String bidId;
	
	@Column(name = "product_id")
	private String productId;
	
	@Column(name = "bid_amount")
	private double bidAmount;
	
	@Column(name = "buyer_id")
	private String buyerId;
}
