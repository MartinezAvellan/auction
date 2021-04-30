package br.com.quarktech.auction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.quarktech.auction.entities.Auction;
import br.com.quarktech.auction.service.AuctionService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping
public class AuctionController {
	
	@Autowired
	private AuctionService auctionService;
	
	@GetMapping("/sale")
	public @ResponseBody Auction auction() {
		//final BigDecimal d = new BigDecimal(0.01).setScale(2, RoundingMode.HALF_UP);
		Auction winner = null;
		try {
			winner = auctionService.getWinner();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return winner;
	}

}