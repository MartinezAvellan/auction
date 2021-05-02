package br.com.quarktech.auction;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.AfterAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.quarktech.auction.constants.AuctionConstants;
import br.com.quarktech.auction.dto.AuctionRequestDTO;
import br.com.quarktech.auction.exceptions.FullAuctionExcpetion;
import br.com.quarktech.auction.interfaces.AuctionServiceInterface;

@SpringBootTest
public class AuctionBidInsert1000Test {
	
	@Autowired
	private AuctionServiceInterface auctionService;
	
	private static BigDecimal generateRandomBigDecimalFromRange(BigDecimal min, BigDecimal max) {
		BigDecimal randomBigDecimal = min.add(new BigDecimal(Math.random()).multiply(max.subtract(min)));
		return randomBigDecimal.setScale(2, RoundingMode.HALF_UP);
	}
	
	@AfterAll
	void testIFBidMoreThan1000() throws InterruptedException {
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