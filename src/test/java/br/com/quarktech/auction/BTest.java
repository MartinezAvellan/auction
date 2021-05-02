package br.com.quarktech.auction;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.quarktech.auction.constants.AuctionConstants;
import br.com.quarktech.auction.dto.AuctionRequestDTO;
import br.com.quarktech.auction.entities.Auction;
import br.com.quarktech.auction.exceptions.FullAuctionExcpetion;
import br.com.quarktech.auction.interfaces.AuctionServiceInterface;

@SpringBootTest
public class BTest {

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
	
	private static BigDecimal generateRandomBigDecimalFromRange(BigDecimal min, BigDecimal max) {
		BigDecimal randomBigDecimal = min.add(new BigDecimal(Math.random()).multiply(max.subtract(min)));
		return randomBigDecimal.setScale(2, RoundingMode.HALF_UP);
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
	
	@Test
	void testIFBidWinMoreThan1000() throws InterruptedException {
		FullAuctionExcpetion e = assertThrows(FullAuctionExcpetion.class, () -> {
			for (int i = 1; i <= 1000; i++) {
				final BigDecimal random = generateRandomBigDecimalFromRange(new BigDecimal(0.01).setScale(2, RoundingMode.HALF_UP), new BigDecimal(99.99).setScale(2, RoundingMode.HALF_UP));
				final AuctionRequestDTO requestDTO = new AuctionRequestDTO();
				requestDTO.setName("WINNER-" + random);
				requestDTO.setValue(random);
				auctionService.setAuction(requestDTO);
			}		
		}, "Expected more tahn 1000 auction to throw, but it didn't");
		assertTrue(AuctionConstants.FULL_AUCTION, e.getMessage().contains(AuctionConstants.FULL_AUCTION));
	}	

}