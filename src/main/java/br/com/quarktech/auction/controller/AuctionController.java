package br.com.quarktech.auction.controller;

import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.quarktech.auction.constants.AuctionConstants;
import br.com.quarktech.auction.dto.AuctionRequestDTO;
import br.com.quarktech.auction.dto.AuctionResponseDTO;
import br.com.quarktech.auction.entities.Auction;
import br.com.quarktech.auction.exceptions.BidFieldsNullException;
import br.com.quarktech.auction.exceptions.BidNotFoundException;
import br.com.quarktech.auction.exceptions.FullAuctionExcpetion;
import br.com.quarktech.auction.exceptions.GreaterThanZeroAuctionException;
import br.com.quarktech.auction.exceptions.TwoDecimalBidAuctionException;
import br.com.quarktech.auction.interfaces.AuctionServiceInterface;

@RestController
@RequestMapping
public class AuctionController {
	
	@Autowired
	private AuctionServiceInterface auctionService;
	
	@PostMapping("/auction-bid")
	public ResponseEntity<?> insertAuction(@Validated @RequestBody final AuctionRequestDTO requestDTO) {
		try {
			auctionService.setAuction(requestDTO);
		} catch (FullAuctionExcpetion | TwoDecimalBidAuctionException | GreaterThanZeroAuctionException | BidFieldsNullException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(HttpStatus.CREATED);
	}	
	
	@GetMapping("/lower-bid-db")
	public ResponseEntity<?> getLowerBidDB() {
		final AuctionResponseDTO dto = new AuctionResponseDTO();
		try {
			final Auction winner = auctionService.getBidWinnerDB();
			dto.setName(winner.getName());
			dto.setValue(winner.getAuctionValue());
			dto.setTotal(winner.getAuctionValue().multiply(AuctionConstants.BID_COSTS).setScale(2, RoundingMode.HALF_UP));
		} catch (BidNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}
	
	@GetMapping("/lower-bid-map")
	public ResponseEntity<?> getLowerBidMap() {
		final AuctionResponseDTO dto = new AuctionResponseDTO();
		try {
			final Auction winner = auctionService.getBidWinnerMap();
			dto.setName(winner.getName());
			dto.setValue(winner.getAuctionValue());
			dto.setTotal(winner.getAuctionValue().multiply(AuctionConstants.BID_COSTS).setScale(2, RoundingMode.HALF_UP));
		} catch (BidNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}

}