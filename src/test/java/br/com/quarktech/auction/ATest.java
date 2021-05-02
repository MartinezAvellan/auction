package br.com.quarktech.auction;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.quarktech.auction.constants.AuctionConstants;
import br.com.quarktech.auction.dto.AuctionRequestDTO;
import br.com.quarktech.auction.exceptions.BidFieldsNullException;
import br.com.quarktech.auction.exceptions.BidNotFoundException;
import br.com.quarktech.auction.exceptions.GreaterThanZeroAuctionException;
import br.com.quarktech.auction.exceptions.TwoDecimalBidAuctionException;
import br.com.quarktech.auction.interfaces.AuctionServiceInterface;

@SpringBootTest
class ATest {

	@Autowired
	private AuctionServiceInterface auctionService;

	
	@Test
	void testIFBidNotFoundMap() {
		BidNotFoundException e = assertThrows(BidNotFoundException.class, () -> {
			auctionService.getBidWinnerMap();
		}, "Expected not found in map to throw, but it didn't");
		assertTrue(AuctionConstants.BID_NOT_FOUND, e.getMessage().contains(AuctionConstants.BID_NOT_FOUND));
	}
	
	@Test
	void testIFBidNotFoundDB() throws InterruptedException {
		BidNotFoundException e = assertThrows(BidNotFoundException.class, () -> {
			auctionService.getBidWinnerDB();
		}, "Expected not found in DB to throw, but it didn't");
		assertTrue(AuctionConstants.BID_NOT_FOUND, e.getMessage().contains(AuctionConstants.BID_NOT_FOUND));
	}

	
	@Test
	void testFildsNull() {
		BidFieldsNullException e = assertThrows(BidFieldsNullException.class, () -> {
			final AuctionRequestDTO requestDTO = new AuctionRequestDTO();
			requestDTO.setName(null);
			requestDTO.setValue(null);
			auctionService.setAuction(requestDTO);
		}, "Expected message fields null to throw, but it didn't");
		assertTrue(AuctionConstants.BID_FIELDS_NULL, e.getMessage().contains(AuctionConstants.BID_FIELDS_NULL));
	}
	
	@Test
	void testIFGreaterThanZero() {
		GreaterThanZeroAuctionException e = assertThrows(GreaterThanZeroAuctionException.class, () -> {
			final AuctionRequestDTO requestDTO = new AuctionRequestDTO();
			requestDTO.setName("WINNER");
			requestDTO.setValue(new BigDecimal("0.00"));
			auctionService.setAuction(requestDTO);
		}, "Expected message field value greater than zero to throw, but it didn't");
		assertTrue(AuctionConstants.BID_GREATER_THAN_ZERO, e.getMessage().contains(AuctionConstants.BID_GREATER_THAN_ZERO));
	}
	
	@Test
	void testIFTwoDecimal() {
		TwoDecimalBidAuctionException e = assertThrows(TwoDecimalBidAuctionException.class, () -> {
			final AuctionRequestDTO requestDTO = new AuctionRequestDTO();
			requestDTO.setName("WINNER");
			requestDTO.setValue(new BigDecimal("0.003"));
			auctionService.setAuction(requestDTO);
		}, "Expected field value above three decimal to throw, but it didn't");
		assertTrue(AuctionConstants.BID_TWO_DECIMAIS, e.getMessage().contains(AuctionConstants.BID_TWO_DECIMAIS));
	}
	
}