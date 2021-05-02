package br.com.quarktech.auction.interfaces;

import br.com.quarktech.auction.dto.AuctionRequestDTO;
import br.com.quarktech.auction.entities.Auction;
import br.com.quarktech.auction.exceptions.BidFieldsNullException;
import br.com.quarktech.auction.exceptions.BidNotFoundException;
import br.com.quarktech.auction.exceptions.FullAuctionExcpetion;
import br.com.quarktech.auction.exceptions.GreaterThanZeroAuctionException;
import br.com.quarktech.auction.exceptions.TwoDecimalBidAuctionException;

public interface AuctionServiceInterface {
	
	public void setAuction(final AuctionRequestDTO requestDTO) throws FullAuctionExcpetion, TwoDecimalBidAuctionException, GreaterThanZeroAuctionException, BidFieldsNullException;
	public Auction getBidWinnerDB() throws BidNotFoundException;
	public Auction getBidWinnerMap() throws BidNotFoundException;

}