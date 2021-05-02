package br.com.quarktech.auction;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.quarktech.auction.dto.AuctionRequestDTO;
import br.com.quarktech.auction.entities.Auction;
import br.com.quarktech.auction.interfaces.AuctionServiceInterface;

@SpringBootTest
public class AuctionBidWinnerTest {

	@Autowired
	private AuctionServiceInterface auctionService;

	@BeforeEach
	void InsertBids() throws Exception {
		final Map<String, BigDecimal> mapBid = new HashMap<>();
		mapBid.put("HUGO 1.99", new BigDecimal("1.99"));
		mapBid.put("HUGO 0.99", new BigDecimal("0.9"));
		mapBid.put("HUGO 0.01-2", new BigDecimal("0.01"));
		mapBid.put("HUGO 1.3", new BigDecimal("1.3"));
		mapBid.put("HUGO WIN", new BigDecimal("0.3"));
		mapBid.put("HUGO 0.01", new BigDecimal("0.01"));
		for (final Map.Entry<String, BigDecimal> bid : mapBid.entrySet()) {
			final AuctionRequestDTO requestDTO = new AuctionRequestDTO();
			requestDTO.setName(bid.getKey());
			requestDTO.setValue(bid.getValue());
			auctionService.setAuction(requestDTO);
		}
	}
	
	@Test
	void testIFBidWinMap() throws Exception {
		final Auction winner = auctionService.getBidWinnerMap();
		assertEquals("HUGO WIN", winner.getName(), "Winner MAP name espected is HUGO WIN and not " + winner.getName());
		assertEquals(new BigDecimal("0.3"), winner.getAuctionValue(), "Winner DB value espected is 0.3 and not " + winner.getName());
	}
	
	@Test
	void testIFBidWinDB() throws Exception {
		final Auction winner = auctionService.getBidWinnerDB();
		assertEquals("HUGO WIN", winner.getName(), "Winner DB name espected is HUGO WIN and not " + winner.getName());
		assertEquals(new BigDecimal("0.3"), winner.getAuctionValue(), "Winner DB value espected is 0.3 and not " + winner.getName());
	}

}