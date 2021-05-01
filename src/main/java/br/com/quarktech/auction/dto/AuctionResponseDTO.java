package br.com.quarktech.auction.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class AuctionResponseDTO implements Serializable {
	
	private static final long serialVersionUID = 4972293521099073279L;
	private String name;
	private BigDecimal value;
	private BigDecimal total;

}