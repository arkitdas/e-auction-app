package com.cognizant.buyer.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private String bidId;
	
	@Column(name = "productId")
	private String productId;
	
	@Column(name = "bidAmount")
	private double bidAmount;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;
}
