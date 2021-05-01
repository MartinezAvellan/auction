package br.com.quarktech.auction.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class AuctionRequestDTO implements Serializable {
	
	private static final long serialVersionUID = 5119787893150862689L;
	private String name;
	private BigDecimal value;

}